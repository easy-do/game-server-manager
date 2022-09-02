package game.server.manager.client.ranner;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import game.server.manager.client.config.SystemUtils;
import game.server.manager.common.mode.ClientData;
import game.server.manager.common.mode.ClientInitData;
import game.server.manager.common.mode.SyncData;
import game.server.manager.client.server.SyncServer;
import game.server.manager.client.server.ClientDataServer;
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
    private ClientDataServer clientDataServer;
    @Autowired
    private SyncServer syncServer;

    @Autowired
    private SystemUtils systemUtils;

    @Override
    public void run(ApplicationArguments args) {
        String clientId = systemUtils.getClientId();
        try {
            //获取app初始化信息
            R<String> initResult = syncServer.sync(SyncData.builder().key("clientInitData").clientId(clientId).build());
            ClientInitData initData = JSON.parseObject(initResult.getData(), ClientInitData.class);
            systemUtils.init(initData);
            //获取本地信息
            ClientData clientData = clientDataServer.getClientData();
            R<String> syncResult = syncServer.sync(SyncData.builder()
                    .key("clientData")
                    .clientId(clientId)
                    .data(systemUtils.encrypt(JSONObject.toJSONString(clientData)))
                    .encryption(true).build());
            logger.info(syncResult.getData());
        } catch (Exception exception) {
            exception.printStackTrace();
            logger.error(ExceptionUtil.getMessage(exception));
            logger.error("客户端 {} 启动失败 , 错误信息{} , 请联系作者寻求帮助：https://push.easydo.plus", clientId,ExceptionUtil.getMessage(exception));
            System.exit(1000);
        }
    }
}
