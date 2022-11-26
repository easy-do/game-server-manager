package game.server.manager.server.websocket.handler.client;

import com.alibaba.fastjson2.JSON;
import game.server.manager.common.constant.MessageTypeConstants;
import game.server.manager.common.enums.ServerMessageTypeEnum;
import game.server.manager.common.mode.socket.ClientMessage;
import game.server.manager.common.mode.socket.ServerMessage;
import game.server.manager.handler.AbstractHandlerService;
import game.server.manager.handler.annotation.HandlerService;
import game.server.manager.server.websocket.SessionUtils;
import game.server.manager.server.websocket.SocketSessionCache;

import javax.websocket.Session;


/**
 * @author yuzhanfeng
 * @Date 2022/11/26 23:29
 * @Description 客户端心跳处理服务
 */
@HandlerService(MessageTypeConstants.HEARTBEAT)
public class HeartbeatHandlerService extends AbstractHandlerService<ClientHandlerData, Void> {

    @Override
    public Void handler(ClientHandlerData clientHandlerData) {
        ClientMessage clientMessage = clientHandlerData.getClientMessage();
        Session session = clientHandlerData.getSession();
        SocketSessionCache.saveSession(session);
        SocketSessionCache.saveClientIdAndSid(clientMessage.getClientId(),session.getId());
        ServerMessage serverMsg = ServerMessage.builder()
                .messageId(session.getId())
                .type(ServerMessageTypeEnum.HEARTBEAT.getType())
                .sync(0)
                .build();
        SessionUtils.sendMessage(session, JSON.toJSONString(serverMsg));
        return null;
    }

}
