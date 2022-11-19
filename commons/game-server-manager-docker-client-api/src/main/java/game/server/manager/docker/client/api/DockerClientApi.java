package game.server.manager.docker.client.api;

import com.github.dockerjava.api.model.Info;
import com.github.dockerjava.api.model.Version;
import feign.Headers;
import feign.RequestLine;
import game.server.manager.common.result.R;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * @author laoyu
 * @version 1.0
 * @description docker client api
 * @date 2022/11/19
 */

public interface DockerClientApi {

    /**
     * ping
     *
     * @return game.server.manager.common.result.R<java.lang.Object>
     * @author laoyu
     * @date 2022/11/19
     */
    @Headers({"Content-Type: application/json"})
    @RequestLine("GET /v1/ping")
    @GetMapping("/v1/ping")
    public R<Void> ping();


    /**
     * 详细信息
     *
     * @return game.server.manager.common.result.R<com.github.dockerjava.api.model.Info>
     * @author laoyu
     * @date 2022/11/19
     */
    @Headers({"Content-Type: application/json"})
    @RequestLine("GET /v1/info")
    @GetMapping("/v1/info")
    public R<Info> info();

    /**
     * 版本信息
     *
     * @return game.server.manager.common.result.R<com.github.dockerjava.api.model.Version>
     * @author laoyu
     * @date 2022/11/19
     */
    @Headers({"Content-Type: application/json"})
    @RequestLine("GET /v1/version")
    @GetMapping("/v1/version")
    public R<Version> version();
}
