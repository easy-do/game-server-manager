package game.server.manager.uc.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import game.server.manager.common.constant.SystemConstant;
import game.server.manager.common.dto.ChangeStatusDto;
import game.server.manager.uc.dto.SysRoleDto;
import game.server.manager.uc.entity.SysRole;
import game.server.manager.log.SaveLog;
import game.server.manager.mybatis.plus.qo.MpBaseQo;
import game.server.manager.mybatis.plus.result.MpDataResult;
import game.server.manager.mybatis.plus.result.MpResultUtil;
import game.server.manager.uc.service.SysRoleService;
import game.server.manager.common.vo.SysRoleVo;
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
import java.util.Arrays;


/**
 * 角色信息
 *
 * @author laoyu
 * @date 2022/7/18
 */
@RestController
@RequestMapping("/role")
public class SysRoleController {

    public static final String MODULE_NAME = "角色管理";

    public static final String ADD_DESCRIPTION = "添加角色: ?1";

    public static final String ADD_EXPRESSIONS = "#p1.roleName";

    public static final String EDIT_DESCRIPTION = "编辑角色: ?1";

    public static final String EDIT_EXPRESSIONS = "#p1.roleId";

    public static final String CHANGE_DESCRIPTION = "修改状态: ?1";

    public static final String CHANGE_EXPRESSIONS = "#p1.id";

    public static final String REMOVE_DESCRIPTION = "删除角色: ?1";

    public static final String REMOVE_EXPRESSIONS = "#p1";

    @Autowired
    private SysRoleService sysRoleService;


    /**
     * 获取角色选择框列表
     */
    @SaCheckRole(SystemConstant.SUPER_ADMIN_ROLE)
    @GetMapping("/list")
    public R<Object> list() {
        MpBaseQo<SysRole> mpBaseQo = new MpBaseQo<>();
        mpBaseQo.setColumns(Arrays.asList("roleId","roleName"));
        LambdaQueryWrapper<SysRole> wrapper = mpBaseQo.buildSearchWrapper();
        wrapper.eq(SysRole::getStatus,0);
        return DataResult.ok(sysRoleService.list(wrapper));
    }


    @SaCheckRole(SystemConstant.SUPER_ADMIN_ROLE)
    @PostMapping("/page")
    public MpDataResult page(@RequestBody MpBaseQo<SysRole> mpBaseQo) {
        return MpResultUtil.buildPage(sysRoleService.page(mpBaseQo.startPage(),mpBaseQo.buildSearchWrapper()),SysRoleVo.class);
    }


    /**
     * 根据角色编号获取详细信息
     */
    @SaCheckRole(SystemConstant.SUPER_ADMIN_ROLE)
    @GetMapping(value = "/info/{roleId}")
    public R<SysRoleVo> info(@PathVariable("roleId") Long roleId) {
        return DataResult.ok(BeanUtil.copyProperties(sysRoleService.getById(roleId),SysRoleVo.class));
    }

    /**
     * 新增角色
     */
    @SaCheckRole(SystemConstant.SUPER_ADMIN_ROLE)
    @PostMapping("/add")
    @SaveLog(logType = BaseController.LOG_TYPE, moduleName = MODULE_NAME, description = ADD_DESCRIPTION, expressions = ADD_EXPRESSIONS, actionType = BaseController.ADD_ACTION)
    public R<Object> add(@Validated @RequestBody SysRoleDto dto) {
        if (sysRoleService.checkRoleNameUnique(dto)) {
            return DataResult.fail("失败，角色名称或权限已存在");
        }
        SysRole entity = BeanUtil.copyProperties(dto, SysRole.class);
        entity.setCreateTime(LocalDateTime.now());
        return sysRoleService.save(entity)?DataResult.ok():DataResult.fail();

    }

    /**
     * 修改保存角色
     */
    @SaCheckRole(SystemConstant.SUPER_ADMIN_ROLE)
    @PostMapping("/edit")
    @SaveLog(logType = BaseController.LOG_TYPE, moduleName = MODULE_NAME, description = EDIT_DESCRIPTION, expressions =  EDIT_EXPRESSIONS, actionType = BaseController.ADD_ACTION)
    public R<Object> edit(@Validated @RequestBody SysRoleDto dto) {
        if (sysRoleService.checkRoleNameUnique(dto)) {
            return DataResult.fail("失败，角色名称或权限已存在");
        }
        SysRole entity = BeanUtil.copyProperties(dto, SysRole.class);
        entity.setUpdateTime(LocalDateTime.now());
        return sysRoleService.updateById(entity)?DataResult.ok():DataResult.fail();
    }

    /**
     * 状态修改
     */
    @SaCheckRole(SystemConstant.SUPER_ADMIN_ROLE)
    @PostMapping("/changeStatus")
    @SaveLog(logType = BaseController.LOG_TYPE, moduleName = MODULE_NAME, description = CHANGE_DESCRIPTION, expressions = CHANGE_EXPRESSIONS, actionType = BaseController.EDIT_ACTION)
    public R<Object> changeStatus(@RequestBody ChangeStatusDto changeStatusDto) {
        return sysRoleService.updateRoleStatus(changeStatusDto)?DataResult.ok():DataResult.fail();
    }

    /**
     * 删除角色
     */
    @SaCheckRole(SystemConstant.SUPER_ADMIN_ROLE)
    @GetMapping("/delete/{roleId}")
    @SaveLog(logType = BaseController.LOG_TYPE, moduleName = MODULE_NAME, description = REMOVE_DESCRIPTION, expressions = REMOVE_EXPRESSIONS, actionType = BaseController.REMOVE_ACTION)
    public R<Object> remove(@PathVariable Long roleId) {
        return sysRoleService.removeById(roleId)?DataResult.ok():DataResult.fail();
    }


//    /**
//     * 设置所有用户的角色为默认角色
//     *
//     * @return plus.easydo.core.result.R<java.lang.Object>
//     * @author laoyu
//     * @date 2022/7/21
//     */
//    @SaCheckRole(SystemConstant.SUPER_ADMIN_ROLE)
//    @GetMapping("/setAllUserRoleDefault")
//    public R<Object> syncAllUserRoleDefault() {
//        return sysRoleService.syncAllUserRoleDefault()?DataResult.ok():DataResult.fail();
//    }

}