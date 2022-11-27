package game.server.manager.client.websocket.handler;

import game.server.manager.client.websocket.SessionMessageCache;
import game.server.manager.common.constant.MessageTypeConstants;
import game.server.manager.common.mode.socket.ServerMessage;
import game.server.manager.handler.AbstractHandlerService;
import game.server.manager.handler.annotation.HandlerService;
import lombok.extern.slf4j.Slf4j;


/**
 * @author yuzhanfeng
 * @Date 2022/11/26 23:29
 * @Description 取消当前同步请求
 */
@Slf4j
@HandlerService(MessageTypeConstants.CANCEL_SYNC)
public class CanCelSyncHandlerService extends AbstractHandlerService<ServerMessage, Void> {


    @Override
    public Void handler(ServerMessage serverMessage) {
        log.info("服务端取消当前同步请求");
        String browserId = serverMessage.getMessageId();
        SessionMessageCache.cancelSync(browserId);
        return null;
    }
}
