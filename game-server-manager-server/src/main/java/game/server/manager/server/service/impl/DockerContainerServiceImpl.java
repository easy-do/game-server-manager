package game.server.manager.server.service.impl;

import cn.hutool.core.lang.UUID;
import com.alibaba.fastjson2.JSON;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.Container;
import game.server.manager.common.enums.ClientModelEnum;
import game.server.manager.common.enums.ServerMessageTypeEnum;
import game.server.manager.common.exception.ExceptionFactory;
import game.server.manager.common.mode.socket.ClientMessage;
import game.server.manager.common.mode.socket.ServerMessage;
import game.server.manager.common.result.DataResult;
import game.server.manager.docker.client.api.DockerClientApiEndpoint;
import game.server.manager.docker.client.api.DockerContainerApi;
import game.server.manager.common.result.R;
import game.server.manager.docker.model.CreateContainerDto;
import game.server.manager.server.service.DockerContainerService;
import game.server.manager.server.service.DockerDetailsService;
import game.server.manager.server.vo.DockerDetailsVo;
import game.server.manager.server.websocket.SessionResultCache;
import game.server.manager.server.util.SessionUtils;
import game.server.manager.common.mode.socket.RenameContainerData;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.websocket.Session;
import java.util.List;
import java.util.Objects;

/**
 * @author laoyu
 * @version 1.0
 * @description 容器相关
 * @date 2022/11/19
 */
@Service
public class DockerContainerServiceImpl implements DockerContainerService {

    @Resource
    private DockerDetailsService dockerDetailsService;

    @Resource
    private DockerClientApiEndpoint dockerClientApiEndpoint;

    private DockerDetailsVo getDetails(String dockerId){
        DockerDetailsVo dockerDetailsVo = dockerDetailsService.info(dockerId);
        if(Objects.isNull(dockerDetailsVo)){
            throw ExceptionFactory.bizException("客户端不存在");
        }
        return dockerDetailsVo;
    }

    private DockerContainerApi dockerContainerApi(String dockerId){
        DockerDetailsVo dockerDetailsVo = dockerDetailsService.info(dockerId);
        return dockerClientApiEndpoint.dockerContainerApi(dockerDetailsVo.getDockerHost(), dockerDetailsVo.getDockerSecret());
    }

    @Override
    public R<List<Container>> containerList(String dockerId) {
        DockerDetailsVo dockerDetailsVo = getDetails(dockerId);
        if(dockerDetailsVo.getDockerModel().equals(ClientModelEnum.SOCKET.getType())){
            Session clientSession = SessionUtils.getClientSession(dockerDetailsVo.getClientId());
            String messageId = UUID.fastUUID().toString(true);
            ServerMessage serverMessage = ServerMessage.builder()
                    .messageId(messageId)
                    .type(ServerMessageTypeEnum.CONTAINER_LIST.getType())
                    .sync(0)
                    .build();
            return SessionUtils.sendMessageAndGetListResultMessage(clientSession, messageId, serverMessage);
        }
        return dockerContainerApi(dockerId).containerList();
    }

    @Override
    public R<Void> startContainer(String dockerId, String containerId) {
        DockerDetailsVo dockerDetailsVo = getDetails(dockerId);
        if(dockerDetailsVo.getDockerModel().equals(ClientModelEnum.SOCKET.getType())){
            Session clientSession = SessionUtils.getClientSession(dockerDetailsVo.getClientId());
            String messageId = UUID.fastUUID().toString(true);
            ServerMessage serverMessage = ServerMessage.builder()
                    .messageId(messageId)
                    .type(ServerMessageTypeEnum.START_CONTAINER.getType())
                    .sync(0)
                    .data(containerId)
                    .build();
            return sendMessageAndUnPackage(clientSession, messageId, serverMessage);
        }
        return dockerContainerApi(dockerId).startContainer(containerId);
    }

