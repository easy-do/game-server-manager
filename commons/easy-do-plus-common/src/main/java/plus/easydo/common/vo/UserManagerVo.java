package plus.easydo.common.vo;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonFormat;
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
public class UserManagerVo implements Serializable {


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
     * 登录ip
     */
    private String loginIp;

    /**
     * 最后登录时间
     */
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private LocalDateTime lastLoginTime;

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
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private LocalDateTime updateTime;

    /**
     * 更新人
     */
    private Long updateBy;

    /**
     * 删除标记
     */
    private Integer delFlag;

    /**
     * 是否为管理员
     */
    private Integer isAdmin;

}
