package game.server.manager.server.websocket.handler.client;

import com.alibaba.fastjson2.JSON;
import game.server.manager.common.constant.MessageTypeConstants;
import game.server.manager.common.enums.ServerMessageTypeEnum;
import game.server.manager.common.mode.socket.ClientMessage;
import game.server.manager.common.mode.socket.ServerMessage;
import game.server.manager.handler.AbstractHandlerService;
import game.server.manager.handler.annotation.HandlerService;
import game.server.manager.server.util.SessionUtils;
import game.server.manager.server.websocket.SocketSessionCache;
import lombok.extern.slf4j.Slf4j;

import javax.websocket.Session;

/**
 * @author yuzhanfeng
 * @Date 2022/11/26 23:29
 * @Description 客户端返回的锁定消息处理服务
 */
@Slf4j
@HandlerService(MessageTypeConstants.LOCK)
public class LockMessageHandlerService extends AbstractHandlerService<ClientHandlerData, Void> {

    @Override
    public Void handler(ClientHandlerData clientHandlerData) {
        ClientMessage<String> clientMessage = clientHandlerData.getClientMessage();
        Session session = clientHandlerData.getSession();
        //寻找游览器session
        Session browserSession = SocketSessionCache.getBrowserSessionByClientSessionId(session.getId());
        //向游览器转发消息
        assert browserSession != null;
        SessionUtils.sendOkServerMessage(browserSession, browserSession.getId(), clientMessage.getData());
        SessionUtils.close(browserSession);
        return null;
    }
}
