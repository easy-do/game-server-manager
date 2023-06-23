package plus.easydo.uc.service;

import plus.easydo.auth.AbstractAuthorizationCodeService;
import com.baomidou.mybatisplus.extension.service.IService;
import plus.easydo.common.vo.UserInfoVo;
import plus.easydo.common.dto.AuthorizationConfigDto;
import plus.easydo.uc.entity.AuthorizationCode;

/**
* @author yuzhanfeng
* @description 针对表【authorization_code(授权码)】的数据库操作Service
* @createDate 2022-05-19 13:13:29
*/
public interface AuthorizationCodeService  extends IService<AuthorizationCode> , AbstractAuthorizationCodeService {

    /**
     * 批量生成授权码
     *
     * @param authorizationConfigDto authorizationConfigDto
     * @return boolean
     * @author laoyu
     * @date 2022/5/19
     */
    boolean generateAuthorization(AuthorizationConfigDto authorizationConfigDto);

    /**
     * 校验用户授权信息
     *
     * @param user   user
     * @param type
     * @author laoyu
     * @date 2022/5/21
     */
    @Override
    void checkAuthorization(UserInfoVo user, String type);
}
