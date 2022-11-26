package game.server.manager.server.websocket.handler;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.alibaba.fastjson2.JSON;
import game.server.manager.common.exception.ExceptionFactory;
import game.server.manager.common.mode.socket.ClientMessage;
import game.server.manager.handler.HandlerServiceContainer;
import game.server.manager.handler.Void;
import game.server.manager.server.websocket.handler.client.ClientHandlerData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.websocket.Session;

/**
 * @author yuzhanfeng
 * @Date 2022/11/24 16:29
 * @Description 客户端消息处理
 */
@Slf4j
@Component
public class ClientMessageHandler {

    @Resource
    private HandlerServiceContainer<ClientHandlerData, Void> handlerServiceContainer;

    public void handle(String message, Session session) {
        ClientMessage clientMessage;
        try{
            clientMessage = JSON.parseObject(message, ClientMessage.class);
        }catch (Exception exception){
            throw ExceptionFactory.bizException(ExceptionUtil.getMessage(exception));
        }
        String type = clientMessage.getType();
        ClientHandlerData clientHandlerData = ClientHandlerData.builder()
                .session(session)
                .clientMessage(clientMessage)
                        .build();
        handlerServiceContainer.handler(type,clientHandlerData);
    }

}