    @Override
    public R<Void> restartContainer(String dockerId, String containerId) {
        DockerDetailsVo dockerDetailsVo = getDetails(dockerId);
        if(dockerDetailsVo.getDockerModel().equals(ClientModelEnum.SOCKET.getType())){
            Session clientSession = SessionUtils.getClientSession(dockerDetailsVo.getClientId());
            String messageId = UUID.fastUUID().toString(true);
            ServerMessage serverMessage = ServerMessage.builder()
                    .messageId(messageId)
                    .type(ServerMessageTypeEnum.RESTART_CONTAINER.getType())
                    .sync(0)
                    .data(containerId)
                    .build();
            return sendMessageAndUnPackage(clientSession, messageId, serverMessage);
        }
        return dockerContainerApi(dockerId).restartContainer(containerId);
    }

    @Override
    public R<Void> stopContainer(String dockerId, String containerId) {
        DockerDetailsVo dockerDetailsVo = getDetails(dockerId);
        if(dockerDetailsVo.getDockerModel().equals(ClientModelEnum.SOCKET.getType())){
            Session clientSession = SessionUtils.getClientSession(dockerDetailsVo.getClientId());
            String messageId = UUID.fastUUID().toString(true);
            ServerMessage serverMessage = ServerMessage.builder()
                    .messageId(messageId)
                    .type(ServerMessageTypeEnum.STOP_CONTAINER.getType())
                    .sync(0)
                    .data(containerId)
                    .build();
            return sendMessageAndUnPackage(clientSession, messageId, serverMessage);
        }
        return dockerContainerApi(dockerId).stopContainer(containerId);
    }

    @Override
    public R<Void> removeContainer(String dockerId, String containerId) {
        DockerDetailsVo dockerDetailsVo = getDetails(dockerId);
        if(dockerDetailsVo.getDockerModel().equals(ClientModelEnum.SOCKET.getType())){
            Session clientSession = SessionUtils.getClientSession(dockerDetailsVo.getClientId());
            String messageId = UUID.fastUUID().toString(true);
            ServerMessage serverMessage = ServerMessage.builder()
                    .messageId(messageId)
                    .type(ServerMessageTypeEnum.REMOVE_CONTAINER.getType())
                    .sync(0)
                    .data(containerId)
                    .build();
            return sendMessageAndUnPackage(clientSession, messageId, serverMessage);
        }
        return dockerContainerApi(dockerId).removeContainer(containerId);
    }

    @Override
    public R<Void> renameContainer(String dockerId, String containerId, String name) {
        DockerDetailsVo dockerDetailsVo = getDetails(dockerId);
        if(dockerDetailsVo.getDockerModel().equals(ClientModelEnum.SOCKET.getType())){
            Session clientSession = SessionUtils.getClientSession(dockerDetailsVo.getClientId());
            String messageId = UUID.fastUUID().toString(true);
            ServerMessage serverMessage = ServerMessage.builder()
                    .messageId(messageId)
                    .type(ServerMessageTypeEnum.RENAME_CONTAINER.getType())
                    .sync(0)
                    .data(JSON.toJSONString(RenameContainerData.builder()
                            .containerId(containerId)
                            .name(name).build()))
                    .build();
            return sendMessageAndUnPackage(clientSession, messageId, serverMessage);
        }
        return dockerContainerApi(dockerId). renameContainer(containerId,name);
    }

    @Override
    public R<CreateContainerResponse> createContainer(String dockerId, CreateContainerDto createContainerDto) {
        return dockerContainerApi(dockerId).createContainer(createContainerDto);
    }

    @Override
    public R<String> logContainer(String dockerId, String containerId) {
        return dockerContainerApi(dockerId).logContainer(containerId);
    }

    @NotNull
    private R<Void> sendMessageAndUnPackage(Session clientSession, String messageId, ServerMessage serverMessage) {
        return SessionUtils.sendMessageAndGetResultMessage(clientSession, messageId, serverMessage);
    }
}
