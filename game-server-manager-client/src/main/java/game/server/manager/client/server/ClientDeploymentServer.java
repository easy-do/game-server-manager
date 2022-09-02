package game.server.manager.client.server;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.RuntimeUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import game.server.manager.client.config.SystemUtils;
import game.server.manager.common.application.DeployParam;
import game.server.manager.common.enums.DeploymentCallBackTypeEnum;
import game.server.manager.common.mode.ClientDeployData;
import game.server.manager.common.mode.DeploymentCallBackData;
import game.server.manager.common.mode.ExecuteLogModal;
import game.server.manager.common.mode.SyncData;
import game.server.manager.common.result.R;
import game.server.manager.common.utils.AppScriptUtils;
import game.server.manager.common.vo.AppInfoVo;
import game.server.manager.common.vo.AppScriptVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Component;

import javax.websocket.DeploymentException;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
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
 * @date 2022/8/7
 */
@Component
public class ClientDeploymentServer {

    Logger logger = LoggerFactory.getLogger(ClientDeploymentServer.class);
    private static final String INSTALL_FAILED = "INSTALL_FAILED";

    @Autowired
    private SystemUtils systemUtils;

    @Autowired
    private SyncServer syncServer;

    private String deploymentCallBack(DeploymentCallBackData deploymentCallBackData) {
        R<String> result = syncServer.sync(
                SyncData.builder().key("clientDeploymentCallBack")
                        .clientId(systemUtils.getClientId()).encryption(true)
                        .data(systemUtils.encrypt(JSON.toJSONString(deploymentCallBackData))).build()
        );
        return result.getData();
    }


    /**
     * 部署应用
     *
     * @param deployParam deployParam
     * @return java.lang.String
     * @author laoyu
     * @date 2022/8/7
     */
    public String deployment(DeployParam deployParam) {
        LocalDateTime startTime = LocalDateTime.now();
        Long logId = Long.valueOf(deployParam.getLogId());
        LinkedList<String> stdout = new LinkedList<>();
        //生成日志
        ExecuteLogModal executeLog = ExecuteLogModal.builder().id(logId).startTime(startTime).build();
        try {
            saveLogLine(stdout, "开始执行脚本");
            executeLog.setLogData(String.join("", stdout));
            DeploymentCallBackData beforeData = DeploymentCallBackData.builder()
                    .applicationId(deployParam.getApplicationId())
                    .appScriptId(deployParam.getAppScriptId())
                    .executeLogModal(executeLog)
                    .uninstall(deployParam.isUninstall())
                    .type(DeploymentCallBackTypeEnum.BEFORE.getType()).build();
            String resultStr = deploymentCallBack(beforeData);

            ClientDeployData clientDeployData = JSON.to(ClientDeployData.class, resultStr);
            //构建并执行脚本
            extractedBasicScript(deployParam, clientDeployData, stdout);
            //执行主脚本
            buildAndExecScript(deployParam, clientDeployData.getAppInfo(), clientDeployData.getAppScript(), stdout);
            saveLogLine(stdout, "------------------流程执行完毕--------------------");
            executeLog.setEndTime(LocalDateTime.now());
            executeLog.setLogData(String.join("", stdout));
            deploymentCallBack(DeploymentCallBackData.builder()
                    .applicationId(deployParam.getApplicationId())
                    .appScriptId(deployParam.getAppScriptId())
                    .executeLogModal(executeLog)
                    .uninstall(deployParam.isUninstall())
                    .type(DeploymentCallBackTypeEnum.FINISH.getType()).build());
        } catch (Exception e) {
            saveLogLine(stdout, ExceptionUtil.getMessage(e));
            executeLog.setLogData(String.join("", stdout));
            deploymentCallBack(DeploymentCallBackData.builder()
                    .applicationId(deployParam.getApplicationId())
                    .appScriptId(deployParam.getAppScriptId())
                    .uninstall(deployParam.isUninstall())
                    .executeLogModal(executeLog).type(DeploymentCallBackTypeEnum.EXCEPTION.getType()).build());
            return String.join("", stdout);
        }
        return executeLog.getLogData();
    }

