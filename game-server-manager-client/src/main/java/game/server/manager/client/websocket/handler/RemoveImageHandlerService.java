package game.server.manager.client.websocket.handler;

import cn.hutool.core.exceptions.ExceptionUtil;
import game.server.manager.client.server.SyncServer;
import game.server.manager.client.service.DockerImageService;
import game.server.manager.common.constant.MessageTypeConstants;
import game.server.manager.common.enums.ClientSocketTypeEnum;
import game.server.manager.common.mode.socket.ServerMessage;
import game.server.manager.handler.AbstractHandlerService;
import game.server.manager.handler.annotation.HandlerService;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;


/**
 * @author yuzhanfeng
 * @Date 2022/11/26 23:29
 * @Description 服务端心跳处理服务
 */
@Slf4j
@HandlerService(MessageTypeConstants.REMOVE_IMAGE)
public class RemoveImageHandlerService extends AbstractHandlerService<ServerMessage, Void> {

    @Resource
    private SyncServer syncServer;

    @Resource
    private DockerImageService dockerImageService;

    @Override
    public Void handler(ServerMessage serverMessage) {
        log.info("OnMessageHandler remove ==> {}",serverMessage);
        String messageId = serverMessage.getMessageId();
        try {
            dockerImageService.removeImage(serverMessage.getData());
            syncServer.sendOkMessage(ClientSocketTypeEnum.NO_SYNC_RESULT,messageId,"success");
        }catch (Exception e) {
            syncServer.sendFailMessage(ClientSocketTypeEnum.NO_SYNC_RESULT,messageId, ExceptionUtil.getMessage(e));
        }
        return null;
    }
}
