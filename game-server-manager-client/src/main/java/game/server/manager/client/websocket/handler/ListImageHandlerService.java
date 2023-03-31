package game.server.manager.client.websocket.handler;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dockerjava.api.model.Image;
import game.server.manager.client.contants.MessageTypeConstants;
import game.server.manager.client.model.socket.ServerMessage;
import game.server.manager.client.server.SyncServer;
import game.server.manager.client.service.DockerImageService;
import game.server.manager.client.contants.ClientSocketTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author yuzhanfeng
 * @Date 2022/11/26 23:29
 * @Description 服务端心跳处理服务
 */
@Slf4j
@Service(MessageTypeConstants.LIST_IMAGE)
public class ListImageHandlerService implements AbstractHandlerService {

    @Autowired
    private SyncServer syncServer;

    @Autowired
    private DockerImageService dockerImageService;

    @Override
    public Void handler(ServerMessage serverMessage) {
        log.info("OnMessageHandler listImages ==> {}",serverMessage);
        String messageId = serverMessage.getMessageId();
        try {
            List<Image> listImages = dockerImageService.listImages();
            ObjectMapper mapper = new ObjectMapper();
            syncServer.sendOkMessage(ClientSocketTypeEnum.NO_SYNC_RESULT,messageId, mapper.writeValueAsString(listImages));
        }catch (Exception e) {
            syncServer.sendFailMessage(ClientSocketTypeEnum.NO_SYNC_RESULT,messageId, ExceptionUtil.getMessage(e));

        }
        return null;
    }
}
