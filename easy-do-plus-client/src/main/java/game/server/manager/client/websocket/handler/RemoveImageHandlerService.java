package plus.easydo.push.client.websocket.handler;

import cn.hutool.core.exceptions.ExceptionUtil;
import plus.easydo.push.client.contants.ClientSocketTypeEnum;
import plus.easydo.push.client.contants.MessageTypeConstants;
import plus.easydo.push.client.model.socket.ServerMessage;
import plus.easydo.push.client.server.SyncServer;
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
@Service(MessageTypeConstants.REMOVE_IMAGE)
public class RemoveImageHandlerService implements AbstractHandlerService {

    @Autowired
    private SyncServer syncServer;

    @Autowired
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
