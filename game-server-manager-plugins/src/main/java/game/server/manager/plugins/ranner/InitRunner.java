package game.server.manager.plugins.ranner;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.RuntimeUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import game.server.manager.plugins.config.SystemUtils;
import game.server.manager.common.mode.PluginsData;
import game.server.manager.common.mode.PluginsInitData;
import game.server.manager.common.mode.SyncData;
import game.server.manager.plugins.server.SyncServer;
import game.server.manager.plugins.server.PluginsDataServer;
import game.server.manager.common.result.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;


/**
 * @author laoyu
 * @version 1.0
 */
@Component
public class InitRunner implements ApplicationRunner {

    Logger logger = LoggerFactory.getLogger(InitRunner.class);
    @Autowired
    private PluginsDataServer pluginsDataServer;
    @Autowired
    private SyncServer syncServer;

    @Autowired
    private SystemUtils systemUtils;

    @Override
    public void run(ApplicationArguments args) {
        String applicationId = systemUtils.getApplicationId();
        try {
            //获取app初始化信息
            R<String> initResult = syncServer.sync(SyncData.builder().key("pluginsInitData").applicationId(applicationId).build());
            PluginsInitData initData = JSON.parseObject(initResult.getData(),PluginsInitData.class);
            systemUtils.init(initData);
            //获取本地信息
            PluginsData pluginsData = pluginsDataServer.getPluginsData();
            R<String> syncResult = syncServer.sync(SyncData.builder()
                    .key("pluginsData")
                    .applicationId(applicationId)
                    .data(systemUtils.encrypt(JSONObject.toJSONString(pluginsData)))
                    .encryption(true).build());
            logger.info(syncResult.getData());
            //执行启动脚本
            if(CharSequenceUtil.isNotEmpty(initData.getStartCmd())){
                logger.info("执行启动命令");
                String result = RuntimeUtil.execForStr(CharsetUtil.CHARSET_UTF_8, initData.getStartCmd());
                logger.info("执行结果:{}",result);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            logger.error(ExceptionUtil.getMessage(exception));
            logger.error("应用 {} 启动失败 , 错误信息{} , 请联系作者寻求帮助：https://push.easydo.plus", applicationId,ExceptionUtil.getMessage(exception));
            System.exit(1000);
        }
    }
}
