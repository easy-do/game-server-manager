package game.server.manager.server.websocket.handler.client;

import game.server.manager.common.constant.MessageTypeConstants;
import game.server.manager.common.mode.socket.ClientMessage;
import game.server.manager.server.websocket.SessionResultCache;
import game.server.manager.server.websocket.handler.AbstractHandlerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author yuzhanfeng
 * @Date 2022/11/26 23:29
 * @Description 客户端返回的非同步消息处理服务
 */
@Slf4j
@Service(MessageTypeConstants.INSTALL_APPLICATION_RESULT)
public class InstallApplicationResultHandlerService implements AbstractHandlerService<ClientHandlerData> {

    @Override
    public Void handler(ClientHandlerData clientHandlerData) {
        ClientMessage clientMessage = clientHandlerData.getClientMessage();
        String messageId = clientMessage.getMessageId();

        return null;
    }
}
