package game.server.manager.auth;

import game.server.manager.common.constant.SystemConstant;
import game.server.manager.redis.config.RedisUtils;
import me.zhyd.oauth.cache.AuthStateCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author laoyu
 * @version 1.0
 * @date 2022/2/23
 */
@Component
public class AuthStateRedisCache implements AuthStateCache {


    @Value("${sa-token.timeout:60}")
    private Integer timeout;

    @Autowired
    RedisUtils<Object> redisUtils;

    @Override
    public void cache(String key, String value) {
        redisUtils.set(SystemConstant.PREFIX + SystemConstant.JUST_AUTH + key, value, timeout, TimeUnit.MINUTES);
    }

    @Override
    public void cache(String key, String value, long timeout) {
        redisUtils.set(SystemConstant.PREFIX + SystemConstant.JUST_AUTH + key, value, timeout, TimeUnit.MILLISECONDS);
    }

    @Override
    public String get(String key) {
        return (String) redisUtils.get(SystemConstant.PREFIX + SystemConstant.JUST_AUTH + key);
    }

    @Override
    public boolean containsKey(String key) {
        return redisUtils.hasKey(SystemConstant.PREFIX + SystemConstant.JUST_AUTH + key);
    }

    public void cacheUserRoleList(Object userId, List<String> roles) {
        redisUtils.set(SystemConstant.PREFIX + SystemConstant.USER_ROLES + userId,roles);
    }

    public Object getUserRoleList(Object userId) {
        return redisUtils.get(SystemConstant.PREFIX + SystemConstant.USER_ROLES + userId);
    }


    public void cacheUserPermissions(Object userId, List<String> permissions) {
        redisUtils.set(SystemConstant.PREFIX + SystemConstant.USER_PERMISSION + userId,permissions);
    }

    public Object getUserPermissions(Object userId) {
        return redisUtils.get(SystemConstant.PREFIX + SystemConstant.USER_PERMISSION + userId);
    }

    public void cleanUserRoleAndPermission(Object userId) {
        redisUtils.delete(SystemConstant.PREFIX + SystemConstant.USER_ROLES + userId);
        redisUtils.delete(SystemConstant.PREFIX + SystemConstant.USER_PERMISSION + userId);
    }
}
