package game.server.manager.server.server;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.alibaba.fastjson2.JSON;
import game.server.manager.common.enums.AppStatusEnum;
import game.server.manager.common.enums.DeploymentCallBackTypeEnum;
import game.server.manager.common.enums.ScriptTypeEnum;
import game.server.manager.common.mode.ClientDeployData;
import game.server.manager.common.mode.DeploymentCallBackData;
import game.server.manager.common.mode.ExecuteLogModal;
import game.server.manager.common.mode.SyncData;
import game.server.manager.common.result.DataResult;
import game.server.manager.common.result.R;
import game.server.manager.common.vo.AppEnvInfoVo;
import game.server.manager.common.vo.AppInfoVo;
import game.server.manager.common.vo.AppScriptVo;
import game.server.manager.event.BasePublishEventServer;
import game.server.manager.server.annotation.SyncServerClass;
import game.server.manager.server.entity.AppInfo;
import game.server.manager.server.entity.AppScript;
import game.server.manager.server.entity.ApplicationInfo;
import game.server.manager.server.entity.ClientInfo;
import game.server.manager.server.entity.ExecuteLog;
import game.server.manager.server.service.AppEnvInfoService;
import game.server.manager.server.service.AppInfoService;
import game.server.manager.server.service.AppScriptService;
import game.server.manager.server.service.ApplicationInfoService;
import game.server.manager.server.service.ClientInfoService;
import game.server.manager.server.service.ExecuteLogService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author yuzhanfeng
 */
@SyncServerClass("clientDeploymentCallBack")
public class ClientDeploymentCallBackService extends AbstractDefaultServer {

    @Autowired
    private ClientInfoService clientInfoService;

    @Autowired
    private ApplicationInfoService applicationInfoService;

    @Autowired
    private AppInfoService appInfoService;

    @Autowired
    private ExecuteLogService executeLogService;

    @Autowired
    private AppScriptService appScriptService;

    @Autowired
    private AppEnvInfoService appEnvInfoService;

    @Autowired
    private BasePublishEventServer basePublishEventServer;

    @Override
    Object processData(SyncData syncData) {
        String clientId = syncData.getClientId();
        String data = syncData.getData();
        Boolean encryption = syncData.getEncryption();
        try {
            if (CharSequenceUtil.isEmpty(clientId)) {
                return DataResult.fail("????????????????????????");
            }
            ClientInfo dbClientInfo = clientInfoService.getById(clientId);
            if (Objects.isNull(dbClientInfo)) {
                return DataResult.fail("?????????????????????");
            }
            if (encryption) {
                RSA rsa = new RSA(dbClientInfo.getPrivateKey(), null);
                data = StrUtil.str(rsa.decrypt(Base64.decode(data), KeyType.PrivateKey), CharsetUtil.CHARSET_UTF_8);
            }
            DeploymentCallBackData deploymentCallBackData = JSON.to(DeploymentCallBackData.class, data);
            String type = deploymentCallBackData.getType();
            if (type.equals(DeploymentCallBackTypeEnum.BEFORE.getType())) {
                return deploymentCallBackTypeBefore(deploymentCallBackData,clientId);
            }
            if (type.equals(DeploymentCallBackTypeEnum.EXCEPTION.getType())) {
                return deploymentCallBackTypeException(deploymentCallBackData);
            }
            if (type.equals(DeploymentCallBackTypeEnum.FINISH.getType())) {
                return deploymentCallBackTypeFinish(deploymentCallBackData);
            }
        } catch (Exception exception) {
            logger.error("???????????????????????????????????????{}", ExceptionUtil.getMessage(exception));
            return DataResult.fail(ExceptionUtil.getMessage(exception));
        }
        return DataResult.fail();
    }

    private R<Object> deploymentCallBackTypeFinish(DeploymentCallBackData deploymentCallBackData) {
        AppScript appScript = appScriptService.getById(deploymentCallBackData.getAppScriptId());
        if (Objects.isNull(appScript)) {
            return DataResult.fail("?????????????????????");
        }
        if(!deploymentCallBackData.isUninstall()){
            // ?????????????????? ??????????????????????????????
            String applicationId = deploymentCallBackData.getApplicationId();
            ApplicationInfo application = applicationInfoService.getById(applicationId);
            if (Objects.isNull(application)) {
                return DataResult.fail("?????????????????????");
            }
            //??????????????????????????????
            setApplicationState(appScript, applicationId);
            //????????????????????????
            basePublishEventServer.publishScriptEndEvent(application.getUserId(), appScript.getScriptName());
        }
        //??????????????????
        ExecuteLogModal executeLogModal = deploymentCallBackData.getExecuteLogModal();
        executeLogService.updateById(ExecuteLog.builder()
                .id(executeLogModal.getId())
                .startTime(executeLogModal.getStartTime())
                .endTime(executeLogModal.getEndTime())
                .executeState(AppStatusEnum.FINISH.getDesc()).logData(executeLogModal.getLogData()).build());
        return DataResult.ok();
    }

