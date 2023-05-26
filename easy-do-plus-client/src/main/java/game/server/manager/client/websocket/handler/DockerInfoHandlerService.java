package plus.easydo.push.client.websocket.handler;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.github.dockerjava.api.model.Info;
import plus.easydo.push.client.config.JacksonObjectMapper;
import plus.easydo.push.client.contants.ClientSocketTypeEnum;
import plus.easydo.push.client.contants.MessageTypeConstants;
import plus.easydo.push.client.model.socket.ServerMessage;
import plus.easydo.push.client.server.SyncServer;
import plus.easydo.push.client.service.DockerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author yuzhanfeng
 * @Date 2022/11/26 23:29
 * @Description 服务端心跳处理服务
 */
@Slf4j
@Service(MessageTypeConstants.DOCKER_INFO)
public class DockerInfoHandlerService implements AbstractHandlerService {

    @Autowired
    private SyncServer syncServer;

    @Autowired
    private DockerService dockerService;

    @Autowired
    private JacksonObjectMapper mapper;

    @Override
    public Void handler(ServerMessage serverMessage) {
        log.info("OnMessageHandler info ==> {}",serverMessage);
        String messageId = serverMessage.getMessageId();
        try {
            Info info = dockerService.info();
            syncServer.sendOkMessage(ClientSocketTypeEnum.NO_SYNC_RESULT,messageId, mapper.writeValueAsString(info));
        }catch (Exception e) {
            syncServer.sendFailMessage(ClientSocketTypeEnum.NO_SYNC_RESULT,messageId, ExceptionUtil.getMessage(e));
        }
        return null;
    }
}
