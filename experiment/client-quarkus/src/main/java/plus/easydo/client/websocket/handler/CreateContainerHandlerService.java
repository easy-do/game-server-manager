package plus.easydo.client.websocket.handler;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.dockerjava.api.command.CreateContainerResponse;
import plus.easydo.client.config.JacksonObjectMapper;
import plus.easydo.client.contants.ClientSocketTypeEnum;
import plus.easydo.client.contants.MessageTypeConstants;
import plus.easydo.client.model.CreateContainerDto;
import plus.easydo.client.model.socket.ServerMessage;
import plus.easydo.client.server.SyncServer;
import plus.easydo.client.service.DockerContainerService;
import lombok.extern.slf4j.Slf4j;
import javax.inject.Inject;


import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

/**
 * @author laoyu
 * @version 1.0
 * @Description 创建容器服务
 */
@Slf4j
@ApplicationScoped()
@Named(MessageTypeConstants.CREATE_CONTAINER)
public class CreateContainerHandlerService implements AbstractHandlerService {
    @Inject
    SyncServer syncServer;

    @Inject
    DockerContainerService dockerContainerService;

    @Inject
    JacksonObjectMapper mapper;

    @Override
    public Void handler(ServerMessage serverMessage) {
        log.info("createContainer info ==> {}",serverMessage);
        String messageId = serverMessage.getMessageId();
        String jsonData = serverMessage.getData();
        CreateContainerDto createContainerDto = null;
        try {
            createContainerDto = mapper.readValue(jsonData, CreateContainerDto.class);
        } catch (JsonProcessingException e) {
            log.error("序列化异常:{}",e.getMessage());
        }
        try {
            CreateContainerResponse res = dockerContainerService.createContainer(createContainerDto);
            syncServer.sendOkMessage(ClientSocketTypeEnum.NO_SYNC_RESULT,messageId, mapper.writeValueAsString(res));
        }catch (Exception e) {
            syncServer.sendFailMessage(ClientSocketTypeEnum.NO_SYNC_RESULT,messageId, ExceptionUtil.getMessage(e));
        }
        return null;
    }
}
