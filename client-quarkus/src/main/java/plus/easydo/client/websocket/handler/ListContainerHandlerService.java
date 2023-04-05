package plus.easydo.client.websocket.handler;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.github.dockerjava.api.model.Container;
import plus.easydo.client.config.JacksonObjectMapper;
import plus.easydo.client.contants.ClientSocketTypeEnum;
import plus.easydo.client.contants.MessageTypeConstants;
import plus.easydo.client.model.socket.ServerMessage;
import plus.easydo.client.server.SyncServer;
import plus.easydo.client.service.DockerContainerService;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;


import java.util.List;


/**
 * @author yuzhanfeng
 * @Date 2022/11/26 23:29
 * @Description 服务端心跳处理服务
 */
@Slf4j
@ApplicationScoped()
@Named(MessageTypeConstants.CONTAINER_LIST)
public class ListContainerHandlerService implements AbstractHandlerService {

    @Inject
    SyncServer syncServer;

    @Inject
    DockerContainerService dockerContainerService;

    @Inject
    JacksonObjectMapper mapper;

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
