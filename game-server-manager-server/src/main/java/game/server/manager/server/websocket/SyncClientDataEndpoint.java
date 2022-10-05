package game.server.manager.server.websocket;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.alibaba.fastjson2.JSON;
import game.server.manager.common.exception.BizException;
import game.server.manager.common.mode.SyncData;
import game.server.manager.common.result.DataResult;
import game.server.manager.server.server.DefaultServerContainer;
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
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yuzhanfeng
 * @Date 2022/9/1 11:20
 */
@Slf4j
@Component
@ServerEndpoint("/wss/syncClientData")
public class SyncClientDataEndpoint {

    private static DefaultServerContainer defaultServerContainer;

    /**
     * session集合
     */
    public static Map<String,Session> sessionMap = new ConcurrentHashMap<>();

    /**
     * ServerEndpoint无法直接 Autowired static方式
     *
     * @param defaultServerContainer defaultServerContainer
     * @author laoyu
     * @date 2022/9/2
     */
    @Autowired
    private void setDefaultServerContainer(DefaultServerContainer defaultServerContainer) {
        SyncClientDataEndpoint.defaultServerContainer = defaultServerContainer;
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
        sessionMap.put(session.getId(),session);
        log.info("【websocket消息】建立客户端同步信息连接.");
        sendMessage(session,JSON.toJSONString(DataResult.ok(session.getId(),"success")));
    }

    /**
     * 关闭 websocket连接
     *
     * @author laoyu
     * @date 2022/9/1
     */
    @OnClose
    public void onClose() {
        log.info("【websocket消息】客户端通信请求意外断开");
    }

    @OnError
    public void onError(Throwable exception) {
        exception.printStackTrace();
//        sendMessage(JSON.toJSONString(DataResult.fail()));
    }

    /**
     * 接收到消息
     *
     * @param message message
     * @author laoyu
     * @date 2022/9/1
     */
    @OnMessage
    public void onMessage(String message) {
        log.info("【websocket消息】客户端通信请求:{}", message);
        SyncData syncData = JSON.parseObject(message, SyncData.class);
        String sessionId = syncData.getSessionId();
        Object result = defaultServerContainer.processData(syncData);
        sendMessage(sessionMap.get(sessionId),JSON.toJSONString(result));
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
