package game.server.manager.client.websocket.handler;

import com.alibaba.fastjson2.JSON;
import game.server.manager.client.service.DockerImageService;
import game.server.manager.common.constant.MessageTypeConstants;
import game.server.manager.common.mode.socket.ServerMessage;
import game.server.manager.common.mode.socket.ServerPullImageMessage;
import game.server.manager.handler.AbstractHandlerService;
import game.server.manager.handler.annotation.HandlerService;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;


/**
 * @author yuzhanfeng
 * @Date 2022/11/26 23:29
 * @Description 服务端心跳处理服务
 */
@Slf4j
@HandlerService(MessageTypeConstants.PULL_IMAGE)
public class PullImageHandlerService extends AbstractHandlerService<ServerMessage, Void> {

    @Resource
    private DockerImageService dockerImageService;

    @Override
    public Void handler(ServerMessage serverMessage) {
        log.info("OnMessageHandler pull ==> {}",serverMessage);
        String jsonData = serverMessage.getData();
        ServerPullImageMessage pullImageMessage = JSON.parseObject(jsonData, ServerPullImageMessage.class);
        dockerImageService.pullImage(serverMessage.getMessageId(),pullImageMessage.getRepository());
        return null;
    }
}
