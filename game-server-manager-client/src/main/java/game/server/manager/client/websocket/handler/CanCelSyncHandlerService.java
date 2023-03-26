package game.server.manager.client.websocket.handler;

import game.server.manager.client.contants.MessageTypeConstants;
import game.server.manager.client.model.socket.ServerMessage;
import game.server.manager.client.websocket.SessionMessageCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


/**
 * @author yuzhanfeng
 * @Date 2022/11/26 23:29
 * @Description 取消当前同步请求
 */
@Slf4j
@Service(MessageTypeConstants.CANCEL_SYNC)
public class CanCelSyncHandlerService implements AbstractHandlerService {


    @Override
    public Void handler(ServerMessage serverMessage) {
        log.info("服务端取消当前同步请求");
        String browserId = serverMessage.getMessageId();
        SessionMessageCache.cancelSync(browserId);
        return null;
    }
}
