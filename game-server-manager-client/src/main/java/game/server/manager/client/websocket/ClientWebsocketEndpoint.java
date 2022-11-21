package game.server.manager.client.websocket;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.alibaba.fastjson2.JSON;
import game.server.manager.common.enums.ClientSocketTypeEnum;
import game.server.manager.common.mode.socket.ClientSocketMessage;
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

    private String clientId;

    public ClientWebsocketEndpoint(URI serverUri,String clientId) {
        super(serverUri);
        this.clientId = clientId;
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        log.info("开启客户端连接,{}",new String(serverHandshake.getContent()));
        ClientSocketMessage message = ClientSocketMessage.builder()
                .type(ClientSocketTypeEnum.HEARTBEAT.getType()).clientId(clientId)
                .build();
        this.send(JSON.toJSONString(message));
    }

    @Override
    public void onMessage(String s) {
        log.info("接收到服务端消息,{}",s);
    }

    @Override
    public void onClose(int i, String s, boolean b) {
        log.info("与服务端连接断开");
    }

    @Override
    public void onError(Exception e) {
        log.warn("与服务端连接出现异常, {}", ExceptionUtil.getMessage(e));
    }

}
