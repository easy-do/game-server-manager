package game.server.manager.auth;

import game.server.manager.common.vo.UserInfoVo;

/**
 * @author yuzhanfeng
 */
public interface AbstractAuthorizationCodeService {

    /**
     * 校验授权码
     *
     * @param user user
     * @param authType authType
     * @author laoyu
     * @date 2022/8/30
     */
    void checkAuthorization(UserInfoVo user, String authType);
}
