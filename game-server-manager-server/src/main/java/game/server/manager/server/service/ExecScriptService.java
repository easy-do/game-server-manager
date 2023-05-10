package game.server.manager.server.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.extra.ssh.ChannelType;
import cn.hutool.extra.ssh.JschUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import game.server.manager.auth.AuthorizationUtil;
import game.server.manager.common.application.ExecScriptParam;
import game.server.manager.common.enums.AppStatusEnum;
import game.server.manager.common.enums.ConvertExceptionEnum;
import game.server.manager.common.enums.DeviceTypeEnum;
import game.server.manager.common.enums.ExecScriptEnum;
import game.server.manager.common.enums.ServerMessageTypeEnum;
import game.server.manager.common.exception.ExceptionFactory;
import game.server.manager.common.utils.ScriptDataUtils;
import game.server.manager.common.vo.ExecScriptVo;
import game.server.manager.event.BasePublishEventServer;
import game.server.manager.redis.config.RedisStreamUtils;
import game.server.manager.server.application.DeploymentLogServer;
import game.server.manager.server.entity.ClientInfo;
import game.server.manager.server.entity.ExecuteLog;
import game.server.manager.server.entity.ScriptData;
import game.server.manager.server.entity.ServerInfo;
import game.server.manager.server.redis.ExecScriptListenerMessage;
import game.server.manager.server.util.server.SessionUtils;
import game.server.manager.server.websocket.SocketSessionCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.DeploymentException;
import javax.websocket.Session;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static game.server.manager.common.constant.Constants.UNIX_LINE_END_CHAR;
import static game.server.manager.common.constant.Constants.WINDOW_LINE_END_CHAR;

/**
 * @author laoyu
 * @version 1.0
 * @description 日志执行服务
 * @date 2023/3/16
 */
@Component
public class ExecScriptService {

    private static final String INSTALL_FAILED = "INSTALL_FAILED";
    @Autowired
    private ServerInfoService serverInfoService;

    @Autowired
    private ClientInfoService clientInfoService;

    @Autowired
    private ScriptDataService scriptDataService;

    @Autowired
    private ExecuteLogService executeLogService;

    @Autowired
    private RedisStreamUtils<Object> redisStreamUtils;

    @Autowired
    private BasePublishEventServer basePublishEventServer;

    @Autowired
    private ScriptEnvDataService scriptEnvDataService;

    @Autowired
    private DeploymentLogServer deploymentLogServer;


    public boolean execScript(ExecScriptVo execScriptVo) {
        String deviceId = execScriptVo.getDeviceId();
        String scriptId = execScriptVo.getScriptId();
        Integer deviceType = execScriptVo.getDeviceType();
        ScriptData scriptData = scriptDataService.getById(scriptId);
        if(Objects.isNull(scriptData)){
            throw ExceptionFactory.bizException("脚本不存在.");
        }
        //创建执行记录
        ExecuteLog executeLog = ExecuteLog.builder()
                .deviceId(deviceId).deviceType(deviceType)
                .scriptId(Long.valueOf(scriptId))
                .createTime(LocalDateTime.now())
                .createBy(AuthorizationUtil.getUserId())
                .build();
        executeLog.setScriptName(scriptData.getScriptName());
        //保存日志
        boolean result = executeLogService.save(executeLog);
        ClientInfo clientInfo;
        if (!result) {
            return false;
        }
        if(deviceType == DeviceTypeEnum.SERVER.getType()){
            ServerInfo serverInfo = serverInfoService.getById(deviceId);
            if(Objects.isNull(serverInfo)){
                throw ExceptionFactory.bizException("目标服务器不存在.");
            }
            executeLog.setDeviceName(serverInfo.getServerName());
            executeLog.setExecuteState(AppStatusEnum.QUEUE.getDesc());
            //发布订阅消息
            ExecScriptParam execParam = BeanUtil.copyProperties(execScriptVo,ExecScriptParam.class);
            execParam.setExecuteLogId(String.valueOf(executeLog.getId()));
            execParam.setUserId(String.valueOf(AuthorizationUtil.getUserId()));
            redisStreamUtils.add(ExecScriptListenerMessage.DEFAULT_STREAM_NAME, BeanUtil.beanToMap(execParam));
        }else {
            clientInfo = clientInfoService.getById(deviceId);
            if(Objects.isNull(clientInfo)){
                throw ExceptionFactory.bizException("目标客户端不存在.");
            }
            executeLog.setDeviceName(clientInfo.getClientName());
            executeLog.setExecuteState(AppStatusEnum.WAIT_CLIENT.getDesc());
            //获得客户端session
            Session clientSession = SocketSessionCache.getClientByClientId(clientInfo.getId());
            if(Objects.isNull(clientSession)){
                executeLog.setExecuteState(ExecScriptEnum.FAILED.getDesc());
                executeLogService.updateById(executeLog);
                throw ExceptionFactory.bizException("客户端不在线,或断开连接.");
            }
            //发送部署消息
            SessionUtils.sendSimpleServerMessage(clientSession,clientSession.getId(), JSON.toJSONString(execScriptVo) , ServerMessageTypeEnum.EXEC_SCRIPT);
        }
        return true;
    }


