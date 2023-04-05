package game.server.manager.client.websocket.handler;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.github.dockerjava.api.model.Container;
import game.server.manager.client.config.JacksonObjectMapper;
import game.server.manager.client.contants.ClientSocketTypeEnum;
import game.server.manager.client.contants.MessageTypeConstants;
import game.server.manager.client.model.socket.ServerMessage;
import game.server.manager.client.server.SyncServer;
import game.server.manager.client.service.DockerContainerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author yuzhanfeng
 * @Date 2022/11/26 23:29
 * @Description 服务端心跳处理服务
 */
@Slf4j
@Service(MessageTypeConstants.CONTAINER_LIST)
public class ListContainerHandlerService implements AbstractHandlerService {

    @Autowired
    private SyncServer syncServer;

    @Autowired
    private DockerContainerService dockerContainerService;

    @Autowired
    private JacksonObjectMapper mapper;

    @Override
    public Void handler(ServerMessage serverMessage) {
        log.info("OnMessageHandler containerList ==> {}",serverMessage);
        String messageId = serverMessage.getMessageId();
        try {
            List<Container> list = dockerContainerService.containerList();
            syncServer.sendOkMessage(ClientSocketTypeEnum.NO_SYNC_RESULT,messageId, mapper.writeValueAsString(list));
        }catch (Exception e) {
            syncServer.sendFailMessage(ClientSocketTypeEnum.NO_SYNC_RESULT,messageId, ExceptionUtil.getMessage(e));
        }
        return null;
    }
}
