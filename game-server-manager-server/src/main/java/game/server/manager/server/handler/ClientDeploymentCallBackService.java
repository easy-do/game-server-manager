package game.server.manager.server.handler;

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
import game.server.manager.common.vo.ScriptDataVo;
import game.server.manager.event.BasePublishEventServer;
import game.server.manager.handler.AbstractHandlerService;
import game.server.manager.handler.annotation.HandlerService;
import game.server.manager.server.entity.ScriptData;
import game.server.manager.server.entity.ClientInfo;
import game.server.manager.server.entity.ExecuteLog;
import game.server.manager.server.service.AppEnvInfoService;
import game.server.manager.server.service.AppInfoService;
import game.server.manager.server.service.ScriptDataService;
import game.server.manager.server.service.ClientInfoService;
import game.server.manager.server.service.ExecuteLogService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author yuzhanfeng
 */
@HandlerService("clientDeploymentCallBack")
public class ClientDeploymentCallBackService extends AbstractHandlerService<SyncData,Object> {

    @Autowired
    private ClientInfoService clientInfoService;

    @Autowired
    private AppInfoService appInfoService;

    @Autowired
    private ExecuteLogService executeLogService;

    @Autowired
    private ScriptDataService scriptDataService;

    @Autowired
    private AppEnvInfoService appEnvInfoService;

    @Autowired
    private BasePublishEventServer basePublishEventServer;

    @Override
    public Object handler(SyncData syncData) {
        String clientId = syncData.getClientId();
        String data = syncData.getData();
        Boolean encryption = syncData.getEncryption();
        try {
            if (CharSequenceUtil.isEmpty(clientId)) {
                return DataResult.fail("客户端标识为空。");
            }
            ClientInfo dbClientInfo = clientInfoService.getById(clientId);
            if (Objects.isNull(dbClientInfo)) {
                return DataResult.fail("客户端不存在。");
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
            logger.error("接收客户端部署回调发生异常{}", ExceptionUtil.getMessage(exception));
            return DataResult.fail(ExceptionUtil.getMessage(exception));
        }
        return DataResult.fail();
    }

    private R<Object> deploymentCallBackTypeFinish(DeploymentCallBackData deploymentCallBackData) {
        ScriptData scriptData = scriptDataService.getById(deploymentCallBackData.getScriptId());
        if (Objects.isNull(scriptData)) {
            return DataResult.fail("脚本已不存在。");
        }
        //设置日志状态
        ExecuteLogModal executeLogModal = deploymentCallBackData.getExecuteLogModal();
        executeLogService.updateById(ExecuteLog.builder()
                .id(executeLogModal.getId())
                .startTime(executeLogModal.getStartTime())
                .endTime(executeLogModal.getEndTime())
                .executeState(AppStatusEnum.FINISH.getDesc()).logData(executeLogModal.getLogData()).build());
        //发送执行完毕通知
//        String deviceId = deploymentCallBackData.getDeviceId();
//        basePublishEventServer.publishScriptEndEvent(application.getUserId(), scriptData.getScriptName());
        return DataResult.ok();
    }

    private R<Object> deploymentCallBackTypeException(DeploymentCallBackData deploymentCallBackData) {
//        if(!deploymentCallBackData.isUninstall()){
//            // 执行异常 设置应用状态
//            String applicationId = deploymentCallBackData.getDeviceId();
//            ApplicationInfo application = applicationInfoService.getById(applicationId);
//            if (Objects.isNull(application)) {
//                return DataResult.fail("应用已不存在。");
//            }
//            //设置应用状态
//            application.setStatus(AppStatusEnum.DEPLOYMENT_FAILED.getDesc());
//            applicationInfoService.updateById(application);
//        }
        //设置日志状态
        ExecuteLogModal executeLogModal = deploymentCallBackData.getExecuteLogModal();
        executeLogService.updateById(ExecuteLog.builder()
                .id(executeLogModal.getId())
                .executeState(AppStatusEnum.FAILED.getDesc()).logData(executeLogModal.getLogData()).build());
        return DataResult.ok();
    }

    private R<Object> deploymentCallBackTypeBefore(DeploymentCallBackData deploymentCallBackData, String clientId) {
        //设置日志为部署中
        ExecuteLogModal executeLogModal = deploymentCallBackData.getExecuteLogModal();
        executeLogService.updateById(ExecuteLog.builder()
                .id(executeLogModal.getId())
                .executeState(AppStatusEnum.DEPLOYMENT_ING.getDesc()).logData(executeLogModal.getLogData()).build());
        //构建返回部署数据
        ClientDeployData clientDeployData = ClientDeployData.builder().build();
        //构建主脚本
        ScriptDataVo scriptDataVo = getAppScriptVo(deploymentCallBackData.getScriptId());
        clientDeployData.setAppScript(scriptDataVo);
        //构建基础脚本
        List<ScriptDataVo> appScriptList = getBasicAppScriptVo(scriptDataVo);
        clientDeployData.setBasicScript(BeanUtil.copyToList(appScriptList, ScriptDataVo.class));
        if(!deploymentCallBackData.isUninstall()){
//            //如果不是卸载脚本
//            // 执行前回调 获取部署数据 设置日志为部署中 设置应用状态为部署中 增加应用热度
//            ApplicationInfo application = applicationInfoService.getById(deploymentCallBackData.getDeviceId());
//            if (Objects.isNull(application)) {
//                return DataResult.fail("应用已不存在。");
//            }
//            AppInfo appInfo = appInfoService.getById(application.getAppId());
//            if (Objects.isNull(appInfo)) {
//                return DataResult.fail("APP已不存在。");
//            }
//            //增加热度
//            appInfoService.addHeat(appInfo.getId());
//            clientDeployData.setAppInfo(BeanUtil.copyProperties(appInfo, AppInfoVo.class));
//            //发送执行开始通知
//            basePublishEventServer.publishScriptStartEvent(application.getUserId(), scriptDataVo.getId());
        }else {
            //发送删除客户端消息
            basePublishEventServer.publishClientUninstallEvent(clientId);
        }

        return DataResult.ok(clientDeployData);
    }


    private ScriptDataVo getAppScriptVo(String appScriptId){
        ScriptData scriptData = scriptDataService.getById(appScriptId);
        ScriptDataVo scriptDataVo = BeanUtil.copyProperties(scriptData, ScriptDataVo.class);
        List<AppEnvInfoVo> envInfoVoList = appEnvInfoService.getVoListByScriptId(scriptData.getId());
        scriptDataVo.setScriptEnv(envInfoVoList);
        return scriptDataVo;
    }

    private List<ScriptDataVo> getBasicAppScriptVo(ScriptDataVo scriptDataVo){
        String basicScriptStr = scriptDataVo.getBasicScript();
        List<ScriptDataVo> appScriptList = new ArrayList<>();
        if (CharSequenceUtil.isNotEmpty(basicScriptStr) && basicScriptStr.length() > 0) {
            String[] basicScriptIds = basicScriptStr.split(",");
            for (String basicScriptId: basicScriptIds) {
                ScriptData basicScript = scriptDataService.getById(basicScriptId);
                List<AppEnvInfoVo> basicScriptEnvInfoVoList = appEnvInfoService.getVoListByScriptId(scriptDataVo.getId());
                ScriptDataVo basicScriptVo = BeanUtil.copyProperties(basicScript, ScriptDataVo.class);
                basicScriptVo.setScriptEnv(basicScriptEnvInfoVoList);
                appScriptList.add(basicScriptVo);
            }
        }
        return appScriptList;
    }
}
