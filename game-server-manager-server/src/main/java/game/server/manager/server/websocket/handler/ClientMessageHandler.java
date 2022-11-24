package game.server.manager.server.websocket.handler;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.alibaba.fastjson2.JSON;
import game.server.manager.common.enums.ClientSocketTypeEnum;
import game.server.manager.common.enums.ServerMessageTypeEnum;
import game.server.manager.common.exception.BizException;
import game.server.manager.common.mode.socket.ClientMessage;
import game.server.manager.common.mode.socket.ServerMessage;
import game.server.manager.server.websocket.SocketSessionCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Objects;

/**
 * @author yuzhanfeng
 * @Date 2022/11/24 16:29
 * @Description 客户端消息处理类
 */
@Slf4j
@Component
public class ClientMessageHandler {


    public void handle(String message, Session session) {
        ClientMessage clientMessage = null;
        try{
            clientMessage = JSON.parseObject(message, ClientMessage.class);
        }catch (Exception exception){
            throw new RuntimeException(exception);
        }
        String type = clientMessage.getType();
        //心跳数据
        if(ClientSocketTypeEnum.HEARTBEAT.getType().equals(type)){
            SocketSessionCache.saveSession(session);
            SocketSessionCache.saveClientIdSession(clientMessage.getClientId(),session.getId());
            ServerMessage serverMsg = ServerMessage.builder()
                    .messageId(session.getId())
                    .type(ServerMessageTypeEnum.HEARTBEAT.getType())
                    .sync(0)
                    .build();
            sendMessage(session, JSON.toJSONString(serverMsg));
            return;
        }
        if(ClientSocketTypeEnum.RESULT.getType().equals(type)){
            //寻找游览器session
            Session browserSession = SocketSessionCache.getBrowserSessionByClientSessionId(session.getId());
            //向游览器转发消息
            if(Objects.nonNull(browserSession)){
                try {
                    browserSession.getBasicRemote().sendText(clientMessage.getData());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }else {
                log.info("【websocket消息】游览器会话不存在或已结束,放弃消息转发。{}", session.getId());
            }
        }
        if(ClientSocketTypeEnum.RESULT_END.getType().equals(type)){
            //寻找游览器session
            Session browserSession = SocketSessionCache.getBrowserSessionByClientSessionId(session.getId());
            //向游览器转发消息
            try {
                browserSession.getBasicRemote().sendText(clientMessage.getData());
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
