package plus.easydo.uc.controller;



import cn.dev33.satoken.annotation.SaCheckRole;
import cn.hutool.core.lang.tree.Tree;
import cn.zhxu.bs.BeanSearcher;
import lombok.RequiredArgsConstructor;
import plus.easydo.common.constant.SystemConstant;
import plus.easydo.common.dto.ChangeStatusDto;
import plus.easydo.common.result.DataResult;
import plus.easydo.uc.dto.AuthRoleMenuDto;
import plus.easydo.uc.vo.UserResourceVo;
import plus.easydo.common.result.MpDataResult;
import plus.easydo.dao.result.MpResultUtil;
import plus.easydo.uc.entity.SysResource;
import plus.easydo.uc.mapstruct.SysResourceMapstruct;
import plus.easydo.uc.qo.SysResourceQo;
import plus.easydo.uc.service.SysResourceService;
import plus.easydo.web.base.BaseController;
import plus.easydo.uc.vo.SysResourceVo;
import plus.easydo.uc.dto.SysResourceDto;
import plus.easydo.log.SaveLog;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import plus.easydo.common.result.R;
import plus.easydo.common.vaild.Insert;
import plus.easydo.common.vaild.Update;

import java.util.List;


/**
 * 系统资源Controller
 * 
 * @author yuzhanfeng
 * @date 2022-09-19 22:43:39
 */
@RestController
@RequestMapping("/resource")
@RequiredArgsConstructor
public class SysResourceController  {

    private final SysResourceService baseService;

    private final BeanSearcher beanSearcher;

    /**
     * 获取所有系统资源列表
     */
    @SaCheckRole(SystemConstant.SUPER_ADMIN_ROLE)
    @RequestMapping("/list")
    public R<List<SysResourceVo>> list() {
        List<SysResource> result = beanSearcher.searchList(SysResource.class);
        return DataResult.ok(SysResourceMapstruct.INSTANCE.entityToVo(result));
    }

    /**
     * 分页条件查询系统资源列表
     */
    @SaCheckRole(SystemConstant.SUPER_ADMIN_ROLE)
    @PostMapping("/page")
    public MpDataResult page(@RequestBody SysResourceQo sysResourceQo) {
        return MpResultUtil.buildPage(baseService.page(sysResourceQo));
    }


    /**
     * 获取系统资源详细信息
     */
    @SaCheckRole(SystemConstant.SUPER_ADMIN_ROLE)
    @GetMapping("/info/{id}")
    public R<SysResourceVo> info(@PathVariable("id")Long id) {
        return DataResult.ok(baseService.info(id));
    }

    /**
     * 新增系统资源
     */
    @SaCheckRole(SystemConstant.SUPER_ADMIN_ROLE)
    @PostMapping("/add")
    @SaveLog(logType = "操作日志", moduleName = "系统资源", description = "添加系统资源", actionType = "添加")
    public R<Object> add(@RequestBody @Validated({Insert.class}) SysResourceDto sysResourceDto) {
        return DataResult.ok(baseService.add(sysResourceDto));
    }

    /**
     * 修改系统资源
     */
    @SaCheckRole(SystemConstant.SUPER_ADMIN_ROLE)
    @PostMapping("/update")
    @SaveLog(logType = "操作日志", moduleName = "系统资源", description = "编辑系统资源: ?1", expressions = {"#p1.id"},actionType = "编辑")
    public R<Object> update(@RequestBody @Validated({Update.class}) SysResourceDto sysResourceDto) {

        return DataResult.ok(baseService.updateById(SysResourceMapstruct.INSTANCE.dtoToEntity(sysResourceDto)));
    }

    /**
     * 删除系统资源
     */
    @SaCheckRole(SystemConstant.SUPER_ADMIN_ROLE)
    @GetMapping("/remove/{id}")
    @SaveLog(logType = "操作日志", moduleName = "系统资源", description = "删除系统资源: ?1", expressions = {"#p1"}, actionType = "删除")
    public R<Object> remove(@PathVariable("id")Long id) {
        return DataResult.ok(baseService.removeById(id));
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
     * 树列表
     */
    @SaCheckRole(SystemConstant.SUPER_ADMIN_ROLE)
    @PostMapping("/treeList")
    public R<List<SysResourceVo>> treeList(@RequestBody SysResourceQo sysResourceQo) {
        return DataResult.ok(baseService.treeList(sysResourceQo));
    }

    /**
     * 获取所有资源下拉树(带详情)
     */
    @SaCheckRole(SystemConstant.SUPER_ADMIN_ROLE)
    @GetMapping("/resourceInfoTree")
    public R<List<Tree<Long>>> resourceInfoTree() {
        return DataResult.ok(baseService.resourceInfoTree());
    }

    /**
     * 获取所有资源下拉树
     */
    @SaCheckRole(SystemConstant.SUPER_ADMIN_ROLE)
    @GetMapping("/resourceTree")
    public R<List<Tree<Long>>> resourceTree() {
        return DataResult.ok(baseService.resourceTree());
    }


    /**
     * 根据类型获取资源列表(带详情)
     */
    @SaCheckRole(SystemConstant.SUPER_ADMIN_ROLE)
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
    public R<List<UserResourceVo>> userResource() {
        return DataResult.ok(baseService.userResource());
    }


}
