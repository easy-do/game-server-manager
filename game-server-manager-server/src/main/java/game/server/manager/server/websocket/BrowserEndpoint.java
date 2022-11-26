package game.server.manager.server.websocket;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.alibaba.fastjson2.JSON;
import game.server.manager.common.enums.ServerMessageTypeEnum;
import game.server.manager.common.mode.socket.ServerMessage;
import game.server.manager.server.websocket.handler.BrowserMessageHandler;
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
@ServerEndpoint("/wss/browser")
public class BrowserEndpoint {


    public static BrowserMessageHandler browserMessageHandler;

    /**
     * ServerEndpoint无法直接 Autowired static方式
     *
     * @param browserMessageHandler browserMessageHandler
     * @author laoyu
     * @date 2022/9/2
     */
    @Autowired
    private void setBrowserMessageHandler(BrowserMessageHandler browserMessageHandler) {
        BrowserEndpoint.browserMessageHandler = browserMessageHandler;
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
        log.info("【websocket消息】客户端docker相关连接.");
        SocketSessionCache.saveSession(session);
        ServerMessage serverMessage = ServerMessage.builder()
                .messageId(session.getId())
                .type(ServerMessageTypeEnum.SUCCESS.getType())
                .data("connect success").build();
        SessionUtils.sendMessage(session,JSON.toJSONString(serverMessage));
    }

    /**
     * 关闭 websocket连接
     *
     * @author laoyu
     * @date 2022/9/1
     */
    @OnClose
    public void onClose(Session session) {
        SocketSessionCache.removeBrowserBySessionId(session.getId());
        log.info("【websocket消息】客户端docker相关连接断开");
    }

    @OnError
    public void onError(Session session,Throwable exception) {
        log.warn("【websocket消息】客户端docker socket通信异常，{}",ExceptionUtil.getMessage(exception));
        ServerMessage serverMessage = ServerMessage.builder()
                .messageId(session.getId())
                .type(ServerMessageTypeEnum.ERROR.getType())
                .data("服务器异常:," + ExceptionUtil.getMessage(exception))
                .build();
        SessionUtils.sendMessage(session,JSON.toJSONString(serverMessage));
            SocketSessionCache.removeBrowserBySessionId(session.getId());
            SessionUtils.close(session);
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
        log.info("【websocket消息】客户端docker相关连接请求:{}", message);
        browserMessageHandler.handle(message, session);
    }

}