    public String startExecScript (ExecScriptParam execScriptParam) {
        LocalDateTime startTime = LocalDateTime.now();
        Long logId = Long.valueOf(execScriptParam.getExecuteLogId().replace("\"", ""));
        LinkedList<String> stdout = new LinkedList<>();
        com.jcraft.jsch.Session session = null;
        ScriptData scriptData = null;
        ServerInfo serverInfo;
        ExecuteLog executeLog = executeLogService.getById(logId);
        try {
            if(Objects.isNull(executeLog)){
                throw new DeploymentException("日志不存在。");
            }
            serverInfo = serverInfoService.getById(Long.valueOf(execScriptParam.getDeviceId().replace("\"", "")));
            if(Objects.isNull(serverInfo)){
                throw new DeploymentException("服务器已不存在。");
            }
            scriptData = scriptDataService.getById(execScriptParam.getScriptId());
            if(Objects.isNull(scriptData)){
                throw new DeploymentException("脚本已不存在。");
            }
            //生成日志
            executeLog = generateExecuteLog(logId, startTime);
            String address = serverInfo.getAddress();
            String port = serverInfo.getPort();
            String userName = serverInfo.getUserName();
            String password = serverInfo.getPassword();
            session = JschUtil.openSession(address, Integer.parseInt(port), userName, password);
            saveLogLine(logId, stdout, "成功连接到服务器");
            //构建上传执行脚本
            //判断脚本是否存在依赖 存在则先执行依赖的脚本
            extractedBasicScript(execScriptParam, scriptData, logId, session, stdout);
            //执行主脚本
            buildUploadAndExecScript(execScriptParam, scriptData, session, logId, stdout);
            saveLogLine(logId, stdout, "------------------流程执行完毕--------------------");
            executeLog.setExecuteState(AppStatusEnum.FINISH.getDesc());
        } catch (Exception e) {
            saveLogLine(logId, stdout,convertExceptionMessage(e));
            if(Objects.nonNull(executeLog)){
                executeLog.setExecuteState(AppStatusEnum.FAILED.getDesc());
            }
            return String.join("", stdout);
        } finally {
            if (Objects.nonNull(session) && session.isConnected()) {
                session.disconnect();
                saveLogLine(logId, stdout, "主动断开服务器连接。");
            }
            deploymentLogServer.cleanDeploymentLog(logId);
            if(Objects.nonNull(executeLog)){
                executeLog.setEndTime(LocalDateTime.now());
                executeLog.setLogData(String.join("", stdout));
                executeLogService.updateById(executeLog);
            }
            //执行完毕通知
            if(Objects.nonNull(scriptData)){
               basePublishEventServer.publishScriptEndEvent(Long.valueOf(execScriptParam.getUserId()), scriptData.getScriptName());
            }
        }
        return String.join("", stdout);
    }


    private void extractedBasicScript(ExecScriptParam execScriptParam, ScriptData scriptData, Long logId, com.jcraft.jsch.Session session, LinkedList<String> stdout) throws DeploymentException {
        String basicScript = scriptData.getBasicScript();
        if (CharSequenceUtil.isNotEmpty(basicScript)) {
            String[] basicScripts = basicScript.split(",");
            if (basicScripts.length > 0) {
                saveLogLine(logId, stdout, "------------------检测到脚本依赖，先执行依赖的脚本--------------------");
                for (int i = 0; i < basicScripts.length; i++) {
                    String basicScriptId = basicScripts[i];
                    ScriptData basicScriptInfo = scriptDataService.getById(basicScriptId);
                    buildUploadAndExecScript(execScriptParam,basicScriptInfo, session, logId, stdout);
                }
                saveLogLine(logId, stdout, "------------------主脚本依赖的脚本执行完毕--------------------");
            }
        }
    }

    private ExecuteLog generateExecuteLog(Long logId, LocalDateTime startTime) {
        ExecuteLog executeLog = ExecuteLog.builder()
                .id(logId).executeState(AppStatusEnum.STARTING.getDesc())
                .startTime(startTime)
                .build();
        executeLogService.updateById(executeLog);
        return executeLog;
    }

