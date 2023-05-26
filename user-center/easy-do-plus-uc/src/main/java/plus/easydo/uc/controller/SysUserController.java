package plus.easydo.uc.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.zhxu.bs.BeanSearcher;
import lombok.RequiredArgsConstructor;
import plus.easydo.auth.AuthorizationUtil;
import plus.easydo.common.constant.SystemConstant;
import plus.easydo.common.dto.ChangeStatusDto;
import plus.easydo.log.SaveLog;
import plus.easydo.common.result.MpDataResult;
import plus.easydo.dao.result.MpResultUtil;
import plus.easydo.common.vo.SysRoleVo;
import plus.easydo.common.vo.UserManagerVo;
import plus.easydo.uc.dto.AuthRoleDto;
import plus.easydo.uc.dto.ResetPasswordDto;
import plus.easydo.uc.dto.UserInfoDto;
import plus.easydo.uc.entity.UserInfo;
import plus.easydo.uc.service.SysRoleService;
import plus.easydo.uc.service.UserInfoService;
import plus.easydo.web.base.BaseController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plus.easydo.common.result.DataResult;
import plus.easydo.common.result.R;

import java.util.List;
import java.util.Map;
import java.util.Objects;



/**
 * 用户信息
 *
 * @author laoyu
 * @date 2022/7/17
 */
@RestController
@RequestMapping("/userManager")
@RequiredArgsConstructor
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

    private final UserInfoService userInfoService;

    private final SysRoleService sysRoleService;

    private final BeanSearcher beanSearcher;

    /**
     * 获取用户列表
     */
    @PostMapping("/page")
    public MpDataResult list(@RequestBody Map<String,Object> queryParam) {
        return MpResultUtil.buildPage(beanSearcher.search(UserInfo.class,queryParam));
    }


    /**
     * 根据用户编号获取详细信息
     */
    @SaCheckRole(SystemConstant.SUPER_ADMIN_ROLE)
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
    @SaCheckRole(SystemConstant.SUPER_ADMIN_ROLE)
    @PostMapping("/update")
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
    @SaCheckRole(SystemConstant.SUPER_ADMIN_ROLE)
    @GetMapping("/delete/{userId}")
    @SaveLog(logType = BaseController.LOG_TYPE, moduleName = MODULE_NAME, description = REMOVE_DESCRIPTION, expressions = REMOVE_EXPRESSIONS, actionType = BaseController.REMOVE_ACTION)
    public R<Object> remove(@PathVariable("userId")Long userId) {
        if (userId.equals(AuthorizationUtil.getSimpleUser().getId())) {
            return DataResult.fail("不能删除自己");
        }
        return userInfoService.removeById(userId)?DataResult.ok():DataResult.fail();
    }

    /**
     * 重置密码
     */
    @SaCheckRole(SystemConstant.SUPER_ADMIN_ROLE)
    @PostMapping("/resetPwd")
    public R<Object> resetPwd(@RequestBody ResetPasswordDto resetPasswordDto) {
        resetPasswordDto.setUpdateUserId(AuthorizationUtil.getSimpleUser().getId());
        return userInfoService.resetPassword(resetPasswordDto)? DataResult.ok():DataResult.fail();
    }

    /**
     * 状态修改
     */
    @SaCheckRole(SystemConstant.SUPER_ADMIN_ROLE)
    @PostMapping("/changeStatus")
    @SaveLog(logType = BaseController.LOG_TYPE, moduleName = MODULE_NAME, description = CHANGE_DESCRIPTION, expressions = CHANGE_EXPRESSIONS, actionType = BaseController.EDIT_ACTION)
    public R<Object> changeStatus(@RequestBody ChangeStatusDto changeStatusDto) {
        changeStatusDto.setUpdateUserId(AuthorizationUtil.getSimpleUser().getId());
        return userInfoService.updateUserStatus(changeStatusDto)?DataResult.ok():DataResult.fail();
    }


    /**
     * 用户授权角色
     */
    @SaCheckRole(SystemConstant.SUPER_ADMIN_ROLE)
    @PostMapping("/authRole")
    @SaveLog(logType = BaseController.LOG_TYPE, moduleName = MODULE_NAME, description = AUTH_DESCRIPTION, expressions = AUTH_EXPRESSIONS, actionType = BaseController.EDIT_ACTION)
    public R<Object> insertAuthRole(@RequestBody @Validated AuthRoleDto authRoleDto) {
        return sysRoleService.insertUserAuth(authRoleDto)?DataResult.ok():DataResult.fail();
    }

    /**
     * 获取用户已授权角色集合
     */
    @SaCheckRole(SystemConstant.SUPER_ADMIN_ROLE)
    @GetMapping("/authRole/{userId}")
    public R<List<SysRoleVo>> authRole(@PathVariable("userId") Long userId) {
        return DataResult.ok(sysRoleService.selectRolesByUserId(userId));
    }


}
