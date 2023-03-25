package game.server.manager.client.websocket.handler;

import com.alibaba.fastjson2.JSON;
import game.server.manager.client.service.DockerImageService;
import game.server.manager.common.constant.MessageTypeConstants;
import game.server.manager.common.mode.socket.ServerMessage;
import game.server.manager.common.mode.socket.ServerPullImageMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author yuzhanfeng
 * @Date 2022/11/26 23:29
 * @Description 服务端心跳处理服务
 */
@Slf4j
@Service(MessageTypeConstants.PULL_IMAGE)
public class PullImageHandlerService implements AbstractHandlerService {

    @Autowired
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
