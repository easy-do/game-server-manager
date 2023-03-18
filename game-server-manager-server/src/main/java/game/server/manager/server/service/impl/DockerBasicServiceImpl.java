package game.server.manager.server.service.impl;

import cn.hutool.core.lang.UUID;
import game.server.manager.common.enums.ClientModelEnum;
import game.server.manager.common.enums.ServerMessageTypeEnum;
import game.server.manager.common.exception.ExceptionFactory;
import game.server.manager.common.mode.socket.ClientMessage;
import game.server.manager.common.result.DataResult;
import game.server.manager.docker.client.api.DockerClientApi;
import game.server.manager.docker.client.api.DockerClientApiEndpoint;
import game.server.manager.common.result.R;
import game.server.manager.server.entity.DockerDetails;
import game.server.manager.server.service.DockerBasicService;
import game.server.manager.server.service.DockerDetailsService;
import game.server.manager.server.websocket.SessionResultCache;
import game.server.manager.server.util.SessionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.websocket.Session;
import java.util.Objects;

/**
 * @author laoyu
 * @version 1.0
 * @description 基础接口
 * @date 2022/11/19
 */
@Service
public class DockerBasicServiceImpl implements DockerBasicService {

    @Resource
    private DockerDetailsService dockerDetailsService;

    @Resource
    private DockerClientApiEndpoint dockerClientApiEndpoint;

    private DockerDetails getDetails(String dockerId){
        DockerDetails dockerDetails = dockerDetailsService.getById(dockerId);
        if(Objects.isNull(dockerDetails)){
            throw ExceptionFactory.bizException("客户端不存在");
        }
        return dockerDetails;
    }

    private DockerClientApi dockerClientApi(DockerDetails dockerDetails){
        return dockerClientApiEndpoint.dockerClientApi(dockerDetails.getDockerHost(), dockerDetails.getDockerSecret());
    }

    @Override
    public Object ping(String dockerId) {
        DockerDetails dockerDetails = getDetails(dockerId);
        if(dockerDetails.getDockerModel().equals(ClientModelEnum.SOCKET.getType())){
            Session clientSession = SessionUtils.getClientSession(dockerDetails.getClientId());
            String messageId = UUID.fastUUID().toString(true);
            SessionUtils.sendSimpleNoSyncMessage(messageId,ServerMessageTypeEnum.PING_DOCKER,clientSession);
            ClientMessage clientMessage = SessionUtils.timeoutGetClientMessage(messageId, 3000);
            if(Objects.isNull(clientMessage)){
                return DataResult.fail("获取客户端消息超时.");
            }
            return clientMessage.isSuccess()? DataResult.ok(clientMessage.getData())
                    :DataResult.fail(clientMessage.getData());
        }
        return dockerClientApi(dockerDetails).ping();
    }

    @Override
    public R<String> info(String dockerId) {
        DockerDetails dockerDetails = getDetails(dockerId);
        if(dockerDetails.getDockerModel().equals(ClientModelEnum.SOCKET.getType())){
            Session clientSession = SessionUtils.getClientSession(dockerDetails.getClientId());
            String messageId = UUID.fastUUID().toString(true);
            SessionUtils.sendSimpleNoSyncMessage(messageId,ServerMessageTypeEnum.DOCKER_INFO,clientSession);
            ClientMessage<String> clientMessage = SessionUtils.timeoutGetClientMessage(messageId, 1000);
            if(Objects.isNull(clientMessage)){
                return DataResult.fail("获取客户端消息超时.");
            }
            return clientMessage.isSuccess()? DataResult.ok(clientMessage.getData())
                    :DataResult.fail(clientMessage.getData());
        }
        return dockerClientApi(dockerDetails).info();
    }

    @Override
    public R<String> version(String id) {
        DockerDetails dockerDetails = getDetails(id);
        if(dockerDetails.getDockerModel().equals(ClientModelEnum.SOCKET.getType())){
            Session clientSession = SessionUtils.getClientSession(dockerDetails.getClientId());
            String messageId = UUID.fastUUID().toString(true);
            SessionUtils.sendSimpleNoSyncMessage(messageId,ServerMessageTypeEnum.DOCKER_VERSION,clientSession);
            ClientMessage<String> clientMessage = SessionUtils.timeoutGetClientMessage(messageId, 1000);
            if(Objects.isNull(clientMessage)){
                return DataResult.fail("获取客户端消息超时.");
            }
            return clientMessage.isSuccess()? DataResult.ok(clientMessage.getData())
                    :DataResult.fail(clientMessage.getData());
        }
        return dockerClientApi(dockerDetails).version();
    }
}
