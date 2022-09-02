package game.server.manager.uc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户信息
 *
 * @author yuzhanfeng
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDto implements Serializable {


    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
    private Long id;

    /**
     * unionId
     */
    private String unionId;

    /**
     * 用户密钥
     */
    private String secret;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 第三方平台
     */
    private String platform;

    /**
     * 用户头像
     */
    private String avatarUrl;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 密码
     */
    private String password;

    /**
     * 登录ip
     */
    private String loginIp;

    /**
     * 最后登录时间
     */
    private LocalDateTime lastLoginTime;

    /**
     * 盐
     */
    private String salt;

    /**
     * 积分
     */
    private Long points;

    /**
     * 状态
     */
    private Integer state;

    /**
     * 授权信息
     *
     */
    private String authorization;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 删除标记
     */
    private Integer delFlag;

    /**
     * 是否为管理员
     */
    private Integer isAdmin;

    public Boolean isAdmin(){
        return this.isAdmin == 1;
    }

}