package game.server.manager.client.controller;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.github.dockerjava.api.model.Info;
import com.github.dockerjava.api.model.Version;
import game.server.manager.common.result.DataResult;
import game.server.manager.common.result.R;
import game.server.manager.client.service.DockerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author laoyu
 * @version 1.0
 * @description 控制层
 * @date 2022/11/19
 */
@RequestMapping("/v1")
@RestController
public class DockerController {


    @Resource
    private DockerService dockerService;

    /**
     * ping
     *
     * @return game.server.manager.common.result.R<java.lang.Object>
     * @author laoyu
     * @date 2022/11/19
     */
    @GetMapping("/ping")
    public R<Void> ping(){
        try {
            return DataResult.ok(dockerService.ping());
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
    public R<Info> info(){
        try {
            return DataResult.ok(dockerService.info());
        }catch (Exception e){
            return DataResult.fail(ExceptionUtil.getMessage(e));
        }
    }

    /**
     * version
     *
     * @return game.server.manager.common.result.R<java.lang.Object>
     * @author laoyu
     * @date 2022/11/19
     */
    @GetMapping("/version")
    public R<Version> version(){
        try {
            return DataResult.ok(dockerService.version());
        }catch (Exception e){
            return DataResult.fail(ExceptionUtil.getMessage(e));
        }
    }



}
