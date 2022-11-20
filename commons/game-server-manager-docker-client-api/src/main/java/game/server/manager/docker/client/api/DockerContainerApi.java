package game.server.manager.docker.client.api;

import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.Container;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import game.server.manager.common.result.R;
import game.server.manager.docker.model.CreateContainerDto;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author laoyu
 * @version 1.0
 * @description 容器相关
 * @date 2022/11/19
 */
public interface DockerContainerApi {


    /**
     * 获取容器列表
     *
     * @return game.server.manager.common.result.R<java.util.List<com.github.dockerjava.api.model.Container>>
     * @author laoyu
     * @date 2022/11/19
     */
    @Headers({"Content-Type: application/json"})
    @RequestLine("GET /v1/containerList")
    @GetMapping("/v1/containerList")
    public R<List<Container>> containerList();

    /**
     * 启动容器列
     *
     * @param containerId containerId
     * @return game.server.manager.common.result.R<java.lang.Void>
     * @author laoyu
     * @date 2022/11/19
     */
    @Headers({"Content-Type: application/json"})
    @RequestLine("GET /v1/startContainer?containerId={containerId}")
    @GetMapping("/v1/startContainer")
    public R<Void> startContainer(@Param("containerId")String containerId);


    /**
     * 重启容器
     *
     * @param containerId containerId
     * @return game.server.manager.common.result.R<java.lang.Void>
     * @author laoyu
     * @date 2022/11/19
     */
    @Headers({"Content-Type: application/json"})
    @RequestLine("GET /v1/restartContainer?containerId={containerId}")
    @GetMapping("/v1/restartContainer")
    public R<Void> restartContainer(@Param("containerId")String containerId);

    /**
     * 停止容器
     *
     * @param containerId containerId
     * @return game.server.manager.common.result.R<java.lang.Void>
     * @author laoyu
     * @date 2022/11/19
     */
    @Headers({"Content-Type: application/json"})
    @RequestLine("GET /v1/stopContainer?containerId={containerId}")
    @GetMapping("/v1/stopContainer")
    public R<Void> stopContainer(@Param("containerId")String containerId);

    /**
     * 删除容器
     *
     * @param containerId containerId
     * @return game.server.manager.common.result.R<java.lang.Void>
     * @author laoyu
     * @date 2022/11/19
     */
    @Headers({"Content-Type: application/json"})
    @RequestLine("DELETE /v1/removeContainer?containerId={containerId}")
    @DeleteMapping("/v1/removeContainer")
    public R<Void> removeContainer(@Param("containerId")String containerId);

    /**
     * 重命名容器
     *
     * @param containerId containerId
     * @param name name
     * @return game.server.manager.common.result.R<java.lang.Void>
     * @author laoyu
     * @date 2022/11/19
     */
    @Headers({"Content-Type: application/json"})
    @RequestLine("GET /v1/renameContainer?containerId={containerId}&name={name}")
    @GetMapping("/v1/renameContainer")
    public R<Void> renameContainer(@Param("containerId")String containerId,@Param("name")String name);

    /**
     * 创建容器
     *
     * @param createContainerDto createContainerDto
     * @return game.server.manager.common.result.R<com.github.dockerjava.api.command.CreateContainerResponse>
     * @author laoyu
     * @date 2022/11/19
     */
    @Headers({"Content-Type: application/json"})
    @RequestLine("POST /v1/createContainer")
    @PostMapping("/v1/createContainer")
    public R<CreateContainerResponse> createContainer(@RequestBody CreateContainerDto createContainerDto);

    @Headers({"Content-Type: application/json"})
    @RequestLine("GET /v1/logContainer?containerId={containerId}")
    @GetMapping("/v1/logContainer")
    public  R<String> logContainer(@Param("containerId")String containerId);
}
