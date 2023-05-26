package plus.easydo.docker.client.api;

import com.github.dockerjava.api.model.Image;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import plus.easydo.common.result.R;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author laoyu
 * @version 1.0
 * @description docker client api
 * @date 2022/11/19
 */
public interface DockerImageApi {

    /**
     * 获取镜像列表
     *
     * @return result.plus.easydo.common.R<java.util.List<com.github.dockerjava.api.model.Image>>
     * @author laoyu
     * @date 2022/11/19
     */
    @Headers({"Content-Type: application/json"})
    @RequestLine("GET /v1/listImages")
    @GetMapping("/v1/listImages")
    public R<List<Image>> listImages();

    /**
     * 删除镜像
     *
     * @param imageId imageId
     * @return result.plus.easydo.common.R<java.lang.Object>
     * @author laoyu
     * @date 2022/11/19
     */
    @Headers({"Content-Type: application/json"})
    @RequestLine("DELETE /v1/removeImage?imageId={imageId}")
    @DeleteMapping("/v1/removeImage")
    public R<Object> removeImage(@Param(value = "imageId")String imageId);

    @Headers({"Content-Type: application/json"})
    @RequestLine("GET /v1/pullImage?repository={repository}")
    @GetMapping("/v1/pullImage")
    public R<String> pullImage(@Param(value = "repository")String repository);

}
