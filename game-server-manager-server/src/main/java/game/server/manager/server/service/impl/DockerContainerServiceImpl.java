package game.server.manager.server.service.impl;

import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.Container;
import game.server.manager.docker.client.api.DockerClientApiEndpoint;
import game.server.manager.docker.client.api.DockerContainerApi;
import game.server.manager.common.result.R;
import game.server.manager.docker.model.CreateContainerDto;
import game.server.manager.server.service.DockerContainerService;
import game.server.manager.server.service.DockerDetailsService;
import game.server.manager.server.vo.DockerDetailsVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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

    private DockerContainerApi dockerContainerApi(String dockerId){
        DockerDetailsVo dockerDetailsVo = dockerDetailsService.info(dockerId);
        return dockerClientApiEndpoint.dockerContainerApi(dockerDetailsVo.getDockerHost(), dockerDetailsVo.getDockerSecret());
    }

    @Override
    public R<List<Container>> containerList(String dockerId) {
        return dockerContainerApi(dockerId).containerList();
    }

    @Override
    public R<Void> startContainer(String dockerId, String containerId) {
        return dockerContainerApi(dockerId).startContainer(containerId);
    }

    @Override
    public R<Void> restartContainer(String dockerId, String containerId) {
        return dockerContainerApi(dockerId).restartContainer(containerId);
    }

    @Override
    public R<Void> stopContainer(String dockerId, String containerId) {
        return dockerContainerApi(dockerId).stopContainer(containerId);
    }

    @Override
    public R<Void> removeContainer(String dockerId, String containerId) {
        return dockerContainerApi(dockerId).removeContainer(containerId);
    }

    @Override
    public R<Void> renameContainer(String dockerId, String containerId, String name) {
        return dockerContainerApi(dockerId).renameContainer(containerId,name);
    }

    @Override
    public R<CreateContainerResponse> createContainer(String dockerId, CreateContainerDto createContainerDto) {
        return dockerContainerApi(dockerId).createContainer(createContainerDto);
    }

    @Override
    public R<String> logContainer(String dockerId, String containerId) {
        return dockerContainerApi(dockerId).logContainer(containerId);
    }
}
