package game.server.manager.server.application;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.extra.ssh.ChannelType;
import cn.hutool.extra.ssh.JschRuntimeException;
import cn.hutool.extra.ssh.JschUtil;
import com.alibaba.fastjson2.JSONObject;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import game.server.manager.api.SysDictDataApi;
import game.server.manager.common.application.DeployParam;
import game.server.manager.common.enums.ConvertExceptionEnum;
import game.server.manager.common.exception.BizException;
import game.server.manager.common.utils.AppScriptUtils;
import game.server.manager.common.vo.SysDictDataVo;
import game.server.manager.event.BasePublishEventServer;
import game.server.manager.server.entity.AppInfo;
import game.server.manager.server.entity.AppScript;
import game.server.manager.server.entity.ApplicationInfo;
import game.server.manager.server.entity.ClientInfo;
import game.server.manager.server.entity.ExecuteLog;
import game.server.manager.server.entity.ServerInfo;
import game.server.manager.common.enums.AppStatusEnum;
import game.server.manager.common.enums.ScriptTypeEnum;
import game.server.manager.server.service.AppEnvInfoService;
import game.server.manager.server.service.AppInfoService;
import game.server.manager.server.service.AppScriptService;
import game.server.manager.server.service.ApplicationInfoService;
import game.server.manager.server.service.ClientInfoService;
import game.server.manager.server.service.ExecuteLogService;
import game.server.manager.server.service.ServerInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.DeploymentException;
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
 * @date 2022/5/21
 */
@Slf4j
@Component
public class DeploymentServer {

    private static final String INSTALL_FAILED = "INSTALL_FAILED";

    @Autowired
    private ApplicationInfoService applicationInfoService;

    @Autowired
    private ServerInfoService serverInfoService;

    @Autowired
    private AppInfoService appInfoService;

    @Autowired
    private DeploymentLogServer deploymentLogServer;

    @Autowired
    private AppScriptService appScriptService;

    @Autowired
    private AppEnvInfoService appEnvInfoService;

    @Autowired
    private ExecuteLogService executeLogService;

    @Autowired
    private BasePublishEventServer basePublishEventServer;

    @Autowired
    private ClientInfoService clientInfoService;

    @Autowired
    private SysDictDataApi sysDictDataService;


    /**
     * ????????????
     *
     * @param deployParam de
     * @author laoyu
     * @date 2022/5/21
     */
    public String deployment(DeployParam deployParam) {
        return startDeployment(deployParam);
    }

