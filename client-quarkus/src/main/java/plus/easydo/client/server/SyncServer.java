package plus.easydo.client.server;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import plus.easydo.client.config.JacksonObjectMapper;
import plus.easydo.client.config.SystemUtils;
import plus.easydo.client.contants.ClientSocketTypeEnum;
import plus.easydo.client.model.socket.ClientMessage;
import plus.easydo.client.websocket.ClientWebsocketEndpoint;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;


/**
 * @author laoyu
 * @version 1.0
 */
@Data
@Slf4j
@ApplicationScoped
public class SyncServer {

    @Inject
    SystemUtils systemUtils;

    @Inject
    ClientDataServer clientDataServer;

    @Inject
    JacksonObjectMapper mapper;

    public void sendOkMessage(ClientSocketTypeEnum type, String messageId, String message){
        ClientMessage clientMessage = ClientMessage.builder()
                .clientId(systemUtils.getClientId())
                .messageId(messageId)
                .type(type.getType())
                .data(message)
                .success(true)
                .build();

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
            try {
                sendMessage(mapper.writeValueAsString(clientMessage));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }else {
                    log.error("连接断开,尝试重连。");
            ClientWebsocketEndpoint.CLIENT.reconnect();
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
