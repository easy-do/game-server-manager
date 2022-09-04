package game.server.manager.uc.service;

import cn.dev33.satoken.stp.SaLoginConfig;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import game.server.manager.common.constant.SystemConstant;
import game.server.manager.common.enums.LoginTypeEnums;
import game.server.manager.common.utils.IpRegionSearchUtil;
import game.server.manager.common.vo.SysRoleVo;
import game.server.manager.common.vo.UserInfoVo;
import game.server.manager.uc.dto.LoginModel;
import game.server.manager.uc.entity.UserInfo;
import game.server.manager.uc.mapstruct.UserInfoMapstruct;
import me.zhyd.oauth.model.AuthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;
import game.server.manager.common.exception.BizException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * @author laoyu
 * @version 1.0
 * @description 登录服务
 * @date 2022/6/14
 */
@Component
public class LoginService {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private EmailService emailService;

    /**
     * 登录
     *
     * @param loginModel loginModel
     * @return game.server.manager.server.vo.UserInfoVo
     * @author laoyu
     * @date 2022/6/14
     */
    public UserInfoVo login(LoginModel loginModel) {
        if(loginModel.getLoginType().equals(LoginTypeEnums.EMAIL.getType())){
            return emailLogin(loginModel);
        }
        if(loginModel.getLoginType().equals(LoginTypeEnums.PASSWORD.getType())){
            return passwordLogin(loginModel);
        }
        if(loginModel.getLoginType().equals(LoginTypeEnums.SECRET.getType())){
            return secretLogin(loginModel);
        }
        throw new BizException("500","不支持的登录方式");
    }

    /**
     * 密钥登陆
     *
     * @param loginModel loginModel
     * @return game.server.manager.server.vo.UserInfoVo
     * @author laoyu
     * @date 2022/6/14
     */
    private UserInfoVo secretLogin(LoginModel loginModel) {
        String secret = loginModel.getPassword();
        LambdaQueryWrapper<UserInfo> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(UserInfo::getSecret,secret);
        UserInfo user = userInfoService.getOne(wrapper);
        if(Objects.isNull(user)){
           throw  new BizException("500","无效密钥");
        }
        setUserLastLoginDetails(user);
        UserInfoVo userInfoVo = UserInfoMapstruct.INSTANCE.entityToVo(user);
        buildUserRoleAndPermission(userInfoVo);
        StpUtil.login(user.getId(), SaLoginConfig.setExtra(SystemConstant.TOKEN_USER_INFO, userInfoVo));
        userInfoVo.setToken(StpUtil.getTokenValue());
        return userInfoVo;
    }

    /**
     * 密码登录
     *
     * @param loginModel loginModel
     * @return game.server.manager.server.vo.UserInfoVo
     * @author laoyu
     * @date 2022/6/14
     */
    private UserInfoVo passwordLogin(LoginModel loginModel) {
        String email = loginModel.getUserName();
        if(CharSequenceUtil.isBlank(email)){
            throw new BizException("500","账号不能为空");
        }
        String password = loginModel.getPassword();
        LambdaQueryWrapper<UserInfo> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(UserInfo::getEmail,email);
        UserInfo user = userInfoService.getOne(wrapper);
        if(Objects.isNull(user)){
            throw new BizException("500","账号不存在");
        }
        String dbPassword = user.getPassword();
        if(CharSequenceUtil.isBlank(dbPassword)){
            throw new BizException("500","帐号没有设置密码,请重置密码.");
        }
        //对照结果·
        if(BCrypt.checkpw(password,dbPassword)){
            setUserLastLoginDetails(user);
            UserInfoVo userInfoVo = UserInfoMapstruct.INSTANCE.entityToVo(user);
            buildUserRoleAndPermission(userInfoVo);
            StpUtil.login(user.getId(), SaLoginConfig.setExtra(SystemConstant.TOKEN_USER_INFO, userInfoVo));
            userInfoVo.setToken(StpUtil.getTokenValue());
            return userInfoVo;
        }
        throw new BizException("500","密码错误");
    }

    /**
     * 邮箱登录
     *
     * @param loginModel loginModel
     * @return game.server.manager.server.vo.UserInfoVo
     * @author laoyu
     * @date 2022/6/14
     */
    private UserInfoVo emailLogin(LoginModel loginModel){
        String email = loginModel.getUserName();
        String code = loginModel.getPassword();
        boolean result = emailService.checkCode(email, code);
        if(result){
            LambdaQueryWrapper<UserInfo> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(UserInfo::getEmail,email);
            UserInfo user = userInfoService.getOne(wrapper);
            if(Objects.isNull(user)){
                throw new BizException("500","邮箱未绑定账号");
            }
            setUserLastLoginDetails(user);
            UserInfoVo userInfoVo = UserInfoMapstruct.INSTANCE.entityToVo(user);
            buildUserRoleAndPermission(userInfoVo);
            StpUtil.login(user.getId(), SaLoginConfig.setExtra(SystemConstant.TOKEN_USER_INFO, userInfoVo));
            userInfoVo.setToken(StpUtil.getTokenValue());
            return userInfoVo;
        }else {
            throw new BizException("500","验证码错误或不存在");
        }
    }

    /**
     * 第三方平台登陆
     *
     * @param authUser authUser
     * @return game.server.manager.server.vo.UserInfoVo
     * @author laoyu
     * @date 2022/6/14
     */
    public String platformLogin(AuthUser authUser) {
        UserInfoVo user = userInfoService.getOrCreateUserInfo(authUser);
        setUserLastLoginDetails(user);
        buildUserRoleAndPermission(user);
        StpUtil.login(user.getId(), SaLoginConfig.setExtra(SystemConstant.TOKEN_USER_INFO, user));
        return StpUtil.getTokenValue();
    }

    //TODO 改造为消息发布
    private UserInfoVo setUserLastLoginDetails(UserInfoVo userInfoVo){
        LocalDateTime loginTime = LocalDateTime.now();
        String ip = IpRegionSearchUtil.searchRequestIp();
        UserInfo userinfo = UserInfo.builder()
                .id(userInfoVo.getId()).lastLoginTime(loginTime).build();
        if(CharSequenceUtil.isNotEmpty(ip)){
            userinfo.setLoginIp(ip);
            userInfoVo.setLoginIp(ip);
        }
        userInfoService.updateById(userinfo);
        return userInfoVo;
    }

    //TODO 改造为消息发布
    private UserInfo setUserLastLoginDetails(UserInfo userInfo){
        LocalDateTime loginTime = LocalDateTime.now();
        String ip = IpRegionSearchUtil.searchRequestIp();
        UserInfo userinfoEntity = UserInfo.builder()
                .id(userInfo.getId()).lastLoginTime(loginTime).build();
        if(CharSequenceUtil.isNotEmpty(ip)){
            ip = ip.split(",")[0];
            userinfoEntity.setLoginIp(ip);
            userInfo.setLoginIp(ip);
        }
        userInfoService.updateById(userinfoEntity);
        return userInfo;
    }

    private void buildUserRoleAndPermission(UserInfoVo userInfoVo) {
        Long userId = userInfoVo.getId();
        List<String> roleList = sysRoleService.selectRolesByUserId(userId).stream().map(SysRoleVo::getRoleKey).toList();
        List<String> permissionList = sysMenuService.userPermissionList(userId);
        userInfoVo.setRoles(roleList);
        userInfoVo.setPermissions(permissionList);
    }

    public void logout() {
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        StpUtil.logout(tokenInfo.loginId);
    }
}