    public String startDeployment(DeployParam deployParam) {
        LocalDateTime startTime = LocalDateTime.now();
        Long logId = Long.valueOf(deployParam.getLogId());
        LinkedList<String> stdout = new LinkedList<>();
        String applicationId = null;
        Session session = null;
        ApplicationInfo application = null;
        ClientInfo clientInfo = null;
        AppInfo appInfo = null;
        AppScript appScript = null;
        ServerInfo serverInfo;
        ExecuteLog executeLog = executeLogService.getById(logId);
        try {
            if(Objects.isNull(executeLog)){
                throw new DeploymentException("??????????????????");
            }
            applicationId = deployParam.getApplicationId().replace("\"", "");
            if(!deployParam.isClient()){
                application = applicationInfoService.getById(applicationId);
                if(Objects.isNull(application)){
                    throw new DeploymentException("?????????????????????");
                }
                appInfo = appInfoService.getById(application.getAppId());
                if(Objects.isNull(appInfo)){
                    throw new DeploymentException("APP???????????????");
                }
                serverInfo = serverInfoService.getById(Long.valueOf(application.getDeviceId()));
            }else {
                clientInfo = clientInfoService.getById(applicationId);
                if(Objects.isNull(clientInfo)){
                    throw new DeploymentException("????????????????????????");
                }
                SysDictDataVo clientAppDictData = sysDictDataService.getSingleDictData("system_config", "client_app_id").getData();
                if(Objects.isNull(clientAppDictData)){
                    throw new BizException("?????????APP???????????????.");
                }
                appInfo = appInfoService.getById(clientAppDictData.getDictValue());
                if(Objects.isNull(appInfo)){
                    throw new DeploymentException("APP???????????????");
                }
                serverInfo = serverInfoService.getById(clientInfo.getServerId());
            }
            if(Objects.isNull(serverInfo)){
                throw new DeploymentException("?????????????????????");
            }
            appScript = appScriptService.getById(deployParam.getAppScriptId());
            if(Objects.isNull(appScript)){
                throw new DeploymentException("?????????????????????");
            }

            //????????????
            executeLog = generateExecuteLog(logId, startTime);
            if(!deployParam.isClient()){
                //??????????????????????????????
                application.setStatus(AppStatusEnum.DEPLOYMENT_ING.getDesc());
                applicationInfoService.updateById(application);
                //????????????
                appInfoService.addHeat(appInfo.getId());
            }else {
                //?????????????????????????????????
                applicationId = clientInfo.getId();
                clientInfo.setStatus(AppStatusEnum.DEPLOYMENT_ING.getDesc());
                clientInfoService.updateById(clientInfo);
            }
            String address = serverInfo.getAddress();
            String port = serverInfo.getPort();
            String userName = serverInfo.getUserName();
            String password = serverInfo.getPassword();
            session = JschUtil.openSession(address, Integer.parseInt(port), userName, password);
            saveLogLine(logId, stdout, "????????????????????????");
            //????????????????????????
            //?????????????????????????????? ?????????????????????????????????
            extractedBasicScript(deployParam, appScript, appInfo, applicationId, logId, session, stdout);
            //???????????????
            buildUploadAndExecScript(deployParam, appInfo, appScript, applicationId, session, logId, stdout);
            saveLogLine(logId, stdout, "------------------??????????????????--------------------");
            setApplicationState(appScript, applicationId, deployParam.isClient());
            executeLog.setExecuteState(AppStatusEnum.FINISH.getDesc());
        } catch (Exception e) {
            saveLogLine(logId, stdout,convertExceptionMessage(e));
            setApplicationState(applicationId, AppStatusEnum.DEPLOYMENT_FAILED,deployParam.isClient());
            if(Objects.nonNull(executeLog)){
                executeLog.setExecuteState(AppStatusEnum.FAILED.getDesc());
            }
            return String.join("", stdout);
        } finally {
            if (Objects.nonNull(session) && session.isConnected()) {
                session.disconnect();
                saveLogLine(logId, stdout, "??????????????????????????????");
            }
            deploymentLogServer.cleanDeploymentLog(logId);
            if(Objects.nonNull(executeLog)){
                executeLog.setEndTime(LocalDateTime.now());
                executeLog.setLogData(String.join("\n", stdout));
                executeLogService.updateById(executeLog);
            }
            //??????????????????
            if(Objects.nonNull(appScript)){
                if(Objects.nonNull(application)){
                    basePublishEventServer.publishScriptEndEvent(application.getUserId(),appScript.getScriptName());
                }
                if(Objects.nonNull(clientInfo)){
                    basePublishEventServer.publishScriptEndEvent(clientInfo.getCreateBy(),appScript.getScriptName());
                }
            }
        }
        return String.join("", stdout);
    }

    private void setApplicationState(AppScript appScript, String applicationId, boolean isClient) {
        String scriptType = appScript.getScriptType();
        if (ScriptTypeEnum.INSTALL.getDesc().contains(scriptType)) {
            setApplicationState(applicationId, AppStatusEnum.DEPLOYMENT_SUCCESS,isClient);
        }
        if (ScriptTypeEnum.RESTART.getDesc().contains(scriptType)) {
            setApplicationState(applicationId, AppStatusEnum.RESTART,isClient);
        }
        if (ScriptTypeEnum.STOP.getDesc().contains(scriptType)) {
            setApplicationState(applicationId, AppStatusEnum.STOP,isClient);
        }
        if (ScriptTypeEnum.UNINSTALL.getDesc().contains(scriptType)) {
            setApplicationState(applicationId, AppStatusEnum.UNINSTALL,isClient);
        }
        if (ScriptTypeEnum.CUSTOM.getDesc().contains(scriptType)) {
            setApplicationState(applicationId, AppStatusEnum.CUSTOM,isClient);
        }
        if (ScriptTypeEnum.BASIC.getDesc().contains(scriptType)) {
            setApplicationState(applicationId, AppStatusEnum.CUSTOM,isClient);
        }
    }

