package plus.easydo.server.controller;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.json.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import plus.easydo.common.result.DataResult;
import plus.easydo.common.result.R;
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
    private plus.easydo.server.service.DockerBasicService DockerBasicService;

    /**
     * ping
     *
     * @return result.plus.easydo.common.R<java.lang.Object>
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
     * @return result.plus.easydo.common.R<java.lang.Object>
     * @author laoyu
     * @date 2022/11/19
     */
    @GetMapping("/info")
    public R<JSON> info(@RequestParam("dockerId") String dockerId) throws JsonProcessingException {
            return DataResult.ok(DockerBasicService.info(dockerId));
    }
}
