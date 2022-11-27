package game.server.manager.client.thread;

import com.alibaba.fastjson2.JSON;
import game.server.manager.client.config.SystemUtils;
import game.server.manager.client.server.SyncServer;
import game.server.manager.common.enums.ClientSocketTypeEnum;
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
    private SyncServer syncServer;

    @Autowired
    private SystemUtils systemUtils;

    @Scheduled(fixedDelay = 1000 * 20)
    public void HeartbeatCheck() {
        syncServer.sendMessage(ClientSocketTypeEnum.HEARTBEAT, JSON.toJSONString(""));
    }
}
