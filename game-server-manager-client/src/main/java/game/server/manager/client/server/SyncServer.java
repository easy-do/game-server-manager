package game.server.manager.client.server;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import game.server.manager.client.config.SystemUtils;
import game.server.manager.client.contants.ClientSocketTypeEnum;
import game.server.manager.client.model.socket.ClientMessage;
import game.server.manager.client.websocket.ClientWebsocketEndpoint;
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

    public void sendOkMessage(ClientSocketTypeEnum type, String messageId, String message){
        ClientMessage clientMessage = ClientMessage.builder()
                .clientId(systemUtils.getClientId())
                .messageId(messageId)
                .type(type.getType())
                .data(message)
                .success(true)
                .build();

        ObjectMapper mapper = new ObjectMapper();
        try {
            sendMessage(mapper.writeValueAsString(clientMessage));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    public void sendFailMessage(ClientSocketTypeEnum type, String messageId, String message){
        ClientMessage clientMessage = ClientMessage.builder()
                .clientId(systemUtils.getClientId())
                .messageId(messageId)
                .type(type.getType())
                .data(message)
                .success(false)
                .build();
        ObjectMapper mapper = new ObjectMapper();
        try {
            sendMessage(mapper.writeValueAsString(clientMessage));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMessage(ClientSocketTypeEnum type, String message){
        ClientMessage clientMessage = ClientMessage.builder()
                .clientId(systemUtils.getClientId())
                .type(type.getType())
                .success(true)
                .data(message)
                .build();
        if(ClientWebsocketEndpoint.CLIENT.isOpen()){
            ObjectMapper mapper = new ObjectMapper();
            try {
                sendMessage(mapper.writeValueAsString(clientMessage));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }else {
                    log.error("连接断开,尝试重连。");
            ClientWebsocketEndpoint.CLIENT.reconnect();
            ObjectMapper mapper = new ObjectMapper();
            try {
                ClientWebsocketEndpoint.CLIENT.send(mapper.writeValueAsString(clientMessage));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void sendMessage(String message){
        if(ClientWebsocketEndpoint.CLIENT.isOpen()){
            ClientWebsocketEndpoint.CLIENT.send(message);
        }else {
            try {
                log.error("连接断开,尝试重连。");
                ClientWebsocketEndpoint.CLIENT.reconnectBlocking();
                ClientWebsocketEndpoint.CLIENT.send(message);
            } catch (InterruptedException ex) {
                log.error("重连失败,{}",ExceptionUtil.getMessage(ex));
            }
        }
    }

    public void unLock(String messageId) {
        ClientWebsocketEndpoint.unLock(messageId);
    }
}
