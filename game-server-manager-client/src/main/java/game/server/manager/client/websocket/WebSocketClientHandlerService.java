package game.server.manager.client.websocket;

import game.server.manager.client.model.socket.ServerMessage;
import game.server.manager.client.websocket.handler.AbstractHandlerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

/**
 * @author laoyu
 * @version 1.0
 * @description websocket处理
 * @date 2023/3/26
 */
@Slf4j
@Component
public class WebSocketClientHandlerService {

    @Autowired
    private Map<String, AbstractHandlerService> handlerContainer;

    public void setHandlerContainer(Map<String, AbstractHandlerService> handlerContainer) {
        this.handlerContainer = handlerContainer;
    }

    public void handler(ServerMessage serverMessage){
        AbstractHandlerService handlerService = handlerContainer.get(serverMessage.getType());
        if(Objects.isNull(handlerService)){
            log.warn("{} handler not found",serverMessage.getType());
        }else {
            handlerService.handler(serverMessage);
        }
    }

}
