package game.server.manager.server.websocket.handler.client;

import game.server.manager.common.constant.MessageTypeConstants;
import game.server.manager.common.mode.socket.ClientMessage;
import game.server.manager.handler.AbstractHandlerService;
import game.server.manager.handler.annotation.HandlerService;
import game.server.manager.server.websocket.SessionResultCache;
import lombok.extern.slf4j.Slf4j;

/**
 * @author yuzhanfeng
 * @Date 2022/11/26 23:29
 * @Description 客户端返回连续数据结束时的处理服务
 */
@Slf4j
@HandlerService(MessageTypeConstants.NO_SYNC_RESULT)
public class NoSyncResultEndHandlerService extends AbstractHandlerService<ClientHandlerData, Void> {

    @Override
    public Void handler(ClientHandlerData clientHandlerData) {
        ClientMessage clientMessage = clientHandlerData.getClientMessage();
        String messageId = clientMessage.getMessageId();
        SessionResultCache.saveMessageById(messageId,clientMessage);
        return null;
    }
}
