package game.server.manager.server.controller;


import cn.dev33.satoken.annotation.SaCheckPermission;
import game.server.manager.web.base.BaseController;
import game.server.manager.server.qo.server.ApplicationInstallLogQo;
import game.server.manager.server.service.ApplicationInstallLogService;
import game.server.manager.server.vo.server.ApplicationInstallLogVo;
import game.server.manager.server.dto.ApplicationInstallLogDto;
import  game.server.manager.server.entity.ApplicationInstallLog;
import game.server.manager.log.SaveLog;
import game.server.manager.mybatis.plus.result.MpDataResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import game.server.manager.common.result.R;
import java.util.List;


/**
 * 应用安装日志Controller
 * 
 * @author yuzhanfeng
 * @date 2023-04-08 18:35:18
 */
@RestController
@RequestMapping("/applicationInstallLog")
public class ApplicationInstallLogController extends BaseController<ApplicationInstallLogService,ApplicationInstallLog,String, ApplicationInstallLogQo,ApplicationInstallLogVo,ApplicationInstallLogDto> {

    /**
     * 获取所有应用安装日志列表
     */
    @SaCheckPermission("applicationInstallLog:list")
    @RequestMapping("/list")
    @Override
    public R<List<ApplicationInstallLogVo>> list() {
        return super.list();
    }

    /**
     * 分页条件查询应用安装日志列表
     */
    @SaCheckPermission("applicationInstallLog:page")
    @PostMapping("/page")
    @Override
    public MpDataResult page(@RequestBody ApplicationInstallLogQo applicationInstallLogQo) {
        return super.page(applicationInstallLogQo);
    }


    /**
     * 获取应用安装日志详细信息
     */
    @SaCheckPermission("applicationInstallLog:info")
    @GetMapping("/info/{id}")
    @Override
    public R<ApplicationInstallLogVo> info(@PathVariable("id")String id) {
        return super.info(id);
    }

    /**
     * 删除应用安装日志
     */
    @SaCheckPermission("applicationInstallLog:remove")
	@GetMapping("/remove/{id}")
    @SaveLog(logType = "操作日志", moduleName = "应用安装日志", description = "删除应用安装日志: ?1", expressions = {"#p1"}, actionType = "删除")
    @Override
    public R<Object> remove(@PathVariable("id")String id) {
        return super.remove(id);
    }
}
