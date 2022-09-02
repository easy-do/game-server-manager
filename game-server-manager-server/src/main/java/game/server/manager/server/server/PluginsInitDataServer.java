package game.server.manager.server.server;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.text.CharSequenceUtil;
import com.alibaba.fastjson2.JSON;
import game.server.manager.common.mode.PluginsInitData;
import game.server.manager.common.mode.SyncData;
import game.server.manager.common.result.DataResult;
import game.server.manager.server.annotation.SyncServerClass;
import game.server.manager.server.entity.AppInfo;
import game.server.manager.server.entity.ApplicationInfo;
import game.server.manager.server.service.AppInfoService;
import game.server.manager.server.service.ApplicationInfoService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;


/**
 * @author laoyu
 * @version 1.0
 * @date 2022/7/31
 */
@SyncServerClass("pluginsInitData")
public class PluginsInitDataServer extends AbstractDefaultServer {

    @Autowired
    private ApplicationInfoService applicationInfoService;

    @Autowired
    private AppInfoService appInfoService;

    @Override
    Object processData(SyncData syncData) {
        String applicationId = syncData.getApplicationId();
        try{
            if(CharSequenceUtil.isEmpty(applicationId)){
                return DataResult.fail("客户端标识为空。");
            }
            ApplicationInfo application = applicationInfoService.getById(applicationId);
            if(Objects.isNull(application)){
                return DataResult.fail("应用不存在。");
            }
            Long appId = application.getAppId();
            AppInfo appInfo = appInfoService.getById(appId);
            if(Objects.isNull(appInfo)){
                return DataResult.fail("APP已不存在。");
            }
            PluginsInitData initData = PluginsInitData.builder()
                    .applicationId(application.getApplicationId())
                    .applicationName(application.getApplicationName())
                    .appId(appInfo.getId())
                    .appName(appInfo.getAppName())
                    .startCmd(appInfo.getStartCmd())
                    .stopCmd(appInfo.getStopCmd())
                    .configFilePath(appInfo.getConfigFilePath())
                    .publicKey(application.getPublicKey())
                    .build();
            return DataResult.ok(initData);
        }catch (Exception exception){
            logger.error("接收客户端启动数据发生异常{}",JSON.toJSONString(syncData));
            return DataResult.fail("获取初始化数据失败，{}", ExceptionUtil.getMessage(exception));
        }


    }
}
