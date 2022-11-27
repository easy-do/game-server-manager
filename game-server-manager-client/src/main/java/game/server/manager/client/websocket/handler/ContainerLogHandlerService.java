package game.server.manager.client.websocket.handler;

import com.alibaba.fastjson2.JSON;
import game.server.manager.client.service.DockerContainerService;
import game.server.manager.common.constant.MessageTypeConstants;
import game.server.manager.common.mode.socket.ServerContainerLogMessage;
import game.server.manager.common.mode.socket.ServerMessage;
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
@HandlerService(MessageTypeConstants.CONTAINER_LOG)
public class ContainerLogHandlerService extends AbstractHandlerService<ServerMessage, Void> {


    @Resource
    private DockerContainerService dockerContainerService;

    @Override
    public Void handler(ServerMessage serverMessage) {
        log.info("OnMessageHandler containerLog ==> {}",serverMessage);
        String jsonData = serverMessage.getData();
        ServerContainerLogMessage containerLogMessage = JSON.parseObject(jsonData, ServerContainerLogMessage.class);
        dockerContainerService.logContainer(serverMessage.getMessageId(),containerLogMessage.getContainerId());
        return null;
    }
}
