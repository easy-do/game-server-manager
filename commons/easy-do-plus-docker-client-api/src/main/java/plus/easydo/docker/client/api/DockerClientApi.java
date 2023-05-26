package plus.easydo.docker.client.api;

import feign.Headers;
import feign.RequestLine;
import plus.easydo.common.result.R;
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
     * @return result.plus.easydo.common.R<java.lang.Object>
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
     * @return result.plus.easydo.common.R<com.github.dockerjava.api.model.Info>
     * @author laoyu
     * @date 2022/11/19
     */
    @Headers({"Content-Type: application/json"})
    @RequestLine("GET /v1/info")
    @GetMapping("/v1/info")
    public R<String> info();

    /**
     * 版本信息
     *
     * @return result.plus.easydo.common.R<com.github.dockerjava.api.model.Version>
     * @author laoyu
     * @date 2022/11/19
     */
    @Headers({"Content-Type: application/json"})
    @RequestLine("GET /v1/version")
    @GetMapping("/v1/version")
    public R<String> version();
}
