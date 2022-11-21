package game.server.manager.auth;

import cn.dev33.satoken.jwt.SaJwtUtil;
import cn.dev33.satoken.jwt.StpLogicJwtForSimple;
import cn.dev33.satoken.stp.SaLoginConfig;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.json.JSONObject;
import game.server.manager.common.constant.SystemConstant;
import game.server.manager.common.vo.UserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
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

    public static UserInfoVo getUser(){
        StpUtil.checkLogin();
        JSONObject userJson = (JSONObject) StpUtil.getExtra(SystemConstant.TOKEN_USER_INFO);
        Integer lastLoginTimeInt = userJson.getInt("lastLoginTime");
        userJson.remove("lastLoginTime");
        UserInfoVo userInfoVo = userJson.toBean(UserInfoVo.class);
        LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(lastLoginTimeInt, 0, ZoneOffset.ofHours(8));
        userInfoVo.setLastLoginTime(localDateTime);
        return userInfoVo ;
    }

    public static void reloadUserCache(UserInfoVo userInfo){
        StpUtil.login(userInfo.getId(), SaLoginConfig.setExtra(SystemConstant.TOKEN_USER_INFO,userInfo));
    }

    public static long getUserId(){
        return StpUtil.getLoginIdAsLong();
    }

    public static boolean isAdmin(){
        return StpUtil.hasRole(SystemConstant.SUPER_ADMIN_ROLE);
    }

    public static cn.hutool.json.JSONObject checkTokenOrLoadUserJson(String token) {
        StpLogicJwtForSimple stpLogic = (StpLogicJwtForSimple) StpUtil.stpLogic;
        cn.hutool.json.JSONObject payloads = SaJwtUtil.getPayloadsNotCheck(token, stpLogic.loginType, stpLogic.jwtSecretKey());
        return payloads.getJSONObject(SystemConstant.TOKEN_USER_INFO);

    }

    public static boolean isAdmin(String token){
        cn.hutool.json.JSONObject info = checkTokenOrLoadUserJson(token);
        return info.getJSONArray(SystemConstant.TOKEN_USER_ROLES).contains(SystemConstant.SUPER_ADMIN_ROLE);
    }
    public static Long getUserId(String token){
        cn.hutool.json.JSONObject info = checkTokenOrLoadUserJson(token);
        return info.getLong("id");
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
}
