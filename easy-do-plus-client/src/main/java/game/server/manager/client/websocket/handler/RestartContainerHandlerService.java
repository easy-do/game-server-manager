package plus.easydo.push.client.websocket.handler;

import cn.hutool.core.exceptions.ExceptionUtil;
import plus.easydo.push.client.contants.ClientSocketTypeEnum;
import plus.easydo.push.client.contants.MessageTypeConstants;
import plus.easydo.push.client.model.socket.ServerMessage;
import plus.easydo.push.client.server.SyncServer;
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
@Service(MessageTypeConstants.RESTART_CONTAINER)
public class RestartContainerHandlerService implements AbstractHandlerService {

    @Autowired
    private SyncServer syncServer;

    @Autowired
    private DockerContainerService dockerContainerService;

    @Override
    public Void handler(ServerMessage serverMessage) {
        log.info("OnMessageHandler restartContainer ==> {}",serverMessage);
        String messageId = serverMessage.getMessageId();
        try {
            dockerContainerService.restartContainer(serverMessage.getData());
            syncServer.sendOkMessage(ClientSocketTypeEnum.NO_SYNC_RESULT,messageId, "success");
        }catch (Exception e) {
            syncServer.sendFailMessage(ClientSocketTypeEnum.NO_SYNC_RESULT,messageId, ExceptionUtil.getMessage(e));
        }
        return null;
    }
}