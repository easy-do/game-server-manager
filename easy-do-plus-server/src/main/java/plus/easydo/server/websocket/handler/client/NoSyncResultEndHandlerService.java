package plus.easydo.server.websocket.handler.client;

import plus.easydo.common.constant.MessageTypeConstants;
import plus.easydo.common.mode.socket.ClientMessage;
import plus.easydo.server.websocket.SessionResultCache;
import plus.easydo.server.websocket.handler.AbstractHandlerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author yuzhanfeng
 * @Date 2022/11/26 23:29
 * @Description 客户端返回的非同步消息处理服务
 */
@Slf4j
@Service(MessageTypeConstants.NO_SYNC_RESULT)
public class NoSyncResultEndHandlerService implements AbstractHandlerService<ClientHandlerData> {

    @Override
    public Void handler(ClientHandlerData clientHandlerData) {
        ClientMessage clientMessage = clientHandlerData.getClientMessage();
        String messageId = clientMessage.getMessageId();
        SessionResultCache.saveMessageById(messageId,clientMessage);
        return null;
    }
}
