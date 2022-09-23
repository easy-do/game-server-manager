package game.server.manager.auth;

import cn.dev33.satoken.stp.StpInterface;
import game.server.manager.common.vo.UserInfoVo;
import game.server.manager.redis.config.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static game.server.manager.common.constant.SystemConstant.USER_PERMISSION;

/**
 * @author yuzhanfeng
 * @Date 2022/9/1 17:31
 * @Description 自定义权限验证接口扩展
 */
@Component
public class StpInterfaceImpl implements StpInterface {

    @Autowired
    private RedisUtils<List<String>> redisUtils;

    /**
     * 返回一个账号所拥有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        return redisUtils.get(USER_PERMISSION+loginId);
    }

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        UserInfoVo user = AuthorizationUtil.getUser();
        return user.getRoles();
    }

}

