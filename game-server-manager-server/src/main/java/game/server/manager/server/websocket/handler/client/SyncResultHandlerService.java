package game.server.manager.server.websocket.handler.client;

import game.server.manager.common.constant.MessageTypeConstants;
import game.server.manager.common.mode.socket.ClientMessage;
import game.server.manager.server.util.server.SessionUtils;
import game.server.manager.server.websocket.SocketSessionCache;
import game.server.manager.server.websocket.handler.AbstractHandlerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.websocket.Session;
import java.util.Objects;

/**
 * @author yuzhanfeng
 * @Date 2022/11/26 23:29
 * @Description 客户端返回的连续数据的处理服务
 */
@Slf4j
@Service(MessageTypeConstants.SYNC_RESULT)
public class SyncResultHandlerService implements AbstractHandlerService<ClientHandlerData> {

    @Override
    public Void handler(ClientHandlerData clientHandlerData) {
        ClientMessage clientMessage = clientHandlerData.getClientMessage();
        Session session = clientHandlerData.getSession();
        //寻找游览器session
        Session browserSession = SocketSessionCache.getBrowserSessionByMessageId(clientMessage.getMessageId());
        //向游览器转发消息
        if (Objects.nonNull(browserSession)) {
            SessionUtils.sendOkServerMessage(browserSession,browserSession.getId(),clientMessage.getData());
        } else {
            log.info("【websocket消息】游览器会话不存在或已结束,放弃消息转发。{}", session.getId());
        }
        return null;
    }
}
