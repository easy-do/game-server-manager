package game.server.manager.client.controller;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.Container;
import game.server.manager.common.result.DataResult;
import game.server.manager.common.result.R;
import game.server.manager.docker.client.api.DockerContainerApi;
import game.server.manager.client.service.DockerContainerService;
import game.server.manager.client.service.DockerService;
import game.server.manager.docker.model.CreateContainerDto;
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
 * @description 容器相关
 * @date 2022/11/19
 */
@RequestMapping("/v1")
@RestController
public class DockerContainerController implements DockerContainerApi {

    @Resource
    private DockerService dockerService;

    @Resource
    private DockerContainerService dockerContainerService;

    /**
     * 获取容器列表
     *
     * @return game.server.manager.common.result.R<java.util.List<com.github.dockerjava.api.model.Container>>
     * @author laoyu
     * @date 2022/11/19
     */
    @GetMapping("/containerList")
    public R<List<Container>> containerList(){
        try {
            return DataResult.ok(dockerContainerService.containerList());
        }catch (Exception e){
            return DataResult.fail(ExceptionUtil.getMessage(e));
        }
    }

    /**
     * 启动容器列
     *
     * @param containerId containerId
     * @return game.server.manager.common.result.R<java.lang.Void>
     * @author laoyu
     * @date 2022/11/19
     */
    @GetMapping("/startContainer")
    public R<Void> startContainer(@RequestParam("containerId")String containerId){
        try {
            return DataResult.ok(dockerContainerService.startContainer(containerId));
        }catch (Exception e){
            return DataResult.fail(ExceptionUtil.getMessage(e));
        }
    }


    /**
     * 重启容器
     *
     * @param containerId containerId
     * @return game.server.manager.common.result.R<java.lang.Void>
     * @author laoyu
     * @date 2022/11/19
     */
    @GetMapping("/restartContainer")
    public R<Void> restartContainer(@RequestParam("containerId")String containerId){
        try {
            return DataResult.ok(dockerContainerService.restartContainer(containerId));
        }catch (Exception e){
            return DataResult.fail(ExceptionUtil.getMessage(e));
        }
    }

    /**
     * 停止容器
     *
     * @param containerId containerId
     * @return game.server.manager.common.result.R<java.lang.Void>
     * @author laoyu
     * @date 2022/11/19
     */
    @GetMapping("/stopContainer")
    public R<Void> stopContainer(@RequestParam("containerId")String containerId){
        try {
            return DataResult.ok(dockerContainerService.stopContainer(containerId));
        }catch (Exception e){
            return DataResult.fail(ExceptionUtil.getMessage(e));
        }
    }

    /**
     * 删除容器
     *
     * @param containerId containerId
     * @return game.server.manager.common.result.R<java.lang.Void>
     * @author laoyu
     * @date 2022/11/19
     */
    @DeleteMapping("/removeContainer")
    public R<Void> removeContainer(@RequestParam("containerId")String containerId){
        try {
            return DataResult.ok(dockerContainerService.removeContainer(containerId));
        }catch (Exception e){
            return DataResult.fail(ExceptionUtil.getMessage(e));
        }
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
    @GetMapping("/renameContainer")
    public R<Void> renameContainer(@RequestParam("containerId")String containerId,@RequestParam("name")String name){
        try {
            return DataResult.ok(dockerContainerService.renameContainer(containerId,name));
        }catch (Exception e){
            return DataResult.fail(ExceptionUtil.getMessage(e));
        }
    }

    /**
     * 创建容器
     *
     * @param createContainerDto createContainerDto
     * @return game.server.manager.common.result.R<com.github.dockerjava.api.command.CreateContainerResponse>
     * @author laoyu
     * @date 2022/11/19
     */
    @PostMapping("/createContainer")
    public R<CreateContainerResponse> createContainer(@RequestBody CreateContainerDto createContainerDto){
        try {
            return DataResult.ok(dockerContainerService.createContainer(createContainerDto));
        }catch (Exception e){
            return DataResult.fail(ExceptionUtil.getMessage(e));
        }
    }

    /**
     * 查看容器日志
     *
     * @param containerId containerId
     * @return game.server.manager.common.result.R<com.github.dockerjava.core.InvocationBuilder.AsyncResultCallback>
     * @author laoyu
     * @date 2022/11/20
     */
    @GetMapping("/logContainer")
    public R<String> logContainer(@RequestParam("containerId")String containerId) {
        try {
            return DataResult.ok(dockerContainerService.logContainer(containerId));
        }catch (Exception e){
            return DataResult.fail(ExceptionUtil.getMessage(e));
        }
    }
}
