package plus.easydo.client.server;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import plus.easydo.client.config.JacksonObjectMapper;
import plus.easydo.client.config.SystemUtils;
import plus.easydo.client.contants.ClientSocketTypeEnum;
import plus.easydo.client.model.socket.ClientMessage;
import plus.easydo.client.websocket.ClientWebsocketEndpoint;
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
    private JacksonObjectMapper mapper;

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
}
