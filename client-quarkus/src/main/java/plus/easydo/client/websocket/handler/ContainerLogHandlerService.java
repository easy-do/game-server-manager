package plus.easydo.client.websocket.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import plus.easydo.client.config.JacksonObjectMapper;
import plus.easydo.client.contants.MessageTypeConstants;
import plus.easydo.client.model.socket.ServerContainerLogMessage;
import plus.easydo.client.model.socket.ServerMessage;
import plus.easydo.client.service.DockerContainerService;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;


/**
 * @author yuzhanfeng
 * @Date 2022/11/26 23:29
 * @Description 服务端心跳处理服务
 */
@Slf4j
@ApplicationScoped()
@Named(MessageTypeConstants.CONTAINER_LOG)
public class ContainerLogHandlerService implements AbstractHandlerService {


    @Inject
    DockerContainerService dockerContainerService;

    @Inject
    JacksonObjectMapper mapper;

    @Override
    public Void handler(ServerMessage serverMessage) {
        log.info("OnMessageHandler containerLog ==> {}",serverMessage);
        String jsonData = serverMessage.getData();
        ServerContainerLogMessage containerLogMessage = null;
        try {
            containerLogMessage = mapper.readValue(jsonData, ServerContainerLogMessage.class);
        } catch (JsonProcessingException e) {
            log.error("序列化异常:{}",e.getMessage());
        }
        dockerContainerService.logContainer(serverMessage.getMessageId(),containerLogMessage.getContainerId());
        return null;
    }
}
