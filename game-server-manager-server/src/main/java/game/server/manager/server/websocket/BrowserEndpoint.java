package game.server.manager.server.websocket;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.alibaba.fastjson2.JSON;
import game.server.manager.common.exception.BizException;
import game.server.manager.common.result.DataResult;
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
import java.io.IOException;
import java.util.Objects;

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
        sendMessage(session,JSON.toJSONString(DataResult.ok(session.getId(),"connect success")));
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
        sendMessage(session,"服务器异常:," + ExceptionUtil.getMessage(exception));
        try {
            SocketSessionCache.removeBrowserBySessionId(session.getId());
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
        log.info("【websocket消息】客户端docker相关连接请求:{}", message);

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