    private void extractedBasicScript(DeployParam deployParam, ClientDeployData clientDeployData, LinkedList<String> stdout) throws DeploymentException {
        List<AppScriptVo> basicScript = clientDeployData.getBasicScript();
        if (Objects.nonNull(basicScript) && !basicScript.isEmpty()) {
            AppInfoVo appInfoVo = clientDeployData.getAppInfo();
            saveLogLine(stdout, "------------------检测到脚本依赖，先执行依赖的脚本--------------------");
            for (AppScriptVo appScriptVo : basicScript) {
                buildAndExecScript(deployParam, appInfoVo, appScriptVo, stdout);
            }
            saveLogLine(stdout, "------------------主脚本依赖的脚本执行完毕--------------------");
        }
    }

    /**
     * 构建并执行脚本
     *
     * @param deployParam deployParam
     * @param stdout      stdout
     * @author laoyu
     * @date 2022/8/7
     */
    private void buildAndExecScript(DeployParam deployParam, AppInfoVo appInfoVo, AppScriptVo appScriptVo, List<String> stdout) throws DeploymentException {
        String scriptName = appScriptVo.getScriptName();
        String fileName = UUID.randomUUID() + ".sh";
        String filePath = getJarFilePath() + "/" + fileName;
        File file = null;
        try {
            //1.构建脚本
            saveLogLine(stdout, "------------------构建脚本《" + scriptName + "》--------------------");
            String scriptStr = appScriptVo.getScriptFile();
            if (CharSequenceUtil.isEmpty(scriptStr)) {
                throw new DeploymentException("脚本《" + scriptName + "》内容为空,请编写脚本后再执行!!!");
            }
            scriptStr = scriptStr.replace(UNIX_LINE_END_CHAR, WINDOW_LINE_END_CHAR);
            // 在本地生成脚本文件
            file = FileUtil.file(filePath);
            BufferedOutputStream opt = FileUtil.getOutputStream(file);
            IoUtil.writeUtf8(opt, true, scriptStr);
            IoUtil.close(opt);
            saveLogLine(stdout, "------------------脚本《" + scriptName + "》构建完毕--------------------");
            //2.执行脚本
            saveLogLine(stdout, "-----开始运行脚本《" + scriptName + "》,跑完脚本后会刷新详细日志,耐心等待------");
            exec("chmod -R 777 " + filePath, stdout);
            String execStr = "sh " + filePath;
            StringBuilder stringBuilder = new StringBuilder(execStr);
            JSONObject env = deployParam.getEnv();
            env.put("APPLICATION_ID", deployParam.getApplicationId());
            env.put("APP_ID", Objects.nonNull(appInfoVo)? appInfoVo.getId():"");
            env.put("APP_VERSION", Objects.nonNull(appInfoVo)? appInfoVo.getVersion():"");
            env.put("CLIENT_VERSION", systemUtils.getVersion());
            String execShellEnv = AppScriptUtils.generateExecShellEnvs(env,appScriptVo.getScriptEnv());
            stringBuilder.append(" ").append(execShellEnv);
            exec(stringBuilder.toString(), stdout);
            saveLogLine(stdout, "------------------脚本《" + scriptName + "》运行完毕--------------------");
            saveLogLine(stdout, "注意：如果没有脚本的详细安装信息可能代表运行失败。");
        } catch (Exception e) {
            throw new DeploymentException(ExceptionUtil.getMessage(e));
        } finally {
            //3.删除脚本文件
            FileUtil.del(file);
        }
    }

    public void exec(String cmd, List<String> stdout) throws DeploymentException {
        try {
            String line = RuntimeUtil.execForStr(cmd);
            saveLogLine(stdout, line);
        } catch (Exception e) {
            throw new DeploymentException(ExceptionUtil.getMessage(e));
        }
    }


    public void saveLogLine(List<String> stdout, String log) {
        if (!CharSequenceUtil.endWith(log, WINDOW_LINE_END_CHAR)) {
            log = log + WINDOW_LINE_END_CHAR;
        }
        LocalDateTime now = LocalDateTime.now();
        String prefix = DateUtil.format(now, DatePattern.NORM_DATETIME_PATTERN) + ": ";
        log = prefix + log;
        logger.info(log);
        stdout.add(log);
    }


    private String getJarFilePath() {
        ApplicationHome home = new ApplicationHome(getClass());
        File jarFile = home.getSource();
        return jarFile.getParentFile().toString();
    }
}
