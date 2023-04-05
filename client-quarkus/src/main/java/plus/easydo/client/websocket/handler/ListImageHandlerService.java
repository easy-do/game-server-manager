package plus.easydo.client.websocket.handler;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.github.dockerjava.api.model.Image;
import plus.easydo.client.config.JacksonObjectMapper;
import plus.easydo.client.contants.ClientSocketTypeEnum;
import plus.easydo.client.contants.MessageTypeConstants;
import plus.easydo.client.model.socket.ServerMessage;
import plus.easydo.client.server.SyncServer;
import plus.easydo.client.service.DockerImageService;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;


import java.util.List;


/**
 * @author yuzhanfeng
 * @Date 2022/11/26 23:29
 * @Description 服务端心跳处理服务
 */
@Slf4j
@ApplicationScoped()
@Named(MessageTypeConstants.LIST_IMAGE)
public class ListImageHandlerService implements AbstractHandlerService {

    @Inject
     SyncServer syncServer;

    @Inject
    DockerImageService dockerImageService;

    @Inject
    JacksonObjectMapper mapper;

    @Override
    public Void handler(ServerMessage serverMessage) {
        log.info("OnMessageHandler listImages ==> {}",serverMessage);
        String messageId = serverMessage.getMessageId();
        try {
            List<Image> listImages = dockerImageService.listImages();
            syncServer.sendOkMessage(ClientSocketTypeEnum.NO_SYNC_RESULT,messageId, mapper.writeValueAsString(listImages));
        }catch (Exception e) {
            syncServer.sendFailMessage(ClientSocketTypeEnum.NO_SYNC_RESULT,messageId, ExceptionUtil.getMessage(e));

        }
        return null;
    }
}
