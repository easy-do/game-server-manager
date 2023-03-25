package game.server.manager.client.websocket.handler;

import cn.hutool.core.exceptions.ExceptionUtil;
import game.server.manager.client.server.SyncServer;
import game.server.manager.client.service.DockerService;
import game.server.manager.common.constant.MessageTypeConstants;
import game.server.manager.common.enums.ClientSocketTypeEnum;
import game.server.manager.common.mode.socket.ServerMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author yuzhanfeng
 * @Date 2022/11/26 23:29
 * @Description 服务端心跳处理服务
 */
@Slf4j
@Service(MessageTypeConstants.PING_DOCKER)
public class PingDockerHandlerService implements AbstractHandlerService {

    @Autowired
    private SyncServer syncServer;

    @Autowired
    private DockerService dockerService;

    @Override
    public Void handler(ServerMessage serverMessage) {
        log.info("OnMessageHandler ping ==> {}",serverMessage);
        String messageId = serverMessage.getMessageId();
        try {
            dockerService.ping();
            syncServer.sendOkMessage(ClientSocketTypeEnum.NO_SYNC_RESULT,messageId,"success");
        }catch (Exception e) {
            syncServer.sendFailMessage(ClientSocketTypeEnum.NO_SYNC_RESULT,messageId, ExceptionUtil.getMessage(e));
        }
        return null;
    }
}
