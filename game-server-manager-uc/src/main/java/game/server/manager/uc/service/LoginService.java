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
import game.server.manager.redis.config.RedisUtils;
import game.server.manager.uc.dto.LoginModel;
import game.server.manager.uc.entity.UserInfo;
import game.server.manager.uc.mapstruct.UserInfoMapstruct;
import game.server.manager.uc.vo.FunctionAuthVo;
import game.server.manager.uc.vo.UserResourceVo;
import me.zhyd.oauth.model.AuthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;
import game.server.manager.common.exception.BizException;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;


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
    private SysResourceService sysResourceService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private RedisUtils<Object> redisUtils;

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
        return afterLogin(user);
    }

    private UserInfoVo afterLogin(UserInfo user) {
        UserInfoVo userInfoVo = UserInfoMapstruct.INSTANCE.entityToVo(user);
        setUserLastLoginDetails(user);
        buildUserRoleAndPermission(userInfoVo);
        StpUtil.login(user.getId(), SaLoginConfig.setExtra(SystemConstant.TOKEN_USER_INFO, UserInfoMapstruct.INSTANCE.voToSimpleVo(userInfoVo)));
        userInfoVo.setToken(StpUtil.getTokenValue());
        redisUtils.set(SystemConstant.USER_CACHE+userInfoVo.getId(),userInfoVo);
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
        //对照密码·
        if(BCrypt.checkpw(password,dbPassword)){
            return afterLogin(user);
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
            UserInfoVo userInfoVo = afterLogin(user);
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
        UserInfoVo userInfoVo = userInfoService.getOrCreateUserInfo(authUser);
        userInfoVo = afterLogin(UserInfoMapstruct.INSTANCE.voToEntity(userInfoVo));
        return userInfoVo.getToken();
    }

    //TODO 改造为消息发布
    @Async
    public void setUserLastLoginDetails(UserInfoVo userInfoVo){
        LocalDateTime loginTime = LocalDateTime.now();
        String ip = IpRegionSearchUtil.searchRequestIp();
        UserInfo userinfo = UserInfo.builder()
                .id(userInfoVo.getId()).lastLoginTime(loginTime).build();
        if(CharSequenceUtil.isNotEmpty(ip)){
            userinfo.setLoginIp(ip);
            userInfoVo.setLoginIp(ip);
        }
        userInfoService.updateById(userinfo);
    }

    //TODO 改造为消息发布
    @Async
    public void setUserLastLoginDetails(UserInfo userInfo){
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
    }

    /**
     * 构建用户角色和权限信息
     *
     * @param userInfoVo userInfoVo
     * @author laoyu
     * @date 2022/9/20
     */
    private void buildUserRoleAndPermission(UserInfoVo userInfoVo) {
        Long userId = userInfoVo.getId();
        List<String> roleList = sysRoleService.selectRolesByUserId(userId).stream().map(SysRoleVo::getRoleKey).toList();
        userInfoVo.setRoles(roleList);
        List<UserResourceVo> userResource = sysResourceService.userResource(userId);
        Set<String> permissionList = buildPermissions(userResource);
        userInfoVo.setPermissions(permissionList.stream().toList());
        Map<String,List<String>> resourceAction = buildResourceAction(userResource);
        userInfoVo.setResourceAction(resourceAction);
    }

    private Map<String, List<String>> buildResourceAction(List<UserResourceVo> userResourceList) {
        Map<String, List<String>> resourceActions = new HashMap<>();
        userResourceList.parallelStream().forEach(userResource->{
            List<FunctionAuthVo> functionAuths = userResource.getFunctionAuths();
            if(Objects.nonNull(functionAuths) && !functionAuths.isEmpty()){
                resourceActions.put(userResource.getKey(),functionAuths.parallelStream().map(FunctionAuthVo::getKey).toList());
            }else {
                resourceActions.put(userResource.getKey(), Collections.emptyList());
            }
            List<UserResourceVo> children = userResource.getChildren();
            if(Objects.nonNull(children) && !children.isEmpty()){
                resourceActions.putAll(buildResourceAction(children));
            }
        });
        return resourceActions;
    }

    private Set<String> buildPermissions(List<UserResourceVo> userResourceList) {
        Set<String> permissions = new HashSet<>();
        userResourceList.parallelStream().forEach(userResource->{
            permissions.add(userResource.getKey());
            List<FunctionAuthVo> functionAuths = userResource.getFunctionAuths();
            if (Objects.nonNull(functionAuths) && !functionAuths.isEmpty()) {
                permissions.addAll(functionAuths.stream().map(FunctionAuthVo::getKey).toList());
            }
            List<UserResourceVo> children = userResource.getChildren();
            if(Objects.nonNull(children) && !children.isEmpty()){
                permissions.addAll(buildPermissions(children));
            }
        });
        return permissions;
    }


    public void logout() {
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        StpUtil.logout(tokenInfo.loginId);
        redisUtils.delete(SystemConstant.USER_CACHE+tokenInfo.loginId);
    }

    /**
     * 根据openId获取登录的用户信息
     *
     * @param openId openId
     * @return game.server.manager.common.vo.UserInfoVo
     * @author laoyu
     * @date 2023-02-27
     */
    public UserInfoVo getUserInfoByOpenId(String openId) {
        List<String> ids = CharSequenceUtil.split(openId, "_");
        UserInfo user = userInfoService.getById(ids.get(1));
        if(Objects.isNull(user)){
            throw new BizException("500","可能为非密码模式授权,无法获取到用户信息。");
        }
        UserInfoVo userInfoVo = UserInfoMapstruct.INSTANCE.entityToVo(user);
        buildUserRoleAndPermission(userInfoVo);
        return userInfoVo;
    }
}
