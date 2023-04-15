package game.server.manager.server.controller;


import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import game.server.manager.common.result.DataResult;
import game.server.manager.server.dto.InstallApplicationDto;
import game.server.manager.web.base.BaseController;
import game.server.manager.server.qo.ApplicationQo;
import game.server.manager.server.service.ApplicationService;
import game.server.manager.server.vo.ApplicationVo;
import game.server.manager.server.dto.ApplicationDto;
import  game.server.manager.server.entity.Application;
import game.server.manager.log.SaveLog;
import game.server.manager.mybatis.plus.result.MpDataResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import game.server.manager.common.result.R;
import game.server.manager.common.vaild.Insert;
import game.server.manager.common.vaild.Update;

import java.util.List;


/**
 * 应用信息Controller
 * 
 * @author yuzhanfeng
 * @date 2023-03-18 00:48:08
 */
@RestController
@RequestMapping("/application")
public class ApplicationController extends BaseController<ApplicationService,Application,Long, ApplicationQo,ApplicationVo,ApplicationDto> {

    /**
     * 获取所有应用信息列表
     */
    @SaCheckPermission("application:list")
    @RequestMapping("/list")
    @Override
    public R<List<ApplicationVo>> list() {
        return super.list();
    }

    /**
     * 分页条件查询应用信息列表
     */
    @SaCheckPermission("application:page")
    @PostMapping("/page")
    @Override
    public MpDataResult page(@RequestBody ApplicationQo applicationQo) {
        return super.page(applicationQo);
    }


    /**
     * 获取应用信息详细信息
     */
    @Override
    public R<ApplicationVo> info(@PathVariable("id")Long id) {
        return super.info(id);
    }

    /**
     * 新增应用信息
     */
    @SaCheckPermission("application:add")
    @PostMapping("/add")
    @SaveLog(logType = "操作日志", moduleName = "应用信息", description = "添加应用信息", actionType = "添加")
    @Override
    public R<Object> add(@RequestBody @Validated({Insert.class}) ApplicationDto applicationDto) {
        return super.add(applicationDto);
    }

    /**
     * 修改应用信息
     */
    @SaCheckPermission("application:update")
    @PostMapping("/update")
    @SaveLog(logType = "操作日志", moduleName = "应用信息", description = "编辑应用信息: ?1", expressions = {"#p1.id"},actionType = "编辑")
    @Override
    public R<Object> update(@RequestBody @Validated({Update.class}) ApplicationDto applicationDto) {
        return super.update(applicationDto);
    }

    /**
     * 删除应用信息
     */
    @SaCheckPermission("application:remove")
	@GetMapping("/remove/{id}")
    @SaveLog(logType = "操作日志", moduleName = "应用信息", description = "删除应用信息: ?1", expressions = {"#p1"}, actionType = "删除")
    @Override
    public R<Object> remove(@PathVariable("id")Long id) {
        return super.remove(id);
    }

    /**
     * 分页条件查询应用信息列表
     */
    @SaCheckLogin
    @PostMapping("/install")
    public R<Boolean> install(@RequestBody InstallApplicationDto installApplicationDto) {
        return DataResult.ok(baseService.install(installApplicationDto));
    }
}
