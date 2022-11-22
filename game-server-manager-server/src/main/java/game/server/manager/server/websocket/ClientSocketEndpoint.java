package game.server.manager.server.websocket;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.alibaba.fastjson2.JSON;
import game.server.manager.common.enums.ClientSocketTypeEnum;
import game.server.manager.common.enums.ServerMessageTypeEnum;
import game.server.manager.common.exception.BizException;
import game.server.manager.common.mode.socket.ClientMessage;
import game.server.manager.common.mode.socket.ServerMessage;
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
        log.info("【websocket消息】客户端建立连接.");
    }

    /**
     * 关闭 websocket连接
     *
     * @author laoyu
     * @date 2022/9/1
     */
    @OnClose
    public void onClose(Session session) {
        SocketSessionCache.removeClient(session.getId());
        log.info("【websocket消息】客户端通信请求意外断开");
    }

    @OnError
    public void onError(Throwable exception, Session session) {
        log.warn("【websocket消息】客户端socket通信异常，{}",ExceptionUtil.getMessage(exception));
        ServerMessage serverMsg = ServerMessage.builder().messageId(session.getId()).type(ServerMessageTypeEnum.ERROR.getType()).sync(0).jsonData(ExceptionUtil.getMessage(exception)).build();
        sendMessage(session, JSON.toJSONString(serverMsg));
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
        log.info("【websocket消息】客户端消息:{}", message);
        ClientMessage clientMessage = JSON.parseObject(message, ClientMessage.class);
        String type = clientMessage.getType();
        //心跳数据
        if(ClientSocketTypeEnum.HEARTBEAT.getType().equals(type)){
            SocketSessionCache.saveClientSession(session);
            SocketSessionCache.saveClientIdSession(clientMessage.getClientId(),session.getId());
            ServerMessage serverMsg = ServerMessage.builder().messageId(session.getId()).type(ServerMessageTypeEnum.HEARTBEAT.getType()).sync(0).build();
            sendMessage(session, JSON.toJSONString(serverMsg));
            return;
        }
        if(ClientSocketTypeEnum.RESULT.getType().equals(type)){
            //寻找游览器session
            Session browserSession = SocketSessionCache.getBrowserByClientSessionId(session.getId());
            //向游览器转发消息
            try {
                browserSession.getBasicRemote().sendText(clientMessage.getDataJson());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if(ClientSocketTypeEnum.RESULT_END.getType().equals(type)){
            //寻找游览器session
            Session browserSession = SocketSessionCache.getBrowserByClientSessionId(session.getId());
            //向游览器转发消息
            try {
                browserSession.getBasicRemote().sendText(clientMessage.getDataJson());
                browserSession.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
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
