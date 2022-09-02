package game.server.manager.uc.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import game.server.manager.auth.AuthorizationUtil;
import game.server.manager.common.dto.ChangeStatusDto;
import game.server.manager.log.SaveLog;
import game.server.manager.mybatis.plus.qo.MpBaseQo;
import game.server.manager.mybatis.plus.result.MpDataResult;
import game.server.manager.mybatis.plus.result.MpResultUtil;
import game.server.manager.common.vo.SysRoleVo;
import game.server.manager.common.vo.UserManagerVo;
import game.server.manager.uc.dto.AuthRoleDto;
import game.server.manager.uc.dto.ResetPasswordDto;
import game.server.manager.uc.dto.UserInfoDto;
import game.server.manager.uc.entity.UserInfo;
import game.server.manager.uc.service.SysRoleService;
import game.server.manager.uc.service.UserInfoService;
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

import java.util.List;
import java.util.Objects;



/**
 * 用户信息
 *
 * @author laoyu
 * @date 2022/7/17
 */
@RestController
@RequestMapping("/userManager")
public class SysUserController {


    public static final String MODULE_NAME = "用户管理";


    public static final String EDIT_DESCRIPTION = "编辑用户: ?1";

    public static final String EDIT_EXPRESSIONS = "#p1.id";

    public static final String CHANGE_DESCRIPTION = "修改状态: ?1";

    public static final String CHANGE_EXPRESSIONS = "#p1.id";

    public static final String REMOVE_DESCRIPTION = "删除用户: ?1";

    public static final String REMOVE_EXPRESSIONS = "#p1";

    public static final String AUTH_DESCRIPTION = "授权用户角色: ?1";

    public static final String AUTH_EXPRESSIONS = "#p1.userId";

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 获取用户列表
     */
    @PostMapping("/page")
    public MpDataResult list(@RequestBody MpBaseQo mpBaseQo) {
        return MpResultUtil.buildPage(userInfoService.page(mpBaseQo.startPage(), mpBaseQo.buildSearchWrapper()));
    }


    /**
     * 根据用户编号获取详细信息
     */
    @SaCheckRole("super_admin")
    @GetMapping(value = {"/info/{userId}"})
    public R<UserManagerVo> getInfo(@PathVariable(value = "userId") Long userId) {
        if (Objects.nonNull(userId)) {
            UserInfo userInfo = userInfoService.getById(userId);
            return DataResult.ok(BeanUtil.copyProperties(userInfo,UserManagerVo.class));
        }
        return DataResult.fail();
    }


    /**
     * 修改用户
     */
    @SaCheckRole("super_admin")
    @PostMapping("/edit")
    @SaveLog(logType = BaseController.LOG_TYPE, moduleName = MODULE_NAME, description = EDIT_DESCRIPTION, expressions =  EDIT_EXPRESSIONS, actionType = BaseController.ADD_ACTION)
    public R<Object> edit(@Validated @RequestBody UserInfoDto userInfoDto) {

        UserInfo entity = BeanUtil.copyProperties(userInfoDto,UserInfo.class);
        boolean result = userInfoService.updateById(entity);
        if(result){
            StpUtil.logout(entity.getId());
        }
        return result?DataResult.ok():DataResult.fail();
    }

    /**
     * 删除用户
     */
    @SaCheckRole("super_admin")
    @GetMapping("/delete/{userId}")
    @SaveLog(logType = BaseController.LOG_TYPE, moduleName = MODULE_NAME, description = REMOVE_DESCRIPTION, expressions = REMOVE_EXPRESSIONS, actionType = BaseController.REMOVE_ACTION)
    public R<Object> remove(@PathVariable("userId")Long userId) {
        if (userId.equals(AuthorizationUtil.getUser().getId())) {
            return DataResult.fail("不能删除自己");
        }
        return userInfoService.removeById(userId)?DataResult.ok():DataResult.fail();
    }

    /**
     * 重置密码
     */
    @SaCheckRole("super_admin")
    @PostMapping("/resetPwd")
    public R<Object> resetPwd(@RequestBody ResetPasswordDto resetPasswordDto) {
        resetPasswordDto.setUpdateUserId(AuthorizationUtil.getUser().getId());
        return userInfoService.resetPassword(resetPasswordDto)? DataResult.ok():DataResult.fail();
    }

    /**
     * 状态修改
     */
    @SaCheckRole("super_admin")
    @PostMapping("/changeStatus")
    @SaveLog(logType = BaseController.LOG_TYPE, moduleName = MODULE_NAME, description = CHANGE_DESCRIPTION, expressions = CHANGE_EXPRESSIONS, actionType = BaseController.EDIT_ACTION)
    public R<Object> changeStatus(@RequestBody ChangeStatusDto changeStatusDto) {
        changeStatusDto.setUpdateUserId(AuthorizationUtil.getUser().getId());
        return userInfoService.updateUserStatus(changeStatusDto)?DataResult.ok():DataResult.fail();
    }


    /**
     * 用户授权角色
     */
    @SaCheckRole("super_admin")
    @PostMapping("/authRole")
    @SaveLog(logType = BaseController.LOG_TYPE, moduleName = MODULE_NAME, description = AUTH_DESCRIPTION, expressions = AUTH_EXPRESSIONS, actionType = BaseController.EDIT_ACTION)
    public R<Object> insertAuthRole(@RequestBody @Validated AuthRoleDto authRoleDto) {
        return sysRoleService.insertUserAuth(authRoleDto)?DataResult.ok():DataResult.fail();
    }

    /**
     * 获取用户已授权角色集合
     */
    @SaCheckRole("super_admin")
    @GetMapping("/authRole/{userId}")
    public R<List<SysRoleVo>> authRole(@PathVariable("userId") Long userId) {
        return DataResult.ok(sysRoleService.selectRolesByUserId(userId));
    }


}