    /**
     * 构建上传执行脚本
     *
     * @param execScriptParam execScriptParam
     * @param scriptData   scriptData
     * @param stdout      stdout
     * @param session     session
     * @author laoyu
     * @date 2022/6/12
     */
    private void buildUploadAndExecScript(ExecScriptParam execScriptParam,  ScriptData scriptData,com.jcraft.jsch.Session session, Long logId, List<String> stdout) throws DeploymentException {
        String result;
        String scriptName = scriptData.getScriptName();
        String fileName = UUID.randomUUID().toString();
        try {
            scriptDataService.addHeat(scriptData.getId());
            //1.构建脚本
            saveLogLine(logId, stdout, "------------------构建脚本《" + scriptName + "》--------------------");
            String scriptStr = scriptData.getScriptFile();
            if (CharSequenceUtil.isEmpty(scriptStr)) {
                throw new DeploymentException("脚本《" + scriptName + "》内容为空,请编写脚本后再执行!!!");
            }
            scriptStr = scriptStr.replace(UNIX_LINE_END_CHAR, WINDOW_LINE_END_CHAR);
            InputStream inputStream = new ByteArrayInputStream(scriptStr.getBytes());
            ChannelSftp sftp = JschUtil.openSftp(session);
            OutputStream sftpOps = sftp.put(fileName);
            IoUtil.copy(inputStream, sftpOps);
            sftpOps.flush();
            sftpOps.close();
            inputStream.close();
            saveLogLine(logId, stdout, "------------------脚本《" + scriptName + "》构建完毕--------------------");
            //2.执行脚本
            saveLogLine(logId, stdout, "-----开始运行脚本《" + scriptName + "》,跑完脚本后会刷新详细日志,耐心等待------");
            String execStr = "chmod u+x " + fileName + " && ./" + fileName;
            StringBuilder stringBuilder = new StringBuilder(execStr);
            JSONObject env = execScriptParam.getEnv();
            if(Objects.isNull(env)){
                env = new JSONObject();
            }
            String execShellEnv = ScriptDataUtils.generateExecShellEnvs(env, scriptEnvDataService.getVoListByScriptId(scriptData.getId()));
            stringBuilder.append(" ").append(execShellEnv);
            exec(session, stringBuilder.toString(), CharsetUtil.CHARSET_UTF_8, logId, stdout);
            saveLogLine(logId, stdout, "------------------脚本《" + scriptName + "》运行完毕--------------------");
            saveLogLine(logId, stdout, "注意：如果没有脚本的详细安装信息可能代表运行失败。");
        } catch (Exception e) {
            throw new DeploymentException(ExceptionUtil.getMessage(e));
        } finally {
            //3.删除脚本文件
            result = JschUtil.exec(session, "rm -r " + fileName, CharsetUtil.CHARSET_UTF_8);
            saveLogLine(logId, stdout, result);
        }
    }

    public void exec(com.jcraft.jsch.Session session, String cmd, Charset charset, Long logId, List<String> stdout) throws DeploymentException {
        if (null == charset) {
            charset = CharsetUtil.CHARSET_UTF_8;
        }
        final ChannelExec channel = (ChannelExec) JschUtil.createChannel(session, ChannelType.EXEC);
        channel.setCommand(CharSequenceUtil.bytes(cmd, charset));
        channel.setInputStream(null);
        BufferedReader input = null;
        try {
            channel.connect();
            //接受远程服务器执行命令的结果
            input = new BufferedReader(new InputStreamReader(channel.getInputStream()));
            String line = null;
            while ((line = input.readLine()) != null) {
                if (line.contains(INSTALL_FAILED)) {
                    throw new DeploymentException("脚本执行失败,请查看日志手动解决!!!");
                }
                saveLogLine(logId, stdout, line);
            }
            input.close();
        } catch (IOException | JSchException e) {
            throw ExceptionFactory.bizException(ExceptionUtil.getMessage(e));
        } finally {
            JschUtil.close(channel);
        }
    }


    public void saveLogLine(Long logId, List<String> stdout, String log) {
        if(!CharSequenceUtil.endWith(log, WINDOW_LINE_END_CHAR)){
            log = log + WINDOW_LINE_END_CHAR;
        }
        LocalDateTime now = LocalDateTime.now();
        String prefix = DateUtil.format( now , DatePattern.NORM_DATETIME_PATTERN) +": ";
        log = prefix + log;
        stdout.add(log);
        deploymentLogServer.saveDeploymentLog(logId, log);
    }

    /**
     * 将异常消息转为中文错误
     *
     * @param exception exception
     * @return java.lang.String
     * @author laoyu
     * @date 2022/9/27
     */
    public static String convertExceptionMessage(Exception exception){
        String message = ExceptionUtil.getMessage(exception);
        for (ConvertExceptionEnum exceptionEnum:ConvertExceptionEnum.values()) {
            if(message.contains(exceptionEnum.getEn())){
                return exceptionEnum.getEn() +","+ exceptionEnum.getCn();
            }
        }
        return message;
    }
}
