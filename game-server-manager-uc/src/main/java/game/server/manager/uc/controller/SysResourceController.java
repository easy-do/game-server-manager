package game.server.manager.uc.controller;


import cn.dev33.satoken.annotation.SaCheckPermission;
import game.server.manager.web.base.BaseController;
import game.server.manager.uc.qo.SysResourceQo;
import game.server.manager.uc.service.SysResourceService;
import game.server.manager.uc.vo.SysResourceVo;
import game.server.manager.uc.dto.SysResourceDto;
import  game.server.manager.uc.entity.SysResource;
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
 * 系统资源Controller
 * 
 * @author yuzhanfeng
 * @date 2022-09-19 22:43:39
 */
@RestController
@RequestMapping("/resource")
public class SysResourceController extends BaseController<SysResourceService,SysResource,Long, SysResourceQo,SysResourceVo,SysResourceDto> {

    /**
     * 获取所有系统资源列表
     */
    @SaCheckPermission("uc:resource:list")
    @RequestMapping("/list")
    public R<List<SysResourceVo>> list() {
        return super.list();
    }

    /**
     * 分页条件查询系统资源列表
     */
    @SaCheckPermission("uc:resource:list")
    @PostMapping("/page")
    public MpDataResult page(@RequestBody SysResourceQo sysResourceQo) {
        return super.page(sysResourceQo);
    }


    /**
     * 获取系统资源详细信息
     */
    @SaCheckPermission("uc:resource:info")
    @GetMapping("/info/{id}")
    public R<SysResourceVo> info(@PathVariable("id")Long id) {
        return super.info(id);
    }

    /**
     * 新增系统资源
     */
    @SaCheckPermission("uc:resource:add")
    @PostMapping("/add")
    @SaveLog(logType = "操作日志", moduleName = "系统资源", description = "添加系统资源", actionType = "添加")
    public R<Object> add(@RequestBody @Validated({Insert.class}) SysResourceDto sysResourceDto) {
        return super.add(sysResourceDto);
    }

    /**
     * 修改系统资源
     */
    @SaCheckPermission("uc:resource:update")
    @PostMapping("/update")
    @SaveLog(logType = "操作日志", moduleName = "系统资源", description = "编辑系统资源: ?1", expressions = {"#p1.id"},actionType = "编辑")
    public R<Object> update(@RequestBody @Validated({Update.class}) SysResourceDto sysResourceDto) {
        return super.update(sysResourceDto);
    }

    /**
     * 删除系统资源
     */
    @SaCheckPermission("uc:resource:remove")
	@GetMapping("/{ids}")
    @SaveLog(logType = "操作日志", moduleName = "系统资源", description = "删除系统资源: ?1", expressions = {"#p1"}, actionType = "删除")
    public R<Object> remove(@PathVariable("id")Long id) {
        return super.remove(id);
    }
}
