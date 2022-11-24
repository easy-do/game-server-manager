package game.server.manager.client.websocket;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.text.CharSequenceUtil;
import com.alibaba.fastjson2.JSON;
import game.server.manager.client.websocket.handler.OnMessageHandler;
import game.server.manager.common.enums.ClientSocketTypeEnum;
import game.server.manager.common.mode.socket.ClientMessage;
import game.server.manager.common.mode.socket.ServerMessage;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

/**
 * @author laoyu
 * @version 1.0
 * @description 自实现websocket客户端
 * @date 2022/11/21
 */
@Slf4j
public class ClientWebsocketEndpoint extends WebSocketClient {

    private static volatile boolean messageLock;

    private static volatile String lockMessageId;

    private OnMessageHandler onMessageHandler;

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
        return true;
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



    private String clientId;

    public ClientWebsocketEndpoint(URI serverUri,String clientId,OnMessageHandler onMessageHandler) {
        super(serverUri);
        this.clientId = clientId;
        this.onMessageHandler = onMessageHandler;
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        log.info("开启与服务端的连接.");
        ClientMessage connectMessage = ClientMessage.builder()
                .type(ClientSocketTypeEnum.HEARTBEAT.getType()).clientId(clientId)
                .build();
        send(JSON.toJSONString(connectMessage));
    }

    @Override
    public void onMessage(String message) {
        log.info("接收到服务端消息,{}",message);
        ServerMessage serverMessage = JSON.parseObject(message, ServerMessage.class);
        if(isLock(serverMessage)){
            onMessageHandler.handler(serverMessage);
        }else {
            log.warn("消息被锁定.");
            this.send(JSON.toJSONString(ClientMessage.builder().clientId(clientId).type(ClientSocketTypeEnum.LOCK.getType()).dataJson("当前同步通信消息被占用,请等待上一个操作释放资源。").build()));
        }
    }

    @Override
    public void onClose(int i, String s, boolean b) {
        log.info("与服务端连接断开");
    }

    @Override
    public void onError(Exception e) {
        log.warn("socket异常, {}", ExceptionUtil.getMessage(e));
    }

}
