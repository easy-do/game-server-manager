package plus.easydo.client.thread;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.quarkus.scheduler.Scheduled;
import lombok.extern.slf4j.Slf4j;
import plus.easydo.client.config.JacksonObjectMapper;
import plus.easydo.client.contants.ClientSocketTypeEnum;
import plus.easydo.client.model.ClientData;
import plus.easydo.client.server.ClientDataServer;
import plus.easydo.client.server.SyncServer;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;


/**
 * @author laoyu
 * @version 1.0
 * @description 心跳检测
 * @date 2022/11/21
 */
@Slf4j
@ApplicationScoped
public class HeartbeatThread {

    @Inject
    ClientDataServer clientDataServer;
    @Inject
    SyncServer syncServer;

    @Inject
    JacksonObjectMapper mapper;

    @Scheduled(every = "30s")
    public void HeartbeatCheck() {
        ClientData clientData = clientDataServer.getClientData();
        try {
            syncServer.sendMessage(ClientSocketTypeEnum.HEARTBEAT, mapper.writeValueAsString(clientData));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
