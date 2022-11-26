package game.server.manager.client.controller;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.github.dockerjava.api.model.Image;
import game.server.manager.common.result.DataResult;
import game.server.manager.common.result.R;
import game.server.manager.client.service.DockerImageService;
import game.server.manager.docker.client.api.DockerImageApi;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author laoyu
 * @version 1.0
 * @description 镜像相关
 * @date 2022/11/19
 */
@RequestMapping("/v1")
@RestController
public class DockerImageController implements DockerImageApi {

    @Resource
    private DockerImageService dockerImageService;

    /**
     * 获取镜像列表
     *
     * @return game.server.manager.common.result.R<java.util.List<com.github.dockerjava.api.model.Image>>
     * @author laoyu
     * @date 2022/11/19
     */
    @Override
    @GetMapping("/listImages")
    public R<List<Image>> listImages(){
        try {
            return DataResult.ok(dockerImageService.listImages());
        }catch (Exception e){
            return DataResult.fail(ExceptionUtil.getMessage(e));
        }
    }

    /**
     * 删除镜像
     *
     * @param imageId imageId
     * @return game.server.manager.common.result.R<java.lang.Object>
     * @author laoyu
     * @date 2022/11/19
     */
    @Override
    @DeleteMapping("/removeImage")
    public R<Object> removeImage(@RequestParam("imageId")String imageId){
        try {
            return DataResult.ok(dockerImageService.removeImage(imageId));
        }catch (Exception e){
            return DataResult.fail(ExceptionUtil.getMessage(e));
        }
    }

    @Override
    @GetMapping("/pullImage")
    public R<String> pullImage(@RequestParam("repository")String repository){
        try {
            return DataResult.ok(dockerImageService.pullImage(dockerImageService.pullImage(repository)));
        }catch (Exception e){
            return DataResult.fail(ExceptionUtil.getMessage(e));
        }
    }
}
