package game.server.manager.auth;

import cn.dev33.satoken.stp.SaLoginConfig;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.json.JSONObject;
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
        JSONObject userJson = (JSONObject) StpUtil.getExtra("info");
        Integer lastLoginTimeInt = userJson.getInt("lastLoginTime");
        userJson.remove("lastLoginTime");
        UserInfoVo userInfoVo = userJson.toBean(UserInfoVo.class);
        LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(lastLoginTimeInt, 0, ZoneOffset.ofHours(8));
        userInfoVo.setLastLoginTime(localDateTime);
        return userInfoVo ;
    }

    public static void reloadUserCache(UserInfoVo userInfo){
        StpUtil.login(userInfo.getId(), SaLoginConfig.setExtra("info",userInfo));
    }

    public boolean isAdmin(){
        return getUser().isAdmin();
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
