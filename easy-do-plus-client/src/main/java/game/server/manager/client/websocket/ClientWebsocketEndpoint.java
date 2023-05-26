package plus.easydo.push.client.websocket;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.text.CharSequenceUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import plus.easydo.push.client.config.SystemUtils;
import plus.easydo.push.client.contants.ClientSocketTypeEnum;
import plus.easydo.push.client.model.socket.ClientMessage;
import plus.easydo.push.client.model.socket.ServerMessage;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author laoyu
 * @version 1.0
 * @description 自实现websocket客户端
 * @date 2022/11/21
 */
@Slf4j
@Component
public class ClientWebsocketEndpoint {

    private static volatile boolean messageLock;

    private static volatile String lockMessageId;

    public static WebSocketClient CLIENT;


    private WebSocketClientHandlerService handlerService;

    public ClientWebsocketEndpoint(SystemUtils systemUtils,WebSocketClientHandlerService handlerService) throws URISyntaxException {
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
                    handlerService.handler(serverMessage);
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


}
