package game.server.manager.client.websocket.handler;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.alibaba.fastjson2.JSON;
import com.github.dockerjava.api.command.CreateContainerResponse;
import game.server.manager.client.server.SyncServer;
import game.server.manager.client.service.DockerContainerService;
import game.server.manager.common.constant.MessageTypeConstants;
import game.server.manager.common.enums.ClientSocketTypeEnum;
import game.server.manager.common.mode.socket.ServerMessage;
import game.server.manager.docker.model.CreateContainerDto;
import game.server.manager.handler.AbstractHandlerService;
import game.server.manager.handler.annotation.HandlerService;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;

/**
 * @author laoyu
 * @version 1.0
 * @Description 创建容器服务
 */
@Slf4j
@HandlerService(MessageTypeConstants.CREATE_CONTAINER)
public class CreateContainerHandlerService extends AbstractHandlerService<ServerMessage, Void> {
    @Resource
    private SyncServer syncServer;

    @Resource
    private DockerContainerService dockerContainerService;

    @Override
    public Void handler(ServerMessage serverMessage) {
        log.info("createContainer info ==> {}",serverMessage);
        String messageId = serverMessage.getMessageId();
        String jsonData = serverMessage.getData();
        CreateContainerDto createContainerDto = JSON.parseObject(jsonData, CreateContainerDto.class);
        try {
            CreateContainerResponse res = dockerContainerService.createContainer(createContainerDto);
            syncServer.sendOkMessage(ClientSocketTypeEnum.NO_SYNC_RESULT,messageId, JSON.toJSONString(res));
        }catch (Exception e) {
            syncServer.sendFailMessage(ClientSocketTypeEnum.NO_SYNC_RESULT,messageId, ExceptionUtil.getMessage(e));
        }
        return null;
    }
}