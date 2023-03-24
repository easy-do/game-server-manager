package game.server.manager.client.server;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.alibaba.fastjson2.JSON;
import game.server.manager.client.websocket.ClientWebsocketEndpoint;
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
    private ClientWebsocketEndpoint client;



    public R<String> sync(SyncData syncData) {
        String resultStr = HttpRequestUtil.post(
                HttpModel.builder()
                        .host(systemUtils.getManagerUrl())
                        .path(PathConstants.SYNC)
                        .body(JSON.toJSONString(syncData)).build());
        return HttpRequestUtil.unPackage(resultStr);
    }

    public void sendOkMessage(ClientSocketTypeEnum type, String messageId, String message){
        ClientMessage clientMessage = ClientMessage.builder()
                .clientId(systemUtils.getClientId())
                .messageId(messageId)
                .type(type.getType())
                .data(message)
                .success(true)
                .build();
        sendMessage(JSON.toJSONString(clientMessage));
    }
    public void sendFailMessage(ClientSocketTypeEnum type, String messageId, String message){
        ClientMessage clientMessage = ClientMessage.builder()
                .clientId(systemUtils.getClientId())
                .messageId(messageId)
                .type(type.getType())
                .data(message)
                .success(false)
                .build();
        sendMessage(JSON.toJSONString(clientMessage));
    }

    public void sendMessage(ClientSocketTypeEnum type, String message){
        ClientMessage clientMessage = ClientMessage.builder()
                .clientId(systemUtils.getClientId())
                .type(type.getType())
                .success(true)
                .data(message)
                .build();
        if(client.isOpen()){
            sendMessage(JSON.toJSONString(clientMessage));
            }else {
                    log.error("连接断开,尝试重连。");
                    client.reconnect();
                    client.send(JSON.toJSONString(clientMessage));
            }
    }

    public void sendMessage(String message){
        if(client.isOpen()){
            client.send(message);
        }else {
            try {
                log.error("连接断开,尝试重连。");
                client.reconnectBlocking();
                client.send(message);
            } catch (InterruptedException ex) {
                log.error("重连失败,{}",ExceptionUtil.getMessage(ex));
            }
        }
    }

}
