package game.server.manager.server.controller;

import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.Container;
import game.server.manager.common.result.R;
import game.server.manager.docker.model.CreateContainerDto;
import game.server.manager.server.service.DockerContainerService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author laoyu
 * @version 1.0
 * @description docker
 * @date 2022/11/19
 */
@RestController
@RequestMapping("/docker/container")
public class DockerContainerController {

    @Resource
    private DockerContainerService dockerContainerService;

    /**
     * 获取容器列表
     *
     * @return game.server.manager.common.result.R<java.util.List<com.github.dockerjava.api.model.Container>>
     * @author laoyu
     * @date 2022/11/19
     */
    @GetMapping("/v1/containerList")
    public R<List<Container>> containerList(@RequestParam("dockerId")String dockerId){
        return dockerContainerService.containerList(dockerId);
    }

    /**
     * 启动容器
     *
     * @param containerId containerId
     * @return game.server.manager.common.result.R<java.lang.Void>
     * @author laoyu
     * @date 2022/11/19
     */
    @GetMapping("/v1/startContainer")
    public R<Void> startContainer(@RequestParam("dockerId")String dockerId,@RequestParam("containerId")String containerId){
        return dockerContainerService.startContainer(dockerId,containerId);
    }


    /**
     * 重启容器
     *
     * @param containerId containerId
     * @return game.server.manager.common.result.R<java.lang.Void>
     * @author laoyu
     * @date 2022/11/19
     */
    @GetMapping("/v1/restartContainer")
    public R<Void> restartContainer(@RequestParam("dockerId")String dockerId,@RequestParam("containerId")String containerId){
        return dockerContainerService.restartContainer(dockerId,containerId);
    }

    /**
     * 停止容器
     *
     * @param containerId containerId
     * @return game.server.manager.common.result.R<java.lang.Void>
     * @author laoyu
     * @date 2022/11/19
     */
    @GetMapping("/v1/stopContainer")
    public R<Void> stopContainer(@RequestParam("dockerId")String dockerId,@RequestParam("containerId")String containerId){
        return dockerContainerService.stopContainer(dockerId,containerId);
    }

    /**
     * 删除容器
     *
     * @param containerId containerId
     * @return game.server.manager.common.result.R<java.lang.Void>
     * @author laoyu
     * @date 2022/11/19
     */
    @DeleteMapping("/v1/removeContainer")
    public R<Void> removeContainer(@RequestParam("dockerId")String dockerId,@RequestParam("containerId")String containerId){
        return dockerContainerService.removeContainer(dockerId,containerId);
    }

    /**
     * 重命名容器
     *
     * @param containerId containerId
     * @param name name
     * @return game.server.manager.common.result.R<java.lang.Void>
     * @author laoyu
     * @date 2022/11/19
     */
    @GetMapping("/v1/renameContainer")
    public R<Void> renameContainer(@RequestParam("dockerId")String dockerId,@RequestParam("containerId")String containerId,@RequestParam("name")String name){
        return dockerContainerService.renameContainer(dockerId,containerId,name);
    }

    /**
     * 创建容器
     *
     * @param createContainerDto createContainerDto
     * @return game.server.manager.common.result.R<com.github.dockerjava.api.command.CreateContainerResponse>
     * @author laoyu
     * @date 2022/11/19
     */
    @PostMapping("/v1/createContainer")
    public R<CreateContainerResponse> createContainer(@RequestParam("dockerId")String dockerId,@RequestBody CreateContainerDto createContainerDto){
        return dockerContainerService.createContainer(dockerId,createContainerDto);
    };
}
