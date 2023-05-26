package plus.easydo.push.client.websocket.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import plus.easydo.push.client.config.JacksonObjectMapper;
import plus.easydo.push.client.contants.MessageTypeConstants;
import plus.easydo.push.client.model.socket.ServerContainerLogMessage;
import plus.easydo.push.client.model.socket.ServerMessage;
import plus.easydo.push.client.service.DockerContainerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * @author yuzhanfeng
 * @Date 2022/11/26 23:29
 * @Description 服务端心跳处理服务
 */
@Slf4j
@Service(MessageTypeConstants.CONTAINER_LOG)
public class ContainerLogHandlerService implements AbstractHandlerService {


    @Autowired
    private DockerContainerService dockerContainerService;

    @Autowired
    private JacksonObjectMapper mapper;

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
