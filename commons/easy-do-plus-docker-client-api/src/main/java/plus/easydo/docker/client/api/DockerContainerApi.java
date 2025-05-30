package plus.easydo.docker.client.api;

import com.github.dockerjava.api.model.Container;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import plus.easydo.common.result.R;
import plus.easydo.docker.model.CreateContainerDto;
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
     * @return result.plus.easydo.common.R<java.util.List<com.github.dockerjava.api.model.Container>>
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
     * @return result.plus.easydo.common.R<java.lang.Void>
     * @author laoyu
     * @date 2022/11/19
     */
    @Headers({"Content-Type: application/json"})
    @RequestLine("GET /v1/startContainer?containerId={containerId}")
    @GetMapping("/v1/startContainer")
    public R<Object> startContainer(@Param("containerId")String containerId);


    /**
     * 重启容器
     *
     * @param containerId containerId
     * @return result.plus.easydo.common.R<java.lang.Object>
     * @author laoyu
     * @date 2022/11/19
     */
    @Headers({"Content-Type: application/json"})
    @RequestLine("GET /v1/restartContainer?containerId={containerId}")
    @GetMapping("/v1/restartContainer")
    public R<Object> restartContainer(@Param("containerId")String containerId);

    /**
     * 停止容器
     *
     * @param containerId containerId
     * @return result.plus.easydo.common.R<java.lang.Object>
     * @author laoyu
     * @date 2022/11/19
     */
    @Headers({"Content-Type: application/json"})
    @RequestLine("GET /v1/stopContainer?containerId={containerId}")
    @GetMapping("/v1/stopContainer")
    public R<Object> stopContainer(@Param("containerId")String containerId);

    /**
     * 删除容器
     *
     * @param containerId containerId
     * @return result.plus.easydo.common.R<java.lang.Object>
     * @author laoyu
     * @date 2022/11/19
     */
    @Headers({"Content-Type: application/json"})
    @RequestLine("DELETE /v1/removeContainer?containerId={containerId}")
    @DeleteMapping("/v1/removeContainer")
    public R<Object> removeContainer(@Param("containerId")String containerId);

    /**
     * 重命名容器
     *
     * @param containerId containerId
     * @param name name
     * @return result.plus.easydo.common.R<java.lang.Object>
     * @author laoyu
     * @date 2022/11/19
     */
    @Headers({"Content-Type: application/json"})
    @RequestLine("GET /v1/renameContainer?containerId={containerId}&name={name}")
    @GetMapping("/v1/renameContainer")
    public R<Object> renameContainer(@Param("containerId")String containerId,@Param("name")String name);

    /**
     * 创建容器
     *
     * @param createContainerDto createContainerDto
     * @return result.plus.easydo.common.R<com.github.dockerjava.api.command.CreateContainerResponse>
     * @author laoyu
     * @date 2022/11/19
     */
    @Headers({"Content-Type: application/json"})
    @RequestLine("POST /v1/createContainer")
    @PostMapping("/v1/createContainer")
    public R<Object> createContainer(@RequestBody CreateContainerDto createContainerDto);

    @Headers({"Content-Type: application/json"})
    @RequestLine("GET /v1/logContainer?containerId={containerId}")
    @GetMapping("/v1/logContainer")
    public  R<String> logContainer(@Param("containerId")String containerId);
}
