package game.server.manager.server.service.impl;

import cn.hutool.core.lang.UUID;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Image;
import game.server.manager.common.enums.ClientModelEnum;
import game.server.manager.common.enums.ServerMessageTypeEnum;
import game.server.manager.common.exception.ExceptionFactory;
import game.server.manager.common.mode.socket.ServerMessage;
import game.server.manager.common.mode.socket.ServerPullImageMessage;
import game.server.manager.common.vo.UserInfoVo;
import game.server.manager.docker.service.DockerImageBaseService;
import game.server.manager.server.entity.DockerDetails;
import game.server.manager.server.service.DockerDetailsService;
import game.server.manager.server.service.DockerImageService;
import game.server.manager.server.util.DockerUtils;
import game.server.manager.server.util.SessionUtils;
import game.server.manager.server.websocket.SocketSessionCache;
import game.server.manager.server.websocket.handler.browser.SocketPullImageData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.websocket.Session;
import java.util.List;
import java.util.Objects;

/**
 * @author laoyu
 * @version 1.0
 * @description dicker 镜像相关
 * @date 2022/11/19
 */
@Slf4j
@Service
public class DockerImageServiceImpl implements DockerImageService {

    @Resource
    private DockerDetailsService dockerDetailsService;

    @Resource
    private DockerImageBaseService dockerImageBaseService;

    private DockerDetails getDetails(String dockerId){
        DockerDetails dockerDetails = dockerDetailsService.getById(dockerId);
        if(Objects.isNull(dockerDetails)){
            throw ExceptionFactory.bizException("客户端不存在");
        }
        return dockerDetails;
    }

    @Override
    public List<Image> listImages(String dockerId) {
        DockerDetails dockerDetails = getDetails(dockerId);
        if(dockerDetails.getDockerModel().equals(ClientModelEnum.SOCKET.getType())){
            Session clientSession = SessionUtils.getClientSession(dockerDetails.getClientId());
            String messageId = UUID.fastUUID().toString(true);
            ServerMessage serverMessage = ServerMessage.builder()
                    .messageId(messageId)
                    .type(ServerMessageTypeEnum.LIST_IMAGE.getType())
                    .sync(0)
                    .build();
            return SessionUtils.sendMessageAndGetListResultMessage(clientSession, messageId, serverMessage);
        }
        if(dockerDetails.getDockerModel().equals(ClientModelEnum.HTTP.getType())){
            DockerClient dockerClient = DockerUtils.createDockerClient(dockerDetails);
            return dockerImageBaseService.listImages(dockerClient);
        }
       return null;
    }


    @Override
    public Object removeImage(String dockerId, String imageId) {
        DockerDetails dockerDetails = getDetails(dockerId);
        if(dockerDetails.getDockerModel().equals(ClientModelEnum.SOCKET.getType())){
            Session clientSession = SessionUtils.getClientSession(dockerDetails.getClientId());
            String messageId = UUID.fastUUID().toString(true);
            ServerMessage serverMessage = ServerMessage.builder()
                    .messageId(messageId)
                    .type(ServerMessageTypeEnum.REMOVE_IMAGE.getType())
                    .sync(0)
                    .data(imageId)
                    .build();
            return SessionUtils.sendMessageAndGetResultMessage(clientSession, messageId, serverMessage);
        }
        if(dockerDetails.getDockerModel().equals(ClientModelEnum.HTTP.getType())){
            DockerClient dockerClient = DockerUtils.createDockerClient(dockerDetails);
            return dockerImageBaseService.removeImage(dockerClient,imageId);
        }
        return null;
    }


    @Override
    public String pullImage(String dockerId, String repository){
        DockerDetails dockerDetails = getDetails(dockerId);
        if(dockerDetails.getDockerModel().equals(ClientModelEnum.SOCKET.getType())){
            Session clientSession = SessionUtils.getClientSession(dockerDetails.getClientId());
            String messageId = UUID.fastUUID().toString(true);
            ServerMessage serverMessage = ServerMessage.builder()
                    .messageId(messageId)
                    .type(ServerMessageTypeEnum.PULL_IMAGE.getType())
                    .sync(0)
                    .data(repository)
                    .build();
            return SessionUtils.sendMessageAndGetResultMessage(clientSession, messageId, serverMessage);
        }
        if(dockerDetails.getDockerModel().equals(ClientModelEnum.HTTP.getType())){
            DockerClient dockerClient = DockerUtils.createDockerClient(dockerDetails);
            try {
                return dockerImageBaseService.pullImage(dockerClient,repository);
            }catch (InterruptedException exception){
                throw ExceptionFactory.bizException("pull镜像异常:{}",exception.getMessage());
            }
        }
        return null;
    }

    @Override
    public void socketPullImage(SocketPullImageData socketPullImageData, UserInfoVo userInfo){
        String dockerId = socketPullImageData.getDockerId();
        LambdaQueryWrapper<DockerDetails> query = Wrappers.lambdaQuery();
        if(!userInfo.isAdmin()){
            query.eq(DockerDetails::getCreateBy,userInfo.getId());
        }
        query.eq(DockerDetails::getId,dockerId);
        DockerDetails docker = dockerDetailsService.getOne(query);
        Session browserSession = SocketSessionCache.getBrowserByDockerId(dockerId);
        assert browserSession != null;
        if (Objects.isNull(docker)) {
            SessionUtils.sendErrorServerMessage(browserSession,browserSession.getId(),"docker不存在");
            SessionUtils.close(browserSession);
            return;
        }
        String clientId = docker.getClientId();
        Session clientSession = SocketSessionCache.getClientByClientId(clientId);
        if(Objects.isNull(clientSession)){
            SessionUtils.sendErrorServerMessage(browserSession,browserSession.getId(),"客户端未连接");
            SessionUtils.close(browserSession);
            return;
        }
            SocketSessionCache.saveBrowserSIdAndClientSId(browserSession.getId(),clientSession.getId());
            ServerPullImageMessage pullImageMessage = ServerPullImageMessage.builder()
                    .repository(socketPullImageData.getRepository())
                    .build();
            SessionUtils.sendSyncServerMessage(clientSession,browserSession.getId(),JSON.toJSONString(pullImageMessage),ServerMessageTypeEnum.PULL_IMAGE);
    }


}
