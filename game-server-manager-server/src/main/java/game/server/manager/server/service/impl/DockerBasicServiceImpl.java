package game.server.manager.server.service.impl;

import com.github.dockerjava.api.model.Info;
import game.server.manager.docker.client.api.DockerClientApi;
import game.server.manager.docker.client.api.DockerClientApiEndpoint;
import game.server.manager.common.result.R;
import game.server.manager.server.service.DockerBasicService;
import game.server.manager.server.service.DockerDetailsService;
import game.server.manager.server.vo.DockerDetailsVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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

    private DockerClientApi dockerClientApi(String dockerId){
        DockerDetailsVo dockerDetailsVo = dockerDetailsService.info(dockerId);
        return dockerClientApiEndpoint.dockerClientApi(dockerDetailsVo.getDockerHost(), dockerDetailsVo.getDockerSecret());
    }

    @Override
    public Object ping(String dockerId) {
        return dockerClientApi(dockerId).ping();
    }

    @Override
    public R<Info> info(String dockerId) {
        return dockerClientApi(dockerId).info();
    }
}
