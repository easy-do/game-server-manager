package plus.easydo.server.service.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.dockerjava.api.DockerClient;
import plus.easydo.common.enums.ClientModelEnum;
import plus.easydo.common.enums.ServerMessageTypeEnum;
import plus.easydo.common.exception.ExceptionFactory;
import plus.easydo.common.mode.socket.ClientMessage;
import plus.easydo.common.result.DataResult;
import plus.easydo.docker.service.DockerBaseService;
import plus.easydo.server.entity.DockerDetails;
import plus.easydo.server.service.DockerBasicService;
import plus.easydo.server.service.DockerDetailsService;
import plus.easydo.server.util.server.DockerUtils;
import plus.easydo.server.util.server.SessionUtils;
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
    private DockerBaseService dockerBaseService;

    private DockerDetails getDetails(String dockerId){
        DockerDetails dockerDetails = dockerDetailsService.getById(dockerId);
        if(Objects.isNull(dockerDetails)){
            throw ExceptionFactory.bizException("客户端不存在");
        }
        return dockerDetails;
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
        if(dockerDetails.getDockerModel().equals(ClientModelEnum.HTTP.getType())){
            DockerClient dockerClient = DockerUtils.createDockerClient(dockerDetails);
            return dockerBaseService.ping(dockerClient);
        }
        return null;
    }

    @Override
    public JSON info(String dockerId) throws JsonProcessingException {
        DockerDetails dockerDetails = getDetails(dockerId);
        if(dockerDetails.getDockerModel().equals(ClientModelEnum.SOCKET.getType())){
            Session clientSession = SessionUtils.getClientSession(dockerDetails.getClientId());
            String messageId = UUID.fastUUID().toString(true);
            SessionUtils.sendSimpleNoSyncMessage(messageId,ServerMessageTypeEnum.DOCKER_INFO,clientSession);
            ClientMessage clientMessage = SessionUtils.timeoutGetClientMessage(messageId, 1000);
            if(Objects.isNull(clientMessage)){
                throw ExceptionFactory.bizException("获取客户端消息失败");
            }
            if(clientMessage.isSuccess()){

                return JSONUtil.parse(clientMessage.getData());
            }
            throw ExceptionFactory.bizException(clientMessage.getData());
        }
        if(dockerDetails.getDockerModel().equals(ClientModelEnum.HTTP.getType())){
            DockerClient dockerClient = DockerUtils.createDockerClient(dockerDetails);
            return JSONUtil.parse(dockerBaseService.info(dockerClient));
        }
        return null;
    }

    @Override
    public JSON version(String id) {
        DockerDetails dockerDetails = getDetails(id);
        if(dockerDetails.getDockerModel().equals(ClientModelEnum.SOCKET.getType())){
            Session clientSession = SessionUtils.getClientSession(dockerDetails.getClientId());
            String messageId = UUID.fastUUID().toString(true);
            SessionUtils.sendSimpleNoSyncMessage(messageId,ServerMessageTypeEnum.DOCKER_VERSION,clientSession);
            ClientMessage clientMessage = SessionUtils.timeoutGetClientMessage(messageId, 1000);
            if(Objects.isNull(clientMessage)){
                throw ExceptionFactory.bizException("获取客户端消息失败.");
            }
            if(clientMessage.isSuccess()){
                return JSONUtil.parse(clientMessage.getData());
            }
            throw ExceptionFactory.bizException(clientMessage.getData());
        }
        if(dockerDetails.getDockerModel().equals(ClientModelEnum.HTTP.getType())){
            DockerClient dockerClient = DockerUtils.createDockerClient(dockerDetails);
            return JSONUtil.parse(dockerBaseService.version(dockerClient));
        }
        return null;
    }
}
