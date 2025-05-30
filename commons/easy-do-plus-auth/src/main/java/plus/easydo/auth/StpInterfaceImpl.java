package plus.easydo.auth;

import cn.dev33.satoken.stp.StpInterface;
import plus.easydo.common.vo.UserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @author yuzhanfeng
 * @Date 2022/9/1 17:31
 * @Description 自定义权限验证接口扩展
 */
@Component
public class StpInterfaceImpl implements StpInterface {

    @Autowired
    private AuthorizationUtil authorizationUtil;

    /**
     * 返回一个账号所拥有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        return authorizationUtil.getUser(loginId).getPermissions();
    }

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        UserInfoVo user = authorizationUtil.getUser(loginId);
        return user.getRoles();
    }

}

