package game.server.manager.client.thread;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import game.server.manager.client.contants.ClientSocketTypeEnum;
import game.server.manager.client.model.ClientData;
import game.server.manager.client.server.ClientDataServer;
import game.server.manager.client.server.SyncServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


/**
 * @author laoyu
 * @version 1.0
 * @description 心跳检测
 * @date 2022/11/21
 */
@Slf4j
@Component
public class HeartbeatThread {

    @Autowired
    private ClientDataServer clientDataServer;
    @Autowired
    private SyncServer syncServer;

    @Scheduled(fixedDelay = 1000 * 30)
    public void HeartbeatCheck() {
        ClientData clientData = clientDataServer.getClientData();
        ObjectMapper mapper = new ObjectMapper();
        try {
            syncServer.sendMessage(ClientSocketTypeEnum.HEARTBEAT, mapper.writeValueAsString(clientData));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
