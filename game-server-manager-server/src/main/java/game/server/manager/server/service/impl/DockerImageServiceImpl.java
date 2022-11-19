package game.server.manager.server.service.impl;

import com.github.dockerjava.api.model.Image;
import game.server.manager.docker.client.api.DockerClientApiEndpoint;
import game.server.manager.docker.client.api.DockerImageApi;
import game.server.manager.common.result.R;
import game.server.manager.server.service.DockerDetailsService;
import game.server.manager.server.service.DockerImageService;
import game.server.manager.server.vo.DockerDetailsVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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


}