    private void extractedBasicScript(DeployParam deployParam, AppScript appScript, AppInfo appInfo, String applicationId, Long logId, Session session, LinkedList<String> stdout) throws DeploymentException {
        String basicScript = appScript.getBasicScript();
        if (CharSequenceUtil.isNotEmpty(basicScript)) {
            String[] basicScripts = basicScript.split(",");
            if (basicScripts.length > 0) {
                saveLogLine(logId, stdout, "------------------????????????????????????????????????????????????--------------------");
                for (int i = 0; i < basicScripts.length; i++) {
                    String basicScriptId = basicScripts[i];
                    AppScript basicScriptInfo = appScriptService.getById(basicScriptId);
                    buildUploadAndExecScript(deployParam, appInfo, basicScriptInfo, applicationId, session, logId, stdout);
                }
                saveLogLine(logId, stdout, "------------------????????????????????????????????????--------------------");
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
     * ????????????????????????
     *
     * @param deployParam deployParam
     * @param appInfo     appInfo
     * @param appScript   appScript
     * @param stdout      stdout
     * @param session     session
     * @author laoyu
     * @date 2022/6/12
     */
    private void buildUploadAndExecScript(DeployParam deployParam, AppInfo appInfo, AppScript appScript, String applicationId, Session session, Long logId, List<String> stdout) throws DeploymentException {
        String result;
        String scriptName = appScript.getScriptName();
        String fileName = UUID.randomUUID().toString();
        try {
            appScriptService.addHeat(appScript.getId());
            //1.????????????
            saveLogLine(logId, stdout, "------------------???????????????" + scriptName + "???--------------------");
            String scriptStr = appScript.getScriptFile();
            if (CharSequenceUtil.isEmpty(scriptStr)) {
                throw new DeploymentException("?????????" + scriptName + "???????????????,???????????????????????????!!!");
            }
            scriptStr = scriptStr.replace(UNIX_LINE_END_CHAR, WINDOW_LINE_END_CHAR);
            InputStream inputStream = new ByteArrayInputStream(scriptStr.getBytes());
            ChannelSftp sftp = JschUtil.openSftp(session);
            OutputStream sftpOps = sftp.put(fileName);
            IoUtil.copy(inputStream, sftpOps);
            sftpOps.flush();
            sftpOps.close();
            inputStream.close();
            saveLogLine(logId, stdout, "------------------?????????" + scriptName + "???????????????--------------------");
            //2.????????????
            saveLogLine(logId, stdout, "-----?????????????????????" + scriptName + "???,????????????????????????????????????,????????????------");
            String execStr = "chmod u+x " + fileName + " && ./" + fileName;
            StringBuilder stringBuilder = new StringBuilder(execStr);
            JSONObject env = deployParam.getEnv();
            if(Objects.isNull(env)){
                env = new JSONObject();
            }
            env.put("APPLICATION_ID", applicationId);
            env.put("APP_ID", appInfo.getId());
            env.put("APP_VERSION", appInfo.getVersion());
            env.put("CLIENT_VERSION", appInfo.getVersion());
            String execShellEnv = AppScriptUtils.generateExecShellEnvs(env,appEnvInfoService.getVoListByScriptId(appScript.getId()));
            stringBuilder.append(" ").append(execShellEnv);
            exec(session, stringBuilder.toString(), CharsetUtil.CHARSET_UTF_8, logId, stdout);
            saveLogLine(logId, stdout, "------------------?????????" + scriptName + "???????????????--------------------");
            saveLogLine(logId, stdout, "???????????????????????????????????????????????????????????????????????????");
        } catch (Exception e) {
            throw new DeploymentException(ExceptionUtil.getMessage(e));
        } finally {
            //3.??????????????????
            result = JschUtil.exec(session, "rm -r " + fileName, CharsetUtil.CHARSET_UTF_8);
            saveLogLine(logId, stdout, result);
        }
    }

    public void exec(Session session, String cmd, Charset charset, Long logId, List<String> stdout) throws DeploymentException {
        if (null == charset) {
            charset = CharsetUtil.CHARSET_UTF_8;
        }
        final ChannelExec channel = (ChannelExec) JschUtil.createChannel(session, ChannelType.EXEC);
        channel.setCommand(CharSequenceUtil.bytes(cmd, charset));
        channel.setInputStream(null);
        BufferedReader input = null;
        try {
            channel.connect();
            //??????????????????????????????????????????
            input = new BufferedReader(new InputStreamReader(channel.getInputStream()));
            String line = null;
            while ((line = input.readLine()) != null) {
                if (line.contains(INSTALL_FAILED)) {
                    throw new DeploymentException("??????????????????,???????????????????????????!!!");
                }
                saveLogLine(logId, stdout, line);
            }
            input.close();
        } catch (IOException e) {
            throw new IORuntimeException(e);
        } catch (JSchException e) {
            throw new JschRuntimeException(e);
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

    public void setApplicationState(String appId, AppStatusEnum stateEnum, boolean isClient) {
        if(isClient){
            ClientInfo clientInfo = ClientInfo.builder().id(appId).status(stateEnum.getDesc()).build();
            clientInfoService.updateById(clientInfo);
        }else {
            ApplicationInfo entity = ApplicationInfo.builder().applicationId(appId).status(stateEnum.getDesc()).build();
            applicationInfoService.updateById(entity);
        }
    }

    /**
     * ?????????????????????????????????
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
