package game.server.manager.server.service.impl;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.dockerjava.api.model.Image;
import game.server.manager.common.enums.ServerMessageTypeEnum;
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
import game.server.manager.server.websocket.SocketSessionCache;
import game.server.manager.server.websocket.handler.browser.SocketPullImageData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.websocket.Session;
import java.io.IOException;
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

    private DockerImageApi dockerImageApi(String dockerId){
        DockerDetailsVo dockerDetailsVo = dockerDetailsService.info(dockerId);
        return dockerClientApiEndpoint.dockerImageApi(dockerDetailsVo.getDockerHost(), dockerDetailsVo.getDockerSecret());
    }

    @Override
    public R<List<Image>> listImages(String dockerId) {
        return dockerImageApi(dockerId).listImages();
    }

    @Override
    public R<Void> removeImage(String dockerId, String imageId) {
        return dockerImageApi(dockerId).removeImage(imageId);
    }

    @Override
    public R<String> pullImage(String dockerId, String repository){
        return dockerImageApi(dockerId).pullImage(repository);
    }

    @Override
    public void socketPullImage(SocketPullImageData socketPullImageData, UserInfoVo userInfo){
        String dockerId = socketPullImageData.getDockerId();

        LambdaQueryWrapper<DockerDetails> query = Wrappers.lambdaQuery();
        if(!userInfo.isAdmin()){
            query.eq(DockerDetails::getCreateBy,userInfo.getId());
        }
        DockerDetails docker = dockerDetailsService.getOne(query);
        Session browserSession = SocketSessionCache.getBrowserByDockerId(dockerId);
        try {
            if(Objects.isNull(docker)){
                browserSession.getBasicRemote().sendText("docker不存在.或不属于你。");
                browserSession.close();
            }
            String clientId = docker.getClientId();
            Session clientSession = SocketSessionCache.getClientByClientId(clientId);
            if(Objects.isNull(clientSession)){
                browserSession.getBasicRemote().sendText("客户端未连接.");
                return;
            }
            SocketSessionCache.saveBrowserSIdAndClientSId(browserSession.getId(),clientSession.getId());
            ServerPullImageMessage pullImageMessage = ServerPullImageMessage.builder()
                    .repository(socketPullImageData.getRepository())
                    .build();
            ServerMessage serverMessage = ServerMessage.builder()
                    .messageId(browserSession.getId())
                    .sync(1)
                    .type(ServerMessageTypeEnum.PULL_IMAGE.getType())
                    .data(JSON.toJSONString(pullImageMessage)).build();
            clientSession.getBasicRemote().sendText(JSON.toJSONString(serverMessage));
        }catch (IOException e) {
            log.error("socket pull镜像异常，{}", ExceptionUtil.getMessage(e));
        }

    }


}
