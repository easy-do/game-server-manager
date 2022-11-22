package game.server.manager.client.thread;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.alibaba.fastjson2.JSON;
import game.server.manager.client.config.SystemUtils;
import game.server.manager.client.server.SyncServer;
import game.server.manager.client.websocket.ClientWebsocketEndpoint;
import game.server.manager.common.enums.ClientSocketTypeEnum;
import game.server.manager.common.mode.socket.BrowserMessage;
import game.server.manager.common.mode.socket.ClientMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Objects;

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
        ClientWebsocketEndpoint client = syncServer.getClient();
        if(Objects.nonNull(client)){
            ClientMessage message = ClientMessage.builder()
                    .type(ClientSocketTypeEnum.HEARTBEAT.getType()).clientId(systemUtils.getClientId())
                    .build();
                if(client.isOpen()){
                    client.send(JSON.toJSONString(message));
                }else {
                    log.warn("客户端连接已断开，尝试重新连接");
                    try {
                        client.reconnect();
                    }catch (Exception e) {
                        log.warn("尝试重新连接失败,{}", ExceptionUtil.getMessage(e));
                    }

                }
        }
    }
}
