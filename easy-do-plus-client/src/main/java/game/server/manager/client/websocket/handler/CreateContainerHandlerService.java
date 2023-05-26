package plus.easydo.push.client.websocket.handler;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.dockerjava.api.command.CreateContainerResponse;
import plus.easydo.push.client.config.JacksonObjectMapper;
import plus.easydo.push.client.contants.ClientSocketTypeEnum;
import plus.easydo.push.client.contants.MessageTypeConstants;
import plus.easydo.push.client.model.CreateContainerDto;
import plus.easydo.push.client.model.socket.ServerMessage;
import plus.easydo.push.client.server.SyncServer;
import plus.easydo.push.client.service.DockerContainerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author laoyu
 * @version 1.0
 * @Description 创建容器服务
 */
@Slf4j
@Service(MessageTypeConstants.CREATE_CONTAINER)
public class CreateContainerHandlerService implements AbstractHandlerService {
    @Autowired
    private SyncServer syncServer;

    @Autowired
    private DockerContainerService dockerContainerService;

    @Autowired
    private JacksonObjectMapper mapper;

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
