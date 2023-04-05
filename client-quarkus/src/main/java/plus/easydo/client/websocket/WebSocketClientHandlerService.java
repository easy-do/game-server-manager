package plus.easydo.client.websocket;

import lombok.extern.slf4j.Slf4j;
import plus.easydo.client.model.socket.ServerMessage;
import plus.easydo.client.websocket.handler.AbstractHandlerService;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import java.util.Map;
import java.util.Objects;

/**
 * @author laoyu
 * @version 1.0
 * @description websocket处理
 * @date 2023/3/26
 */
@Slf4j
@ApplicationScoped
public class WebSocketClientHandlerService {

    @Inject
    Instance<AbstractHandlerService> instance;


    public void handler(ServerMessage serverMessage) {
//        AbstractHandlerService handlerService = handlerContainer.get(serverMessage.getType());
//        if(Objects.isNull(handlerService)){
//            log.warn("{} handler not found",serverMessage.getType());
//        }else {
//            handlerService.handler(serverMessage);
//        }
    }

}
