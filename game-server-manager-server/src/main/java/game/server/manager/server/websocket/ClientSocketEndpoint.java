package game.server.manager.server.websocket;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.alibaba.fastjson2.JSON;
import game.server.manager.common.enums.ClientSocketTypeEnum;
import game.server.manager.common.exception.BizException;
import game.server.manager.common.mode.socket.ClientSocketMessage;
import game.server.manager.common.result.DataResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Objects;

import static game.server.manager.server.websocket.SocketSessionCache.CLIENT_SESSION_CACHE;
import static game.server.manager.server.websocket.SocketSessionCache.SESSION_CLIENT_CACHE;
import static game.server.manager.server.websocket.SocketSessionCache.SESSION_CACHE;

/**
 * @author yuzhanfeng
 * @Date 2022/9/1 11:20
 */
@Slf4j
@Component
@ServerEndpoint("/wss/client")
public class ClientSocketEndpoint {


    /**
     * 打开websocket连接
     *
     * @param session session
     * @author laoyu
     * @date 2022/9/1
     */
    @OnOpen
    public void onOpen(Session session) {
        SESSION_CACHE.put(session.getId(),session);
        log.info("【websocket消息】客户端建立连接.");
        sendMessage(session,JSON.toJSONString(DataResult.ok(session.getId(),"success")));
    }

    /**
     * 关闭 websocket连接
     *
     * @author laoyu
     * @date 2022/9/1
     */
    @OnClose
    public void onClose(Session session) {
        SESSION_CACHE.remove(session.getId());
        String clientId = SESSION_CLIENT_CACHE.get(session.getId());
        if(Objects.nonNull(clientId)){
            CLIENT_SESSION_CACHE.remove(clientId);
            SESSION_CLIENT_CACHE.remove(session.getId());
        }
        log.info("【websocket消息】客户端通信请求意外断开");
    }

    @OnError
    public void onError(Throwable exception, Session session) {
        log.warn("【websocket消息】客户端socket通信异常，{}",ExceptionUtil.getMessage(exception));
        sendMessage(session,"服务器异常,将断开连接:," + ExceptionUtil.getMessage(exception));
        try {
            session.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 接收到消息
     *
     * @param message message
     * @author laoyu
     * @date 2022/9/1
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("【websocket消息】客户端通信请求:{}", message);
        ClientSocketMessage socketMessage = JSON.parseObject(message, ClientSocketMessage.class);
        String type = socketMessage.getType();
        if(ClientSocketTypeEnum.HEARTBEAT.getType().equals(type)){
            sendMessage(session,"ok");
            return;
        }
        if(ClientSocketTypeEnum.CONNECT.getType().equals(type)){
            CLIENT_SESSION_CACHE.put(socketMessage.getClientId(),session);
            SESSION_CLIENT_CACHE.put(session.getId(),socketMessage.getClientId());
            return;
        }
    }


    /**
     * 发送消息
     *
     * @param message message
     * @author laoyu
     * @date 2022/9/1
     */
    public void sendMessage(Session session,String message) {
        try {
            if(Objects.nonNull(session) && session.isOpen()){
                session.getBasicRemote().sendText(message);
            }else {
                log.warn("Session is null");
            }
        } catch (IOException e) {
            throw new BizException(ExceptionUtil.getMessage(e));
        }
    }


}
