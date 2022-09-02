package game.server.manager.client.thread;

import com.alibaba.fastjson2.JSONObject;
import game.server.manager.client.server.ClientMessageServer;
import game.server.manager.client.server.ClientDeploymentServer;
import game.server.manager.common.application.DeployParam;
import game.server.manager.common.enums.ClientMessageStatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * @author laoyu
 * @version 1.0
 * @date 2022/8/7
 */
@Component
public class ConsumeClientMessageThread {

    private volatile boolean consumeFlag = false;

    Logger logger = LoggerFactory.getLogger(ConsumeClientMessageThread.class);

    @Autowired
    private ClientMessageServer clientMessageServer;

    @Autowired
    private ClientDeploymentServer clientDeploymentServer;


    /**
     * 消费客户端消息
     *
     * @author laoyu
     * @date 2022/8/7
     */
    @Scheduled(fixedDelay = 1000 * 10)
    public void consumeMessage(){
        if(!consumeFlag){
            consumeFlag = true;
            ClientMessageServer.MESSAGE_CACHE.forEach((messageId, clientMessageVo) -> {
                if(clientMessageVo.getStatus() == ClientMessageStatusEnum.ACCEPT.getCode()){
                    logger.info("消费客户端消息,消息id:{},消息类型:{}",clientMessageVo.getClientId(),clientMessageVo.getMessageType());
                    clientMessageVo.setStatus(ClientMessageStatusEnum.CONSUME.getCode());
                    clientMessageServer.messageCallBack(Collections.singletonList(clientMessageVo));
                    String message = clientMessageVo.getMessage();
                    DeployParam deployParam = JSONObject.parseObject(message, DeployParam.class);
                    clientDeploymentServer.deployment(deployParam);
                }
            });
            consumeFlag = false;
        }
    }
}
