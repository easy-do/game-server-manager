package game.server.manager.auth;

import cn.dev33.satoken.stp.StpInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author yuzhanfeng
 * @Date 2022/9/1 17:31
 * @Description 自定义权限验证接口扩展
 */
@Component
public class StpInterfaceImpl implements StpInterface {
    @Autowired
    private AuthStateRedisCache authStateRedisCache;

    //TODO 缓存用户权限和角色集合

    /**
     * 返回一个账号所拥有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        Object list = authStateRedisCache.getUserPermissions(loginId);
        if(Objects.isNull(list)){
            return Collections.emptyList();
        }
        return (List<String>) list;
    }

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        Object list = authStateRedisCache.getUserRoleList(loginId);
        if(Objects.isNull(list)){
            return Collections.emptyList();
        }
        return (List<String>) list;
    }

}

