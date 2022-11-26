package game.server.manager.client.server;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.alibaba.fastjson2.JSON;
import game.server.manager.client.websocket.ClientWebsocketEndpoint;
import game.server.manager.client.websocket.handler.OnMessageHandler;
import game.server.manager.common.constant.PathConstants;
import game.server.manager.common.enums.ClientSocketTypeEnum;
import game.server.manager.common.mode.SyncData;
import game.server.manager.client.config.SystemUtils;
import game.server.manager.common.mode.socket.ClientMessage;
import game.server.manager.common.result.R;
import game.server.manager.common.utils.http.HttpModel;
import game.server.manager.common.utils.http.HttpRequestUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;


/**
 * @author laoyu
 * @version 1.0
 */
@Data
@Slf4j
@Component
public class SyncServer {

    @Autowired
    private SystemUtils systemUtils;

    @Autowired
    private ClientDataServer clientDataServer;

    @Autowired
    private OnMessageHandler onMessageHandler;

    private ClientWebsocketEndpoint client;

    public R<String> sync(SyncData syncData) {
        String resultStr = HttpRequestUtil.post(
                HttpModel.builder()
                        .host(systemUtils.getManagerUrl())
                        .path(PathConstants.SYNC)
                        .body(JSON.toJSONString(syncData)).build());
        return HttpRequestUtil.unPackage(resultStr);
    }

    public void createClient() {
        URI uri = null;
        try {
            uri = new URI(systemUtils.getServerSocketUrl());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        this.client = new ClientWebsocketEndpoint(uri,systemUtils.getClientId(),onMessageHandler);
        log.info("初始化服务端连接");
        try {
            client.connect();
            ClientMessage message = ClientMessage.builder()
                    .type(ClientSocketTypeEnum.HEARTBEAT.getType()).clientId(systemUtils.getClientId())
                    .build();
            client.send(JSON.toJSONString(message));
        }catch (Exception e) {
            log.warn("初始化服务端连接失败,{}", ExceptionUtil.getMessage(e));
        }
    }

    public void sendMessage(ClientSocketTypeEnum type,  String message){
        ClientMessage clientMessage = ClientMessage.builder().clientId(systemUtils.getClientId()).type(type.getType()).data(message).build();
        client.send(JSON.toJSONString(clientMessage));
    }

}
