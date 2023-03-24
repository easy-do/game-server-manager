package game.server.manager.client.websocket.handler;

import cn.hutool.core.exceptions.ExceptionUtil;
import game.server.manager.client.server.SyncServer;
import game.server.manager.client.service.DockerContainerService;
import game.server.manager.common.constant.MessageTypeConstants;
import game.server.manager.common.enums.ClientSocketTypeEnum;
import game.server.manager.common.mode.socket.ServerMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * @author yuzhanfeng
 * @Date 2022/11/26 23:29
 * @Description 服务端心跳处理服务
 */
@Slf4j
@Service(MessageTypeConstants.RESTART_CONTAINER)
public class RestartContainerHandlerService implements AbstractHandlerService {

    @Resource
    private SyncServer syncServer;

    @Resource
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
