package plus.easydo.server.controller;

import com.github.dockerjava.api.model.Container;
import plus.easydo.common.result.DataResult;
import plus.easydo.common.result.R;
import plus.easydo.docker.model.CreateContainerDto;
import plus.easydo.server.service.DockerContainerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
     * @return result.plus.easydo.common.R<java.util.List<com.github.dockerjava.api.model.Container>>
     * @author laoyu
     * @date 2022/11/19
     */
    @GetMapping("/v1/list/{dockerId}")
    public R<List<Container>> containerList(@PathVariable("dockerId")String dockerId){
        return DataResult.ok(dockerContainerService.containerList(dockerId));
    }

    /**
     * 启动容器
     *
     * @param containerId containerId
     * @return result.plus.easydo.common.R<java.lang.Objct>
     * @author laoyu
     * @date 2022/11/19
     */
    @GetMapping("/v1/start/{dockerId}/{containerId}")
    public R<Object> startContainer(@PathVariable("dockerId")Long dockerId,@PathVariable("containerId")String containerId){
        return DataResult.ok(dockerContainerService.startContainer(dockerId,containerId));
    }


    /**
     * 重启容器
     *
     * @param containerId containerId
     * @return result.plus.easydo.common.R<java.lang.Object>
     * @author laoyu
     * @date 2022/11/19
     */
    @GetMapping("/v1/restart/{dockerId}/{containerId}")
    public R<Object> restartContainer(@PathVariable("dockerId")String dockerId,@PathVariable("containerId")String containerId){
        return DataResult.ok(dockerContainerService.restartContainer(dockerId,containerId));
    }

    /**
     * 停止容器
     *
     * @param containerId containerId
     * @return result.plus.easydo.common.R<java.lang.Object>
     * @author laoyu
     * @date 2022/11/19
     */
    @GetMapping("/v1/stop/{dockerId}/{containerId}")
    public R<Object> stopContainer(@PathVariable("dockerId")String dockerId,@PathVariable("containerId")String containerId){
        return DataResult.ok(dockerContainerService.stopContainer(dockerId,containerId));
    }

    /**
     * 删除容器
     *
     * @param containerId containerId
     * @return result.plus.easydo.common.R<java.lang.Object>
     * @author laoyu
     * @date 2022/11/19
     */
    @GetMapping("/v1/remove/{dockerId}/{containerId}")
    public R<Object> removeContainer(@PathVariable("dockerId")String dockerId,@PathVariable("containerId")String containerId){
        return DataResult.ok(dockerContainerService.removeContainer(dockerId,containerId));
    }

    /**
     * 重命名容器
     *
     * @param containerId containerId
     * @param name name
     * @return result.plus.easydo.common.R<java.lang.Object>
     * @author laoyu
     * @date 2022/11/19
     */
    @GetMapping("/v1/rename/{dockerId}/{containerId}")
    public R<Object> renameContainer(@PathVariable("dockerId")String dockerId,@PathVariable("containerId")String containerId,@RequestParam("name")String name){
        return DataResult.ok(dockerContainerService.renameContainer(dockerId,containerId,name));
    }

    /**
     * 查看容器日志
     *
     * @param containerId containerId
     * @return result.plus.easydo.common.R<java.lang.Object>
     * @author laoyu
     * @date 2022/11/19
     */
    @GetMapping("/v1/log/{dockerId}/{containerId}")
    public R<String> logContainer(@PathVariable("dockerId")String dockerId, @PathVariable("containerId")String containerId){
        return DataResult.ok(dockerContainerService.logContainer(dockerId,containerId));
    }

    /**
     * 创建容器
     *
     * @param createContainerDto createContainerDto
     * @return result.plus.easydo.common.R<com.github.dockerjava.api.command.CreateContainerResponse>
     * @author laoyu
     * @date 2022/11/19
     */
    @PostMapping("/v1/createContainer")
    public R<Object> createContainer(@RequestParam("dockerId") Long dockerId, @RequestBody CreateContainerDto createContainerDto){
        return DataResult.ok(dockerContainerService.createContainer(dockerId,createContainerDto));
    }
}
