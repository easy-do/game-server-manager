package game.server.manager.server.service.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.text.CharSequenceUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.Container;
import game.server.manager.common.enums.ClientModelEnum;
import game.server.manager.common.enums.ServerMessageTypeEnum;
import game.server.manager.common.exception.ExceptionFactory;
import game.server.manager.common.mode.socket.ServerContainerLogMessage;
import game.server.manager.common.mode.socket.ServerMessage;
import game.server.manager.common.vo.UserInfoVo;
import game.server.manager.docker.model.CreateContainerDto;
import game.server.manager.docker.service.DockerContainerBaseService;
import game.server.manager.server.entity.DockerDetails;
import game.server.manager.server.service.DockerContainerService;
import game.server.manager.server.service.DockerDetailsService;
import game.server.manager.server.util.DockerUtils;
import game.server.manager.server.util.SessionUtils;
import game.server.manager.common.mode.socket.RenameContainerData;
import game.server.manager.server.websocket.SocketSessionCache;
import game.server.manager.server.websocket.handler.browser.SocketContainerLogData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.websocket.Session;
import java.io.Serializable;
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
    private DockerContainerBaseService dockerContainerBaseService;


    private DockerDetails getDetails(Serializable dockerId) {
        DockerDetails dockerDetails = dockerDetailsService.getById(dockerId);
        if (Objects.isNull(dockerDetails)) {
            throw ExceptionFactory.bizException("docker不存在");
        }
        return dockerDetails;
    }


    @Override
    public List<Container> containerList(String dockerId) {
        DockerDetails dockerDetails = getDetails(dockerId);
        if (dockerDetails.getDockerModel().equals(ClientModelEnum.SOCKET.getType())) {
            Session clientSession = SessionUtils.getClientSession(dockerDetails.getClientId());
            String messageId = UUID.fastUUID().toString(true);
            ServerMessage serverMessage = ServerMessage.builder()
                    .messageId(messageId)
                    .type(ServerMessageTypeEnum.CONTAINER_LIST.getType())
                    .sync(0)
                    .build();
            return SessionUtils.sendMessageAndGetListResultMessage(clientSession, messageId, serverMessage);
        }
        if (dockerDetails.getDockerModel().equals(ClientModelEnum.HTTP.getType())) {
            DockerClient dockerClient = DockerUtils.createDockerClient(dockerDetails);
            return dockerContainerBaseService.containerList(dockerClient);
        }
        return null;
    }

    @Override
    public Object startContainer(Long dockerId, String containerId) {
        DockerDetails dockerDetails = getDetails(dockerId);
        if (dockerDetails.getDockerModel().equals(ClientModelEnum.SOCKET.getType())) {
            Session clientSession = SessionUtils.getClientSession(dockerDetails.getClientId());
            String messageId = UUID.fastUUID().toString(true);
            ServerMessage serverMessage = ServerMessage.builder()
                    .messageId(messageId)
                    .type(ServerMessageTypeEnum.START_CONTAINER.getType())
                    .sync(0)
                    .data(containerId)
                    .build();
            return sendMessageAndUnPackage(clientSession, messageId, serverMessage);
        }
        if (dockerDetails.getDockerModel().equals(ClientModelEnum.HTTP.getType())) {
            DockerClient dockerClient = DockerUtils.createDockerClient(dockerDetails);
            return dockerContainerBaseService.startContainer(dockerClient, containerId);
        }
        return null;
    }

    @Override
    public Object restartContainer(String dockerId, String containerId) {
        DockerDetails dockerDetails = getDetails(dockerId);
        if (dockerDetails.getDockerModel().equals(ClientModelEnum.SOCKET.getType())) {
            Session clientSession = SessionUtils.getClientSession(dockerDetails.getClientId());
            String messageId = UUID.fastUUID().toString(true);
            ServerMessage serverMessage = ServerMessage.builder()
                    .messageId(messageId)
                    .type(ServerMessageTypeEnum.RESTART_CONTAINER.getType())
                    .sync(0)
                    .data(containerId)
                    .build();
            return sendMessageAndUnPackage(clientSession, messageId, serverMessage);
        }
        if (dockerDetails.getDockerModel().equals(ClientModelEnum.HTTP.getType())) {
            DockerClient dockerClient = DockerUtils.createDockerClient(dockerDetails);
            return dockerContainerBaseService.restartContainer(dockerClient, containerId);
        }
        return null;
    }

    @Override
    public Object stopContainer(String dockerId, String containerId) {
        DockerDetails dockerDetails = getDetails(dockerId);
        if (dockerDetails.getDockerModel().equals(ClientModelEnum.SOCKET.getType())) {
            Session clientSession = SessionUtils.getClientSession(dockerDetails.getClientId());
            String messageId = UUID.fastUUID().toString(true);
            ServerMessage serverMessage = ServerMessage.builder()
                    .messageId(messageId)
                    .type(ServerMessageTypeEnum.STOP_CONTAINER.getType())
                    .sync(0)
                    .data(containerId)
                    .build();
            return sendMessageAndUnPackage(clientSession, messageId, serverMessage);
        }
        if (dockerDetails.getDockerModel().equals(ClientModelEnum.HTTP.getType())) {
            DockerClient dockerClient = DockerUtils.createDockerClient(dockerDetails);
            return dockerContainerBaseService.stopContainer(dockerClient, containerId);
        }
        return null;
    }

    @Override
    public Object removeContainer(String dockerId, String containerId) {
        DockerDetails dockerDetails = getDetails(dockerId);
        if (dockerDetails.getDockerModel().equals(ClientModelEnum.SOCKET.getType())) {
            Session clientSession = SessionUtils.getClientSession(dockerDetails.getClientId());
            String messageId = UUID.fastUUID().toString(true);
            ServerMessage serverMessage = ServerMessage.builder()
                    .messageId(messageId)
                    .type(ServerMessageTypeEnum.REMOVE_CONTAINER.getType())
                    .sync(0)
                    .data(containerId)
                    .build();
            return sendMessageAndUnPackage(clientSession, messageId, serverMessage);
        }
        if (dockerDetails.getDockerModel().equals(ClientModelEnum.HTTP.getType())) {
            DockerClient dockerClient = DockerUtils.createDockerClient(dockerDetails);
            return dockerContainerBaseService.removeContainer(dockerClient, containerId);
        }
        return null;
    }

    @Override
    public Object renameContainer(String dockerId, String containerId, String name) {
        DockerDetails dockerDetails = getDetails(dockerId);
        if (dockerDetails.getDockerModel().equals(ClientModelEnum.SOCKET.getType())) {
            Session clientSession = SessionUtils.getClientSession(dockerDetails.getClientId());
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
        if (dockerDetails.getDockerModel().equals(ClientModelEnum.HTTP.getType())) {
            DockerClient dockerClient = DockerUtils.createDockerClient(dockerDetails);
            return dockerContainerBaseService.renameContainer(dockerClient, containerId, name);
        }
        return null;
    }

    @Override
    public CreateContainerResponse createContainer(Long dockerId, CreateContainerDto createContainerDto) {
        DockerDetails dockerDetails = getDetails(dockerId);
        if (dockerDetails.getDockerModel().equals(ClientModelEnum.SOCKET.getType())) {
            Session clientSession = SessionUtils.getClientSession(dockerDetails.getClientId());
            String messageId = UUID.fastUUID().toString(true);
            ServerMessage serverMessage = ServerMessage.builder()
                    .messageId(messageId)
                    .type(ServerMessageTypeEnum.CREATE_CONTAINER.getType())
                    .sync(0)
                    .data(JSON.toJSONString(createContainerDto))
                    .build();
            String data = sendMessageAndUnPackage(clientSession, messageId, serverMessage);
            return JSONObject.parseObject(data,CreateContainerResponse.class);
        }
        if (dockerDetails.getDockerModel().equals(ClientModelEnum.HTTP.getType())) {
            DockerClient dockerClient = DockerUtils.createDockerClient(dockerDetails);
            return dockerContainerBaseService.createContainer(dockerClient, createContainerDto);
        }
        return null;
    }

    @Override
    public String logContainer(String dockerId, String containerId) {
        DockerDetails dockerDetails = getDetails(dockerId);
        if (dockerDetails.getDockerModel().equals(ClientModelEnum.SOCKET.getType())) {
            Session clientSession = SessionUtils.getClientSession(dockerDetails.getClientId());
            String messageId = UUID.fastUUID().toString(true);
            ServerMessage serverMessage = ServerMessage.builder()
                    .messageId(messageId)
                    .type(ServerMessageTypeEnum.CONTAINER_LOG.getType())
                    .sync(0)
                    .data(containerId)
                    .build();
            return sendMessageAndUnPackage(clientSession, messageId, serverMessage);
        }
        if (dockerDetails.getDockerModel().equals(ClientModelEnum.HTTP.getType())) {
            DockerClient dockerClient = DockerUtils.createDockerClient(dockerDetails);
            try {
                return dockerContainerBaseService.logContainer(dockerClient, containerId);
            } catch (InterruptedException e) {
                throw ExceptionFactory.bizException("获取容器日志异常:{}", e.getMessage());
            }
        }
        return null;
    }

    @Override
    public void socketContainerLog(SocketContainerLogData socketContainerLogData, UserInfoVo userInfo) {
        String dockerId = socketContainerLogData.getDockerId();
        LambdaQueryWrapper<DockerDetails> query = Wrappers.lambdaQuery();
        if (!userInfo.isAdmin()) {
            query.eq(DockerDetails::getCreateBy, userInfo.getId());
        }
        query.eq(DockerDetails::getId, dockerId);
        DockerDetails docker = dockerDetailsService.getOne(query);
        Session browserSession = SocketSessionCache.getBrowserByDockerId(dockerId);
        assert browserSession != null;
        //没有找到docker
        if (Objects.isNull(docker)) {
            SessionUtils.sendErrorServerMessage(browserSession, browserSession.getId(), "docker不存在");
            SessionUtils.close(browserSession);
            return;
        }
        if (docker.getDockerModel().equals(ClientModelEnum.SOCKET.getType())) {
            String clientId = docker.getClientId();
            Session clientSession = SocketSessionCache.getClientByClientId(clientId);
            if (Objects.isNull(clientSession)) {
                SessionUtils.sendErrorServerMessage(browserSession, browserSession.getId(), "客户端未连接");
                SessionUtils.close(browserSession);
                return;
            }
            SocketSessionCache.saveBrowserSIdAndClientSId(browserSession.getId(), clientSession.getId());
            ServerContainerLogMessage serverContainerLogMessage = ServerContainerLogMessage.builder()
                    .containerId(socketContainerLogData.getContainerId())
                    .build();
            SessionUtils.sendSyncServerMessage(clientSession, browserSession.getId(), JSON.toJSONString(serverContainerLogMessage), ServerMessageTypeEnum.CONTAINER_LOG);
        }
        if (docker.getDockerModel().equals(ClientModelEnum.HTTP.getType())) {
            DockerClient dockerClient = DockerUtils.createDockerClient(docker);
            try {
                String data = dockerContainerBaseService.logContainer(dockerClient, socketContainerLogData.getContainerId());
                List<String> stringList = CharSequenceUtil.split(data, "\n");
                for (String str: stringList) {
                    SessionUtils.sendOkServerMessage(browserSession,browserSession.getId(),str);
                }
                SessionUtils.sendSimpleServerMessage(browserSession,browserSession.getId(),"获取完毕",ServerMessageTypeEnum.SYNC_RESULT_END);
                SessionUtils.close(browserSession);
            } catch (InterruptedException e) {
                throw ExceptionFactory.bizException("获取容器镜像异常:{}", e.getMessage());
            }
        }
    }

    private <T> T sendMessageAndUnPackage(Session clientSession, String messageId, ServerMessage serverMessage) {
        return SessionUtils.sendMessageAndGetResultMessage(clientSession, messageId, serverMessage);
    }


}
