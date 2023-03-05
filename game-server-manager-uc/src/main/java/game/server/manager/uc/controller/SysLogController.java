package game.server.manager.uc.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import game.server.manager.common.constant.SystemConstant;
import game.server.manager.common.result.DataResult;
import game.server.manager.common.result.R;
import game.server.manager.log.SaveLog;
import game.server.manager.log.entity.SysLog;
import game.server.manager.mybatis.plus.qo.MpBaseQo;
import game.server.manager.mybatis.plus.result.MpDataResult;
import game.server.manager.mybatis.plus.result.MpResultUtil;
import game.server.manager.uc.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author laoyu
 * @version 1.0
 * @description 系统日志
 * @date 2022/6/18
 */
@RestController
@RequestMapping("/sysLog")
public class SysLogController {

    @Autowired
    private SysLogService sysLogService;

    @SaCheckRole(SystemConstant.SUPER_ADMIN_ROLE)
    @PostMapping("/page")
    public MpDataResult page(@RequestBody MpBaseQo<SysLog> mpBaseQo) {
        mpBaseQo.initInstance(SysLog.class);
        return MpResultUtil.buildPage(sysLogService.page(mpBaseQo.getPage(),mpBaseQo.getWrapper()));
    }

    /**
     * 删除日志
     */
    @SaCheckRole(SystemConstant.SUPER_ADMIN_ROLE)
    @GetMapping("/clean")
    @SaveLog(logType = "操作日志", moduleName = "系统日志", description = "清空日志", actionType = "清空")
    public R<Object> clean() {
        sysLogService.remove(null);
        return DataResult.ok();
    }

    /**
     * 删除日志
     */
    @SaCheckRole(SystemConstant.SUPER_ADMIN_ROLE)
    @GetMapping("/remove/{id}")
    @SaveLog(logType = "操作日志", moduleName = "系统日志", description = "删除日志:?1" , expressions = "#p1",actionType = "删除")
    public R<Object> delete(@PathVariable("id")Long id){
        sysLogService.removeById(id);
        return DataResult.ok();
    }


}
