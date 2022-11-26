package game.server.manager.server.websocket;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.alibaba.fastjson2.JSON;
import game.server.manager.common.enums.ServerMessageTypeEnum;
import game.server.manager.common.exception.ExceptionFactory;
import game.server.manager.common.mode.socket.ServerMessage;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Objects;

/**
 * @author yuzhanfeng
 * @Date 2022/11/26 23:01
 * @Description session工具
 */
public class SessionUtils {

    /**
     * 发送消息
     *
     * @param message message
     * @author laoyu
     * @date 2022/9/1
     */
    public static void sendMessage(Session session, String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException exception) {
           throw  ExceptionFactory.bizException(ExceptionUtil.getMessage(exception));
        }
    }

    public static void close(Session session) {
        try {
            if(Objects.nonNull(session)){
                session.close();
            }
        } catch (IOException exception) {
            throw  ExceptionFactory.bizException(ExceptionUtil.getMessage(exception));
        }
    }

    public static void sendSimpleNoSyncMessage(String messageId, ServerMessageTypeEnum type, Session clientSession ){
        ServerMessage serverMessage = ServerMessage.builder()
                .messageId(messageId)
                .type(type.getType())
                .sync(0)
                .build();
        sendMessage(clientSession, JSON.toJSONString(serverMessage));
    }

    public static Session getClientSession(String clientId){
        Session clientSession = SocketSessionCache.getClientByClientId(clientId);
        if(Objects.isNull(clientSession)){
            throw ExceptionFactory.bizException("未找到客户端连接。");
        }
        return clientSession;
    }
}
