package game.server.manager.client.websocket;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.text.CharSequenceUtil;
import com.alibaba.fastjson2.JSON;
import game.server.manager.client.config.SystemUtils;
import game.server.manager.client.contants.ClientSocketTypeEnum;
import game.server.manager.client.model.socket.ClientMessage;
import game.server.manager.client.model.socket.ServerMessage;
import game.server.manager.client.websocket.handler.AbstractHandlerService;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Objects;

/**
 * @author laoyu
 * @version 1.0
 * @description 自实现websocket客户端
 * @date 2022/11/21
 */
@Slf4j
@Component
public class ClientWebsocketEndpoint extends WebSocketClient {

    private static volatile boolean messageLock;

    private static volatile String lockMessageId;

    private final String clientId;

    @Autowired
    private Map<String, AbstractHandlerService> handlerContainer;

    public void setHandlerContainer(Map<String, AbstractHandlerService> handlerContainer) {
        this.handlerContainer = handlerContainer;
    }

//    public ClientWebsocketEndpoint(URI serverUri, String clientId) {
//        super(serverUri);
//        log.info("init client connect");
//        this.clientId = clientId;
//        connect();
//    }

    public ClientWebsocketEndpoint(SystemUtils systemUtils) throws URISyntaxException {
        super(new URI(systemUtils.getServerSocketUrl()));
        log.info("init client connect");
        this.clientId = systemUtils.getClientId();
        connect();
    }

    /**
     * 是否被某个消息锁定
     *
     * @param serverMessage serverMessage
     * @return boolean
     * @author laoyu
     * @date 2022/11/22
     */
    private synchronized boolean isLock(ServerMessage serverMessage){
        //不是需要同步传输的消息
        if(!serverMessage.isSync()){
            return false;
        }
        //已经锁定了，但是被当前消息锁定的，可以继续响应
        if(messageLock && CharSequenceUtil.equals(serverMessage.getMessageId(),lockMessageId)){
            return false;
        }
        //没有锁定，但是为同步传输消息则加锁。
        messageLock = true;
        lockMessageId = serverMessage.getMessageId();
        return false;
    }

    /**
     * 锁定消息
     *
     * @param messageId messageId
     * @return boolean
     * @author laoyu
     * @date 2022/11/22
     */
    public synchronized boolean lock(String messageId){
        if(!messageLock){
            messageLock = true;
            lockMessageId = messageId;
            return true;
        }
        return false;
    }

    /**
     * 解锁消息
     *
     * @param messageId messageId
     * @return boolean
     * @author laoyu
     * @date 2022/11/22
     */
    public synchronized boolean unLock(String messageId){
        if(messageLock && CharSequenceUtil.equals(lockMessageId,messageId)){
            messageLock = false;
            lockMessageId = "";
            return true;
        }
        return false;
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        log.info("open server connect");
        ClientMessage connectMessage = ClientMessage.builder()
                .type(ClientSocketTypeEnum.HEARTBEAT.getType()).clientId(clientId)
                .build();
        send(JSON.toJSONString(connectMessage));
    }

    @Override
    public void onMessage(String message) {
        log.info("server message,{}",message);
        ServerMessage serverMessage = JSON.parseObject(message, ServerMessage.class);
        if(!isLock(serverMessage)){
            AbstractHandlerService handlerService = handlerContainer.get(serverMessage.getType());
            if(Objects.isNull(handlerService)){
                log.warn("{} handler not found",serverMessage.getType());
            }else {
                handlerService.handler(serverMessage);
            }
        }else {
            log.warn("message lock.");
            this.send(JSON.toJSONString(ClientMessage.builder().clientId(clientId).type(ClientSocketTypeEnum.LOCK.getType()).data("当前同步通信消息被占用,请等待上一个操作释放资源。").build()));
        }
    }

    @Override
    public void onClose(int i, String s, boolean b) {
        log.info("server disconnect 。");
    }

    @Override
    public void onError(Exception e) {
        log.warn("socket exception, {}", ExceptionUtil.getMessage(e));
    }

}
