package plus.easydo.push.client.websocket.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import plus.easydo.push.client.config.JacksonObjectMapper;
import plus.easydo.push.client.contants.MessageTypeConstants;
import plus.easydo.push.client.model.socket.ServerMessage;
import plus.easydo.push.client.model.socket.ServerPullImageMessage;
import plus.easydo.push.client.service.DockerImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author yuzhanfeng
 * @Date 2022/11/26 23:29
 * @Description 服务端心跳处理服务
 */
@Slf4j
@Service(MessageTypeConstants.PULL_IMAGE)
public class PullImageHandlerService implements AbstractHandlerService {

    @Autowired
    private DockerImageService dockerImageService;

    @Autowired
    private JacksonObjectMapper mapper;

    @Override
    public Void handler(ServerMessage serverMessage) {
        log.info("OnMessageHandler pull ==> {}",serverMessage);
        String jsonData = serverMessage.getData();
        ServerPullImageMessage pullImageMessage = null;
        try {
            pullImageMessage = mapper.readValue(jsonData, ServerPullImageMessage.class);
        } catch (JsonProcessingException e) {
            log.error("序列化异常:{}",e.getMessage());
        }
        dockerImageService.pullImage(serverMessage.getMessageId(),pullImageMessage.getRepository());
        return null;
    }
}