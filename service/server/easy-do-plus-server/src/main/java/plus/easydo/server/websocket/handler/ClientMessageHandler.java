package plus.easydo.server.websocket.handler;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.json.JSONUtil;
import plus.easydo.common.exception.ExceptionFactory;
import plus.easydo.common.mode.socket.ClientMessage;
import plus.easydo.server.websocket.handler.client.ClientHandlerData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.Session;
import java.util.Map;

/**
 * @author yuzhanfeng
 * @Date 2022/11/24 16:29
 * @Description 客户端消息处理
 */
@Slf4j
@Component
public class ClientMessageHandler {

    @Autowired
    private Map<String, AbstractHandlerService<ClientHandlerData>> handlerServiceContainer;


    public void handle(String message, Session session) {
        ClientMessage clientMessage;
        try{
            clientMessage = JSONUtil.toBean(message, ClientMessage.class);
        }catch (Exception exception){
            throw ExceptionFactory.bizException(ExceptionUtil.getMessage(exception));
        }
        String type = clientMessage.getType();
        ClientHandlerData clientHandlerData = ClientHandlerData.builder()
                .session(session)
                .clientMessage(clientMessage)
                        .build();
        handlerServiceContainer.get(type).handler(clientHandlerData);
    }

}
