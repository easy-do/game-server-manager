package plus.easydo.client.websocket;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.text.CharSequenceUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import plus.easydo.client.config.SystemUtils;
import plus.easydo.client.contants.ClientSocketTypeEnum;
import plus.easydo.client.model.socket.ClientMessage;
import plus.easydo.client.model.socket.ServerMessage;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author laoyu
 * @version 1.0
 * @description 自实现websocket客户端
 * @date 2022/11/21
 */
@Slf4j
@ApplicationScoped
public class ClientWebsocketEndpoint {

    private static volatile boolean messageLock;

    private static volatile String lockMessageId;

    public static WebSocketClient CLIENT;


    private WebSocketClientHandlerService handlerService;


    @Inject
    public ClientWebsocketEndpoint(SystemUtils systemUtils, WebSocketClientHandlerService handlerService) throws URISyntaxException {
        log.info("init client connect");
        this.handlerService = handlerService;
        URI serverSocketUrI = new URI(systemUtils.getServerSocketUrl());
        this.CLIENT = new WebSocketClient(serverSocketUrI) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                log.info("open server connect");
                ClientMessage connectMessage = ClientMessage.builder()
                        .type(ClientSocketTypeEnum.HEARTBEAT.getType()).clientId(systemUtils.getClientId())
                        .build();
                ObjectMapper mapper = new ObjectMapper();
                try {
                    send(mapper.writeValueAsString(connectMessage));
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onMessage(String message) {
                log.info("server message,{}",message);
                ObjectMapper mapper = new ObjectMapper();
                ServerMessage serverMessage = null;
                try {
                    serverMessage = mapper.readValue(message, ServerMessage.class);
                    if(!isLock(serverMessage)){
                        handlerService.handler(serverMessage);
                    }else {
                        log.warn("message lock.");
                        this.send(mapper.writeValueAsString(ClientMessage.builder().clientId(systemUtils.getClientId()).type(ClientSocketTypeEnum.LOCK.getType()).data("当前同步通信消息被占用,请等待上一个操作释放资源。").build()));
                    }
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
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
        };
        CLIENT.connect();
    }

    /**
     * 是否被某个消息锁定
     *
     * @param serverMessage serverMessage
     * @return boolean
     * @author laoyu
     * @date 2022/11/22
     */
    private static synchronized boolean isLock(ServerMessage serverMessage){
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
    public static synchronized boolean lock(String messageId){
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
    public static synchronized boolean unLock(String messageId){
        if(messageLock && CharSequenceUtil.equals(lockMessageId,messageId)){
            messageLock = false;
            lockMessageId = "";
            return true;
        }
        return false;
    }

}
