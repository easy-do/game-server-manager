package game.server.manager.plugins.thread;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.RuntimeUtil;
import com.alibaba.fastjson2.JSON;
import game.server.manager.plugins.config.SystemUtils;
import game.server.manager.common.mode.PluginsData;
import game.server.manager.common.mode.SyncData;
import game.server.manager.plugins.server.SyncServer;
import game.server.manager.plugins.server.PluginsDataServer;
import game.server.manager.common.result.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


/**
 * @author laoyu
 * @version 1.0
 */
@Component
public class PluginsUpThread {

    Logger logger = LoggerFactory.getLogger(PluginsUpThread.class);
    @Autowired
    private PluginsDataServer pluginsDataServer;

    @Autowired
    private SyncServer syncServer;

    @Autowired
    private SystemUtils systemUtils;

    private int errCount = 0;

    @Scheduled(fixedDelay = 1000 * 300)
    public void serverUp() {
        PluginsData pluginsData = pluginsDataServer.getPluginsData();
        try {
            String data = JSON.toJSONString(pluginsData);
            R<String> result = syncServer.sync(SyncData.builder().key("up").applicationId(pluginsData.getApplicationId()).encryption(true).data(systemUtils.encrypt(data)).build());
            logger.info("up, {}", result.getMsg());
        } catch (Exception exception) {
            errCount++;
            logger.error("心跳通信失败,{}, {}",errCount, ExceptionUtil.getMessage(exception));
//            if(errCount > 6){
//                logger.error("心跳通信失败超过六次,结束程序 {}",ExceptionUtil.getMessage(exception));
//                String stopCmd = systemUtils.getPluginsInitData().getStopCmd();
//                if(CharSequenceUtil.isNotEmpty(stopCmd)){
//                    RuntimeUtil.execForStr(CharsetUtil.CHARSET_UTF_8, stopCmd);
//                }
//                System.exit(1000);
//            }
        }
    }
}
