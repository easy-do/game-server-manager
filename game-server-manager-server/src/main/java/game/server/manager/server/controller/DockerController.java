package game.server.manager.server.controller;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.github.dockerjava.api.model.Info;
import game.server.manager.common.result.DataResult;
import game.server.manager.common.result.R;
import game.server.manager.server.service.DockerBasicService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author laoyu
 * @version 1.0
 * @description docker
 * @date 2022/11/19
 */
@RestController
@RequestMapping("/docker")
public class DockerController {

    @Resource
    private DockerBasicService DockerBasicService;

    /**
     * ping
     *
     * @return game.server.manager.common.result.R<java.lang.Object>
     * @author laoyu
     * @date 2022/11/19
     */
    @GetMapping("/ping")
    public R<Object> ping(@RequestParam("dockerId") String dockerId){
        try {
            return DataResult.ok(DockerBasicService.ping(dockerId));
        }catch (Exception e){
            return DataResult.fail(ExceptionUtil.getMessage(e));
        }
    }

    /**
     * Info
     *
     * @return game.server.manager.common.result.R<java.lang.Object>
     * @author laoyu
     * @date 2022/11/19
     */
    @GetMapping("/info")
    public R<String> info(@RequestParam("dockerId") String dockerId){
        try {
            return DockerBasicService.info(dockerId);
        }catch (Exception e){
            return DataResult.fail(ExceptionUtil.getMessage(e));
        }
    }
}