    private R<Object> deploymentCallBackTypeException(DeploymentCallBackData deploymentCallBackData) {
        if(!deploymentCallBackData.isUninstall()){
            // ???????????? ??????????????????
            String applicationId = deploymentCallBackData.getApplicationId();
            ApplicationInfo application = applicationInfoService.getById(applicationId);
            if (Objects.isNull(application)) {
                return DataResult.fail("?????????????????????");
            }
            //??????????????????
            application.setStatus(AppStatusEnum.DEPLOYMENT_FAILED.getDesc());
            applicationInfoService.updateById(application);
        }
        //??????????????????
        ExecuteLogModal executeLogModal = deploymentCallBackData.getExecuteLogModal();
        executeLogService.updateById(ExecuteLog.builder()
                .id(executeLogModal.getId())
                .executeState(AppStatusEnum.FAILED.getDesc()).logData(executeLogModal.getLogData()).build());
        return DataResult.ok();
    }

    private R<Object> deploymentCallBackTypeBefore(DeploymentCallBackData deploymentCallBackData, String clientId) {
        //????????????????????????
        ExecuteLogModal executeLogModal = deploymentCallBackData.getExecuteLogModal();
        executeLogService.updateById(ExecuteLog.builder()
                .id(executeLogModal.getId())
                .executeState(AppStatusEnum.DEPLOYMENT_ING.getDesc()).logData(executeLogModal.getLogData()).build());
        //????????????????????????
        ClientDeployData clientDeployData = ClientDeployData.builder().build();
        //???????????????
        AppScriptVo appScriptVo = getAppScriptVo(deploymentCallBackData.getAppScriptId());
        clientDeployData.setAppScript(appScriptVo);
        //??????????????????
        List<AppScriptVo> appScriptList = getBasicAppScriptVo(appScriptVo);
        clientDeployData.setBasicScript(BeanUtil.copyToList(appScriptList, AppScriptVo.class));
        if(!deploymentCallBackData.isUninstall()){
            //????????????????????????
            // ??????????????? ?????????????????? ???????????????????????? ?????????????????????????????? ??????????????????
            ApplicationInfo application = applicationInfoService.getById(deploymentCallBackData.getApplicationId());
            if (Objects.isNull(application)) {
                return DataResult.fail("?????????????????????");
            }
            AppInfo appInfo = appInfoService.getById(application.getAppId());
            if (Objects.isNull(appInfo)) {
                return DataResult.fail("APP???????????????");
            }
            //????????????
            appInfoService.addHeat(appInfo.getId());
            //??????????????????????????????
            application.setStatus(AppStatusEnum.DEPLOYMENT_ING.getDesc());
            applicationInfoService.updateById(application);

            clientDeployData.setAppInfo(BeanUtil.copyProperties(appInfo, AppInfoVo.class));
            //????????????????????????
            basePublishEventServer.publishScriptStartEvent(application.getUserId(), appScriptVo.getId());
        }else {
            //???????????????????????????
            basePublishEventServer.publishClientUninstallEvent(clientId);
        }

        return DataResult.ok(clientDeployData);
    }


    private AppScriptVo getAppScriptVo(String appScriptId){
        AppScript appScript = appScriptService.getById(appScriptId);
        AppScriptVo appScriptVo = BeanUtil.copyProperties(appScript, AppScriptVo.class);
        List<AppEnvInfoVo> envInfoVoList = appEnvInfoService.getVoListByScriptId(appScript.getId());
        appScriptVo.setScriptEnv(envInfoVoList);
        return appScriptVo;
    }

    private List<AppScriptVo> getBasicAppScriptVo(AppScriptVo appScriptVo){
        String basicScriptStr = appScriptVo.getBasicScript();
        List<AppScriptVo> appScriptList = new ArrayList<>();
        if (CharSequenceUtil.isNotEmpty(basicScriptStr) && basicScriptStr.length() > 0) {
            String[] basicScriptIds = basicScriptStr.split(",");
            for (String basicScriptId: basicScriptIds) {
                AppScript basicScript = appScriptService.getById(basicScriptId);
                List<AppEnvInfoVo> basicScriptEnvInfoVoList = appEnvInfoService.getVoListByScriptId(appScriptVo.getId());
                AppScriptVo basicScriptVo = BeanUtil.copyProperties(basicScript, AppScriptVo.class);
                basicScriptVo.setScriptEnv(basicScriptEnvInfoVoList);
                appScriptList.add(basicScriptVo);
            }
        }
        return appScriptList;
    }

    private void setApplicationState(AppScript appScript, String applicationId) {
        String scriptType = appScript.getScriptType();
        if (ScriptTypeEnum.INSTALL.getDesc().contains(scriptType)) {
            setApplicationState(applicationId, AppStatusEnum.DEPLOYMENT_SUCCESS);
        }
        if (ScriptTypeEnum.RESTART.getDesc().contains(scriptType)) {
            setApplicationState(applicationId, AppStatusEnum.RESTART);
        }
        if (ScriptTypeEnum.STOP.getDesc().contains(scriptType)) {
            setApplicationState(applicationId, AppStatusEnum.STOP);
        }
        if (ScriptTypeEnum.UNINSTALL.getDesc().contains(scriptType)) {
            setApplicationState(applicationId, AppStatusEnum.UNINSTALL);
        }
        if (ScriptTypeEnum.CUSTOM.getDesc().contains(scriptType)) {
            setApplicationState(applicationId, AppStatusEnum.CUSTOM);
        }
        if (ScriptTypeEnum.BASIC.getDesc().contains(scriptType)) {
            setApplicationState(applicationId, AppStatusEnum.CUSTOM);
        }
    }

    public void setApplicationState(String appId, AppStatusEnum stateEnum) {
        ApplicationInfo entity = ApplicationInfo.builder().applicationId(appId).status(stateEnum.getDesc()).build();
        applicationInfoService.updateById(entity);
    }
}
