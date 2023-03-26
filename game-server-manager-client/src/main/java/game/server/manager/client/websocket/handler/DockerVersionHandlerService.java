package game.server.manager.client.websocket.handler;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.alibaba.fastjson2.JSON;
import com.github.dockerjava.api.model.Version;
import game.server.manager.client.contants.ClientSocketTypeEnum;
import game.server.manager.client.contants.MessageTypeConstants;
import game.server.manager.client.model.socket.ServerMessage;
import game.server.manager.client.server.SyncServer;
import game.server.manager.client.service.DockerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author yuzhanfeng
 * @Date 2022/11/26 23:29
 * @Description 服务端心跳处理服务
 */
@Slf4j
@Service(MessageTypeConstants.DOCKER_VERSION)
public class DockerVersionHandlerService implements AbstractHandlerService {

    @Autowired
    private SyncServer syncServer;

    @Autowired
    private DockerService dockerService;

    @Override
    public Void handler(ServerMessage serverMessage) {
        log.info("OnMessageHandler version ==> {}",serverMessage);
        String messageId = serverMessage.getMessageId();
        try {
            Version version = dockerService.version();
            syncServer.sendOkMessage(ClientSocketTypeEnum.NO_SYNC_RESULT,messageId, JSON.toJSONString(version));
        }catch (Exception e) {
            syncServer.sendFailMessage(ClientSocketTypeEnum.NO_SYNC_RESULT,messageId, ExceptionUtil.getMessage(e));
        }
        return null;
    }
}
