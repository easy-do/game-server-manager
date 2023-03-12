package game.server.manager.server.service.impl;

import cn.hutool.core.lang.UUID;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.dockerjava.api.model.Image;
import game.server.manager.common.enums.ClientModelEnum;
import game.server.manager.common.enums.ServerMessageTypeEnum;
import game.server.manager.common.exception.ExceptionFactory;
import game.server.manager.common.mode.socket.ServerMessage;
import game.server.manager.common.mode.socket.ServerPullImageMessage;
import game.server.manager.common.result.R;
import game.server.manager.common.vo.UserInfoVo;
import game.server.manager.docker.client.api.DockerClientApiEndpoint;
import game.server.manager.docker.client.api.DockerImageApi;
import game.server.manager.server.entity.DockerDetails;
import game.server.manager.server.service.DockerDetailsService;
import game.server.manager.server.service.DockerImageService;
import game.server.manager.server.vo.DockerDetailsVo;
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
    private DockerClientApiEndpoint dockerClientApiEndpoint;

    private DockerDetailsVo getDetails(String dockerId){
        DockerDetailsVo dockerDetailsVo = dockerDetailsService.info(dockerId);
        if(Objects.isNull(dockerDetailsVo)){
            throw ExceptionFactory.bizException("客户端不存在");
        }
        return dockerDetailsVo;
    }


    private DockerImageApi dockerImageApi(DockerDetailsVo dockerDetailsVo){
        return dockerClientApiEndpoint.dockerImageApi(dockerDetailsVo.getDockerHost(), dockerDetailsVo.getDockerSecret());
    }

    @Override
    public R<List<Image>> listImages(String dockerId) {
        DockerDetailsVo dockerDetailsVo = getDetails(dockerId);
        if(dockerDetailsVo.getDockerModel().equals(ClientModelEnum.SOCKET.getType())){
            Session clientSession = SessionUtils.getClientSession(dockerDetailsVo.getClientId());
            String messageId = UUID.fastUUID().toString(true);
            ServerMessage serverMessage = ServerMessage.builder()
                    .messageId(messageId)
                    .type(ServerMessageTypeEnum.LIST_IMAGE.getType())
                    .sync(0)
                    .build();
            return SessionUtils.sendMessageAndGetListResultMessage(clientSession, messageId, serverMessage);
        }
        return dockerImageApi(dockerDetailsVo).listImages();
    }


    @Override
    public R<Object> removeImage(String dockerId, String imageId) {
        DockerDetailsVo dockerDetailsVo = getDetails(dockerId);
        if(dockerDetailsVo.getDockerModel().equals(ClientModelEnum.SOCKET.getType())){
            Session clientSession = SessionUtils.getClientSession(dockerDetailsVo.getClientId());
            String messageId = UUID.fastUUID().toString(true);
            ServerMessage serverMessage = ServerMessage.builder()
                    .messageId(messageId)
                    .type(ServerMessageTypeEnum.REMOVE_IMAGE.getType())
                    .sync(0)
                    .data(imageId)
                    .build();
            return SessionUtils.sendMessageAndGetResultMessage(clientSession, messageId, serverMessage);
        }
        return dockerImageApi(dockerDetailsVo).removeImage(imageId);
    }


    @Override
    public R<String> pullImage(String dockerId, String repository){
        DockerDetailsVo dockerDetailsVo = getDetails(dockerId);
        return dockerImageApi(dockerDetailsVo).pullImage(repository);
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
