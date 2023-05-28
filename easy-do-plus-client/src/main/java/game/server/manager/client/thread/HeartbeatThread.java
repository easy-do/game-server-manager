package plus.easydo.client.thread;

import com.fasterxml.jackson.core.JsonProcessingException;
import plus.easydo.client.config.JacksonObjectMapper;
import plus.easydo.client.contants.ClientSocketTypeEnum;
import plus.easydo.client.model.ClientData;
import plus.easydo.client.server.ClientDataServer;
import plus.easydo.client.server.SyncServer;
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

    @Autowired
    private JacksonObjectMapper mapper;

    @Scheduled(fixedDelay = 1000 * 30)
    public void HeartbeatCheck() {
        ClientData clientData = clientDataServer.getClientData();
        try {
            syncServer.sendOkMessage(ClientSocketTypeEnum.HEARTBEAT, clientData.getClientId(), mapper.writeValueAsString(clientData));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
