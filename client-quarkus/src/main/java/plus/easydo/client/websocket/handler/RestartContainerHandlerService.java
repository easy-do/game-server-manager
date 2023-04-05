package plus.easydo.client.websocket.handler;

import cn.hutool.core.exceptions.ExceptionUtil;
import plus.easydo.client.contants.ClientSocketTypeEnum;
import plus.easydo.client.contants.MessageTypeConstants;
import plus.easydo.client.model.socket.ServerMessage;
import plus.easydo.client.server.SyncServer;
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
@ApplicationScoped
@Named(MessageTypeConstants.RESTART_CONTAINER)
public class RestartContainerHandlerService implements AbstractHandlerService {

    @Inject
    SyncServer syncServer;

    @Inject
    DockerContainerService dockerContainerService;

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
