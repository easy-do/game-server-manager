package game.server.manager.server.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import game.server.manager.common.result.DataResult;
import game.server.manager.common.result.R;
import game.server.manager.server.service.AppEnvInfoService;
import game.server.manager.common.vo.ScriptEnvListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

/**
 * @author laoyu
 * @version 1.0
 * @date 2022/5/24
 */
@RestController
@RequestMapping("/appEnv")
public class AppEnvController {

    @Autowired
    private AppEnvInfoService appEnvInfoService;

    @SaCheckLogin
    @RequestMapping("/list/{scriptId}")
    public R<List<ScriptEnvListVo>> listByScriptId(@RequestHeader("token") String token, @PathVariable Long scriptId) {
        return DataResult.ok(appEnvInfoService.listByScriptId(scriptId));
    }
}
