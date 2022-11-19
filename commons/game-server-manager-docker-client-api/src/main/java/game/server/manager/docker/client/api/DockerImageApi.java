package game.server.manager.docker.client.api;

import com.github.dockerjava.api.model.Image;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import game.server.manager.common.result.R;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

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
     * @return game.server.manager.common.result.R<java.util.List<com.github.dockerjava.api.model.Image>>
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
     * @return game.server.manager.common.result.R<java.lang.Void>
     * @author laoyu
     * @date 2022/11/19
     */
    @Headers({"Content-Type: application/json"})
    @RequestLine("DELETE /v1/removeImage?imageId={imageId}")
    @DeleteMapping("/v1/removeImage")
    public R<Void> removeImage(@Param(value = "imageId")String imageId);

}
