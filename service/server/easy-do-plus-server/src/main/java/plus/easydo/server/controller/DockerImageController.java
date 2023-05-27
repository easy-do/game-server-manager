package plus.easydo.server.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.github.dockerjava.api.model.Image;
import plus.easydo.common.result.DataResult;
import plus.easydo.common.result.R;
import plus.easydo.server.service.DockerImageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping("/docker/image")
public class DockerImageController {


    @Resource
    private DockerImageService dockerImageService;

    /**
     * 获取镜像列表
     *
     * @param dockerId dockerId
     * @return result.plus.easydo.common.R<java.util.List<com.github.dockerjava.api.model.Image>>
     * @author laoyu
     * @date 2022/11/19
     */
    @SaCheckLogin
    @GetMapping("/v1/list/{dockerId}")
    public R<List<Image>> listImages(@PathVariable("dockerId")String dockerId){
        return DataResult.ok(dockerImageService.listImages(dockerId));

    }

    @GetMapping("/pull/{dockerId}")
    public R<String> pullImage(@PathVariable("dockerId")String dockerId,@RequestParam("repository")String repository) {
        return  DataResult.ok(dockerImageService.pullImage(dockerId,repository));
    }

    /**
     * 删除镜像
     *
     * @param dockerId dockerId
     * @param imageId imageId
     * @return result.plus.easydo.common.R<java.lang.Void>
     * @author laoyu
     * @date 2022/11/19
     */
    @SaCheckLogin
    @GetMapping("/v1/remove/{dockerId}/{imageId}")
    public R<Object> removeImage(@PathVariable("dockerId")String dockerId, @PathVariable("imageId")String imageId){
        return DataResult.ok(dockerImageService.removeImage(dockerId,imageId));
    }
}
