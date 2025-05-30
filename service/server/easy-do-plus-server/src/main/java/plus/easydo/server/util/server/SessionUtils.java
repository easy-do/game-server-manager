package plus.easydo.server.util.server;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSON;
import plus.easydo.common.enums.ServerMessageTypeEnum;
import plus.easydo.common.exception.ExceptionFactory;
import plus.easydo.common.mode.socket.ClientMessage;
import plus.easydo.common.mode.socket.ServerMessage;
import plus.easydo.server.websocket.SessionResultCache;
import plus.easydo.server.websocket.SocketSessionCache;

import javax.websocket.Session;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * @author yuzhanfeng
 * @Date 2022/11/26 23:01
 * @Description session工具
 */
public class SessionUtils {

    private SessionUtils() {
    }

    /**
     * 发送消息
     *
     * @param message message
     * @author laoyu
     * @date 2022/9/1
     */
    public static void sendMessage(Session session, String message) {
        try {
            if(Objects.nonNull(session)){
                session.getBasicRemote().sendText(message);
            }
        } catch (IOException exception) {
           throw  ExceptionFactory.bizException(ExceptionUtil.getMessage(exception));
        }
    }

    public static void close(Session session) {
        try {
            if(Objects.nonNull(session)){
                ServerMessage serverMessage = ServerMessage.builder()
                        .type(ServerMessageTypeEnum.SUCCESS.getType())
                        .data("服务器主动断开连接.")
                        .build();
                session.getAsyncRemote().sendText(JSON.toJSONString(serverMessage));
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

    /**
     * 超时获取客户端消息
     *
     * @param messageId messageId
     * @param timeoutMs 超时时间 毫秒
     * @return socket.mode.plus.easydo.common.ClientMessage
     * @author laoyu
     * @date 2022/11/27
     */
    public static  ClientMessage timeoutGetClientMessage(String messageId,long timeoutMs){
        long timeoutExpiredMs = System.currentTimeMillis() + timeoutMs;
        while (true) {
            ClientMessage message = SessionResultCache.getResultByMessageId(messageId);
            if(Objects.nonNull(message)){
                SessionResultCache.removeMessageById(messageId);
                return message;
            }
            long waitMillis = timeoutExpiredMs - System.currentTimeMillis();
            if (waitMillis <= 0) {
                //超过指定时间直接超时。
                return null;
            }
        }
    }

    /**
     * 向客户端发送消息并超时获取返回的数组信息
     *
     * @param clientSession clientSession
     * @param messageId messageId
     * @param serverMessage serverMessage
     * @return result.plus.easydo.common.R<java.util.List<T>>
     * @author laoyu
     * @date 2022/11/27
     */
    public static <T> List<T> sendMessageAndGetListResultMessage(Session clientSession, String messageId, ServerMessage serverMessage) {
        sendMessage(clientSession, JSON.toJSONString(serverMessage));
        ClientMessage clientMessage = timeoutGetClientMessage(messageId, 3000);
        if(Objects.isNull(clientMessage)){
            throw ExceptionFactory.bizException("获取客户端消息失败");
        }
        if(clientMessage.isSuccess()){
            return JSON.parseObject(clientMessage.getData(),List.class);
        }
        return null;
    }

    /**
     * 向客户端发送消息并超时获取返回的信息
     *
     * @param clientSession clientSession
     * @param messageId messageId
     * @param serverMessage serverMessage
     * @return result.plus.easydo.common.R<T>
     * @author laoyu
     * @date 2022/11/27
     */
    public static String sendMessageAndGetResultMessage(Session clientSession, String messageId, ServerMessage serverMessage) {
        sendMessage(clientSession, JSON.toJSONString(serverMessage));
        ClientMessage clientMessage = timeoutGetClientMessage(messageId, 3000);
        if(Objects.isNull(clientMessage)){
            throw ExceptionFactory.bizException("获取客户端消息超时.");
        }
        return clientMessage.getData();
    }

    /**
     * 发送成功类型的服务器消息
     *
     * @param session session
     * @param messageId messageId
     * @param message message
     * @return void
     * @author laoyu
     * @date 2022/11/27
     */
    public static void sendOkServerMessage(Session session, String messageId, String message){
        ServerMessage serverMessage = ServerMessage.builder()
                .messageId(messageId)
                .type(ServerMessageTypeEnum.SUCCESS.getType())
                .sync(0)
                .data(message).build();
        sendMessage(session,JSON.toJSONString(serverMessage));
    }

    /**
     * 发送异常类型的服务器消息
     *
     * @param session session
     * @param messageId messageId
     * @param message message
     * @return void
     * @author laoyu
     * @date 2022/11/27
     */
    public static void sendErrorServerMessage(Session session, String messageId, String message){
        ServerMessage serverMessage = ServerMessage.builder()
                .messageId(messageId)
                .type(ServerMessageTypeEnum.ERROR.getType())
                .sync(0)
                .data(message).build();
        sendMessage(session,JSON.toJSONString(serverMessage));
    }

    /**
     * 发送需要同步转发处理的服务器消息
     *
     * @param session session
     * @param messageId messageId
     * @param message message
     * @param type type
     * @return void
     * @author laoyu
     * @date 2022/11/27
     */
    public static void sendSyncServerMessage(Session session, String messageId, String message, ServerMessageTypeEnum type){
        ServerMessage serverMessage = ServerMessage.builder()
                .messageId(messageId)
                .type(type.getType())
                .sync(1)
                .data(message).build();
        sendMessage(session,JSON.toJSONString(serverMessage));
    }

    /**
     * 发送自定义服务器消息
     *
     * @param session session
     * @param messageId messageId
     * @param message message
     * @param type type
     * @author laoyu
     * @date 2022/11/27
     */
    public static void sendSimpleServerMessage(Session session, String messageId, String message, ServerMessageTypeEnum type){
        ServerMessage serverMessage = ServerMessage.builder()
                .messageId(messageId)
                .type(type.getType())
                .sync(0)
                .data(message).build();
            sendMessage(session, JSONUtil.toJsonStr(serverMessage));
    }

}
