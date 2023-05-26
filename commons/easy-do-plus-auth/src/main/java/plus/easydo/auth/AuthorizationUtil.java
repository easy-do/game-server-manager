package plus.easydo.auth;

import cn.dev33.satoken.jwt.SaJwtUtil;
import cn.dev33.satoken.jwt.StpLogicJwtForSimple;
import cn.dev33.satoken.stp.SaLoginConfig;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import plus.easydo.auth.vo.SimpleUserInfoVo;
import plus.easydo.common.constant.SystemConstant;
import plus.easydo.common.vo.UserInfoVo;
import plus.easydo.redis.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author laoyu
 * @version 1.0
 * @date 2022/7/6
 */
@Component
public class AuthorizationUtil {

    @Autowired(required = false)
    private AbstractAuthorizationCodeService abstractAuthorizationCodeService;

    @Autowired
    private RedisUtils<Object> redisUtils;

    public UserInfoVo getUser(){
        StpUtil.checkLogin();
        return (UserInfoVo) redisUtils.get(SystemConstant.USER_CACHE + getUserId());
    }

    public static SimpleUserInfoVo getSimpleUser(){
        StpUtil.checkLogin();
        return JSONUtil.toBean(StpUtil.getExtra("userInfo").toString(),SimpleUserInfoVo.class);
    }

    public UserInfoVo getUser(Object userId){
        return (UserInfoVo) redisUtils.get(SystemConstant.USER_CACHE + userId);
    }


    public void reloadUserCache(UserInfoVo userInfo){
        StpUtil.login(userInfo.getId(), SaLoginConfig.setExtra(SystemConstant.TOKEN_USER_INFO, BeanUtil.copyProperties(userInfo, SimpleUserInfoVo.class)));
        redisUtils.set(SystemConstant.USER_CACHE + userInfo.getId(), userInfo);
    }

    public static long getUserId(){
        return StpUtil.getLoginIdAsLong();
    }

    public static boolean isAdmin(){
        return StpUtil.hasRole(SystemConstant.SUPER_ADMIN_ROLE);
    }

    public static SimpleUserInfoVo checkTokenOrLoadUser(String token) {
        StpLogicJwtForSimple stpLogic = (StpLogicJwtForSimple) StpUtil.stpLogic;
        JSONObject payloads = SaJwtUtil.getPayloadsNotCheck(token, stpLogic.loginType, stpLogic.jwtSecretKey());
        return payloads.get("userInfo",SimpleUserInfoVo.class);

    }

    public boolean isAdmin(String token){
        SimpleUserInfoVo info = checkTokenOrLoadUser(token);
        return getUser(info.getId()).getRoles().contains(SystemConstant.SUPER_ADMIN_ROLE);
    }
    public static Long getUserId(String token){
        return checkTokenOrLoadUser(token).getId();
    }

    /**
     * 校验授权信息
     *
     * @param authType authType
     * @author laoyu
     * @date 2022/7/6
     */
    public void checkAuthorization(String authType){
        if(!isAdmin() && Objects.nonNull(abstractAuthorizationCodeService)){
            abstractAuthorizationCodeService.checkAuthorization(getUser(), authType);
        }
    }

    public static boolean isLogin(){
       return StpUtil.isLogin();
    }
}
