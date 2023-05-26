package plus.easydo.server.websocket;

import cn.hutool.core.exceptions.ExceptionUtil;
import plus.easydo.server.util.server.SessionUtils;
import plus.easydo.server.websocket.handler.ClientMessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;


/**
 * @author yuzhanfeng
 * @Date 2022/9/1 11:20
 */
@Slf4j
@Component
@ServerEndpoint("/wss/client")
public class ClientSocketEndpoint {


    public static ClientMessageHandler clientMessageHandler;

    /**
     * ServerEndpoint无法直接 Autowired static方式注入
     *
     * @param clientMessageHandler clientMessageHandler
     * @author laoyu
     * @date 2022/9/2
     */
    @Autowired
    public void setClientMessageHandler(ClientMessageHandler clientMessageHandler){
        ClientSocketEndpoint.clientMessageHandler = clientMessageHandler;
    }

    /**
     * 打开websocket连接
     *
     * @param session session
     * @author laoyu
     * @date 2022/9/1
     */
    @OnOpen
    public void onOpen(Session session) {
        SocketSessionCache.saveSession(session);
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
        SocketSessionCache.removeClientBySessionId(session.getId());
        log.info("【websocket消息】客户端通信断开");
    }

    @OnError
    public void onError(Throwable exception, Session session) {
        log.warn("【websocket消息】ClientSocketEndpoint异常，{}",ExceptionUtil.getMessage(exception));
        SessionUtils.sendErrorServerMessage(session,session.getId(), ExceptionUtil.getMessage(exception));
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
        clientMessageHandler.handle(message,session);
    }

}
