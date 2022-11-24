package game.server.manager.client.websocket.handler;

import com.alibaba.fastjson2.JSON;
import game.server.manager.client.service.DockerImageService;
import game.server.manager.common.enums.ServerMessageTypeEnum;
import game.server.manager.common.mode.socket.ServerMessage;
import game.server.manager.common.mode.socket.ServerPullImageMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author laoyu
 * @version 1.0
 * @description 服务端消息处理类
 * @date 2022/11/22
 */
@Slf4j
@Component
public class OnMessageHandler {


    @Resource
    private DockerImageService dockerImageService;

    /**
     * 处理服务端发来的消息
     *
     * @param serverMessage serverMessage
     * @author laoyu
     * @date 2022/11/22
     */
    public void handler(ServerMessage serverMessage) {
        if(ServerMessageTypeEnum.HEARTBEAT.getType().equals(serverMessage.getType())){
            log.info("心跳成功");
        }
       if(ServerMessageTypeEnum.PULL_IMAGE.getType().equals(serverMessage.getType())){
           log.info("OnMessageHandler pull ==> {}",serverMessage);
           String jsonData = serverMessage.getJsonData();
           ServerPullImageMessage pullImageMessage = JSON.parseObject(jsonData, ServerPullImageMessage.class);
           dockerImageService.pullImage(serverMessage.getMessageId(),pullImageMessage.getRepository());
       }
    }


}
