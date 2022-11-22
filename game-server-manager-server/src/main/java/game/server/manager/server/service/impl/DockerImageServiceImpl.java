package game.server.manager.server.service.impl;

import com.alibaba.fastjson2.JSON;
import com.github.dockerjava.api.model.Image;
import game.server.manager.common.enums.ServerMessageTypeEnum;
import game.server.manager.common.mode.socket.ServerMessage;
import game.server.manager.common.mode.socket.ServerPullImageMessage;
import game.server.manager.docker.client.api.DockerClientApiEndpoint;
import game.server.manager.docker.client.api.DockerImageApi;
import game.server.manager.common.result.R;
import game.server.manager.server.entity.DockerDetails;
import game.server.manager.server.websocket.SocketSessionCache;
import game.server.manager.server.websocket.model.SocketPullImageData;
import game.server.manager.server.service.DockerDetailsService;
import game.server.manager.server.service.DockerImageService;
import game.server.manager.server.vo.DockerDetailsVo;
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
    public void socketPullImage(SocketPullImageData socketPullImageData) throws IOException {
        String dockerId = socketPullImageData.getDockerId();
        DockerDetails docker = dockerDetailsService.getById(dockerId);
        Session browserSession = SocketSessionCache.getBrowserByDockerId(dockerId);
        if(Objects.isNull(docker)){
            browserSession.getBasicRemote().sendText("docker不存在.");
            browserSession.close();
        }
        String clientId = docker.getClientId();
        //
        Session clientSession = SocketSessionCache.getClient(clientId);
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
                .jsonData(JSON.toJSONString(pullImageMessage)).build();
        clientSession.getBasicRemote().sendText(JSON.toJSONString(serverMessage));
    }


}
