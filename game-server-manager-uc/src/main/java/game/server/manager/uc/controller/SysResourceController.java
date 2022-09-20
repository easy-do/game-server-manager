package game.server.manager.uc.controller;


import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaCheckRole;
import cn.hutool.core.lang.tree.Tree;
import game.server.manager.common.constant.SystemConstant;
import game.server.manager.common.dto.ChangeStatusDto;
import game.server.manager.common.result.DataResult;
import game.server.manager.uc.dto.AuthRoleMenuDto;
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
import org.springframework.web.bind.annotation.RequestParam;
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
	@GetMapping("/{id}")
    @SaveLog(logType = "操作日志", moduleName = "系统资源", description = "删除系统资源: ?1", expressions = {"#p1"}, actionType = "删除")
    public R<Object> remove(@PathVariable("id")Long id) {
        return super.remove(id);
    }


    /**
     * 修改资源状态
     *
     */
    @SaCheckRole(SystemConstant.SUPER_ADMIN_ROLE)
    @PostMapping("/changeStatus")
    @SaveLog(logType = BaseController.LOG_TYPE, moduleName = "系统资源", description = "修改资源状态", actionType = BaseController.EDIT_ACTION)
    public R<Object> changeStatus(@Validated @RequestBody ChangeStatusDto changeStatusDto) {
        return baseService.changeStatus(changeStatusDto)?DataResult.ok():DataResult.fail();
    }


    /**
     * 授权角色资源
     */
    @SaCheckRole(SystemConstant.SUPER_ADMIN_ROLE)
    @PostMapping("/authRoleResource")
    @SaveLog(logType = BaseController.LOG_TYPE, moduleName = "系统资源", description = "授权角色资源", actionType = BaseController.EDIT_ACTION)
    public R<Object> authRoleResource(@RequestBody AuthRoleMenuDto authRoleMenuDto) {
        return baseService.authRoleResource(authRoleMenuDto)?DataResult.ok():DataResult.fail();
    }

    /**
     * 获取所有资源下拉树(带详情)
     */
    @SaCheckLogin
    @GetMapping("/resourceInfoTree")
    public R<List<Tree<Long>>> resourceInfoTree() {
        return DataResult.ok(baseService.resourceInfoTree());
    }

    /**
     * 获取所有资源下拉树
     */
    @SaCheckLogin
    @GetMapping("/resourceTreeSelect")
    public R<List<Tree<Long>>> resourceTreeSelect() {
        return DataResult.ok(baseService.resourceTreeSelect());
    }


    /**
     * 根据类型获取资源列表(带详情)
     */
    @SaCheckLogin
    @GetMapping("/resourceInfoTreeByType")
    public R<List<Tree<Long>>> resourceInfoTreeByType(@RequestParam String type) {
        return DataResult.ok(baseService.resourceInfoTreeByType(type));
    }


    /**
     * 根据类型获取资源列表
     */
    @SaCheckRole(SystemConstant.SUPER_ADMIN_ROLE)
    @GetMapping("/resourceTreeByType")
    public R<List<Tree<Long>>> resourceTreeByType(@RequestParam String type) {
        return DataResult.ok(baseService.resourceTreeByType(type));
    }

    /**
     * 加载对应角色资源列表
     */
    @SaCheckRole(SystemConstant.SUPER_ADMIN_ROLE)
    @GetMapping(value = "/roleResource/{roleId}")
    public R<List<Tree<Long>>> roleResource(@PathVariable("roleId") Long roleId) {
        return DataResult.ok(baseService.roleResource(roleId));
    }

    /**
     * 加载对应角色资源id集合
     */
    @SaCheckRole(SystemConstant.SUPER_ADMIN_ROLE)
    @GetMapping(value = "/roleResourceIds/{roleId}")
    public R<List<Long>> roleResourceIds(@PathVariable("roleId") Long roleId) {
        return DataResult.ok(baseService.roleResourceIds(roleId));
    }

    /**
     * 加载 用户/游客 资源列表
     */
    @GetMapping(value = "/userResource")
    public R<List<Tree<Long>>> userResource() {
        return DataResult.ok(baseService.userResource());
    }


}
