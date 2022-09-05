package game.server.manager.uc.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.tree.Tree;
import game.server.manager.common.constant.SystemConstant;
import game.server.manager.common.dto.ChangeStatusDto;
import game.server.manager.log.SaveLog;
import game.server.manager.auth.AuthorizationUtil;
import game.server.manager.common.vo.SysMenuVo;
import game.server.manager.mybatis.plus.qo.MpBaseQo;
import game.server.manager.uc.dto.AuthRoleMenuDto;
import game.server.manager.uc.dto.SysMenuDto;
import game.server.manager.uc.entity.SysMenu;
import game.server.manager.uc.service.SysMenuService;
import game.server.manager.web.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import game.server.manager.common.result.DataResult;
import game.server.manager.common.result.R;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 菜单信息
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/menu")
public class SysMenuController {

    public static final String MODULE_NAME = "菜单管理";

    public static final String ADD_DESCRIPTION = "添加菜单: ?1";

    public static final String ADD_EXPRESSIONS = "#p1.menuName";

    public static final String EDIT_DESCRIPTION = "编辑菜单: ?1";


    public static final String EDIT_EXPRESSIONS = "#p1.menuId";

    public static final String CHANGE_DESCRIPTION = "修改状态: ?1";

    public static final String CHANGE_EXPRESSIONS = "#p1.id";

    public static final String REMOVE_DESCRIPTION = "删除菜单: ?1";

    public static final String REMOVE_EXPRESSIONS = "#p1";

    public static final String AUTH_DESCRIPTION = "授权角色菜单: ?1";

    public static final String AUTH_EXPRESSIONS = "#p1.roleId";

    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 根据菜单编号获取详细信息
     */
    @SaCheckRole(SystemConstant.SUPER_ADMIN_ROLE)
    @GetMapping(value = "/info/{menuId}")
    public R<SysMenuVo> info(@PathVariable Long menuId) {
        return DataResult.ok(BeanUtil.copyProperties(sysMenuService.getById(menuId),SysMenuVo.class));
    }

    /**
     * 新增菜单
     */
    @SaCheckRole(SystemConstant.SUPER_ADMIN_ROLE)
    @PostMapping("/add")
    @SaveLog(logType = BaseController.LOG_TYPE, moduleName = MODULE_NAME, description = ADD_DESCRIPTION, expressions = ADD_EXPRESSIONS, actionType = BaseController.ADD_ACTION)
    public R<Object> add(@Validated @RequestBody SysMenuDto sysMenuDto) {
        SysMenu menu = BeanUtil.copyProperties(sysMenuDto, SysMenu.class);
        menu.setCreateBy(AuthorizationUtil.getUser().getId());
        menu.setCreateTime(LocalDateTime.now());
        return sysMenuService.save(menu)?DataResult.ok():DataResult.fail();
    }

    /**
     * 修改菜单
     */
    @SaCheckRole(SystemConstant.SUPER_ADMIN_ROLE)
    @PostMapping("/edit")
    @SaveLog(logType = BaseController.LOG_TYPE, moduleName = MODULE_NAME, description = EDIT_DESCRIPTION, expressions = EDIT_EXPRESSIONS, actionType = BaseController.EDIT_ACTION)
    public R<Object> edit(@Validated @RequestBody SysMenuDto sysMenuDto) {
        if (sysMenuDto.getMenuId().equals(sysMenuDto.getParentId())) {
            return DataResult.fail("修改菜单'" + sysMenuDto.getMenuName() + "'失败，上级菜单不能选择自己");
        }
        SysMenu menu = BeanUtil.copyProperties(sysMenuDto, SysMenu.class);
        menu.setUpdateBy(AuthorizationUtil.getUser().getId());
        menu.setUpdateTime(LocalDateTime.now());
        return sysMenuService.updateById(menu)?DataResult.ok():DataResult.fail();
    }

    /**
     * 删除菜单
     */
    @SaCheckRole(SystemConstant.SUPER_ADMIN_ROLE)
    @GetMapping("/delete/{menuId}")
    @SaveLog(logType = BaseController.LOG_TYPE, moduleName = MODULE_NAME, description = REMOVE_DESCRIPTION, expressions = REMOVE_EXPRESSIONS, actionType = BaseController.REMOVE_ACTION)
    public R<Object> remove(@PathVariable("menuId") Long menuId) {
        return sysMenuService.removeById(menuId)?DataResult.ok():DataResult.fail();
    }


    /**
     * 修改菜单状态
     *
     */
    @SaCheckRole(SystemConstant.SUPER_ADMIN_ROLE)
    @PostMapping("/changeStatus")
    @SaveLog(logType = BaseController.LOG_TYPE, moduleName = MODULE_NAME, description = CHANGE_DESCRIPTION, expressions = CHANGE_EXPRESSIONS, actionType = BaseController.EDIT_ACTION)
    public R<Object> changeStatus(@Validated @RequestBody ChangeStatusDto changeStatusDto) {
        return sysMenuService.changeStatus(changeStatusDto)?DataResult.ok():DataResult.fail();
    }


    /**
     * 授权角色菜单
     */
    @SaCheckRole(SystemConstant.SUPER_ADMIN_ROLE)
    @PostMapping("/authRoleMenu")
    @SaveLog(logType = BaseController.LOG_TYPE, moduleName = MODULE_NAME, description = AUTH_DESCRIPTION, expressions = AUTH_EXPRESSIONS, actionType = BaseController.EDIT_ACTION)
    public R<Object> authRoleMenu(@RequestBody AuthRoleMenuDto authRoleMenuDto) {
        return sysMenuService.authRoleMenu(authRoleMenuDto)?DataResult.ok():DataResult.fail();
    }

    /**
     * 获取所有菜单下树列表(带详情)
     */
    @SaCheckRole(SystemConstant.SUPER_ADMIN_ROLE)
    @PostMapping("/list")
    public R<List<Tree<Long>>> list(@RequestBody MpBaseQo mpBaseQo) {
        return DataResult.ok(sysMenuService.treeInfoList(mpBaseQo));
    }

    /**
     * 获取所有菜单下拉树
     */
    @SaCheckRole(SystemConstant.SUPER_ADMIN_ROLE)
    @GetMapping("/treeSelect")
    public R<List<Tree<Long>>> treeSelect() {
        return DataResult.ok(sysMenuService.treeSelect());
    }


    /**
     * 加载对应角色菜单列表树
     */
    @SaCheckRole(SystemConstant.SUPER_ADMIN_ROLE)
    @GetMapping(value = "/roleTreeSelect/{roleId}")
    public R<List<Tree<Long>>> roleTreeSelect(@PathVariable("roleId") Long roleId) {
        return DataResult.ok(sysMenuService.roleTreeSelect(roleId));
    }

    /**
     * 加载对应角色菜单id集合
     */
    @SaCheckRole(SystemConstant.SUPER_ADMIN_ROLE)
    @GetMapping(value = "/roleMenuIds/{roleId}")
    public R<List<Long>> roleMenuIds(@PathVariable("roleId") Long roleId) {
        return DataResult.ok(sysMenuService.roleMenuIds(roleId));
    }

    /**
     * 加载对应用户菜单列表树
     */
    @GetMapping(value = "/userMenu")
    public R<List<Tree<Long>>> userTreeSelect() {
        return DataResult.ok(sysMenuService.userMenu());
    }

}