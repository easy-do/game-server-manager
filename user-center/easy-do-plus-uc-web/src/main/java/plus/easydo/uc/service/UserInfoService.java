package plus.easydo.uc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import plus.easydo.common.dto.ChangeStatusDto;
import plus.easydo.common.vo.UserInfoVo;
import plus.easydo.uc.dto.ResetPasswordDto;
import plus.easydo.uc.dto.RestPasswordModel;
import plus.easydo.uc.entity.UserInfo;
import me.zhyd.oauth.model.AuthUser;

/**
 *
 * @author yuzhanfeng
 */
public interface UserInfoService extends IService<UserInfo>{


    /**
     * 获取用户信息 不存在则创建
     *
     * @param authUser authUser
     * @return plus.easydo.server.vo.UserInfoVo
     * @author laoyu
     * @date 2022/5/19
     */
    UserInfoVo getOrCreateUserInfo(AuthUser authUser);

    /**
     * 用户授权
     *
     * @param authorizationCode authorizationCode
     * @return boolean
     * @author laoyu
     * @date 2022/5/19
     */
    boolean authorization(String authorizationCode);

    /**
     * 拉黑用户
     *
     * @param id id
     * @author laoyu
     * @date 2022/5/21
     */
    void setBlack(Long id);

    /**
     * 绑定邮箱
     *
     * @param id id
     * @param email email
     * @param code code
     * @return java.lang.Object
     * @author laoyu
     * @date 2022/6/12
     */
    boolean bindingEmail(Long id, String email, String code);

    /**
     * 重置密码
     *
     * @param restPasswordModel restPasswordModel
     * @return java.lang.Boolean
     * @author laoyu
     * @date 2022/6/15
     */
    boolean resetPassword(RestPasswordModel restPasswordModel);

    /**
     * 重置密码
     *
     * @param resetPasswordDto resetPasswordDto
     * @return boolean
     * @author laoyu
     * @date 2022/7/17
     */
    boolean resetPassword(ResetPasswordDto resetPasswordDto);

    /**
     * 重置密钥
     *
     * @return boolean
     * @author laoyu
     * @date 2022/7/10
     */
    boolean resetSecret();

    /**
     * 获取用户头像链接
     *
     * @param id id
     * @return java.lang.String
     * @author laoyu
     * @date 2022/7/11
     */
    String avatar(Long id);

    /**
     * 更改角色状态
     *
     * @param changeStatusDto changeStatusDto
     * @return boolean
     * @author laoyu
     * @date 2022/7/17
     */
    boolean updateUserStatus(ChangeStatusDto changeStatusDto);

    /**
     * 用户重置密码
     *
     * @param restPasswordModel restPasswordModel
     * @return boolean
     * @author laoyu
     * @date 2022/11/17
     */
    boolean userResetPassword(RestPasswordModel restPasswordModel);
}
