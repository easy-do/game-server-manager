package game.server.manager.common.vo;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @author laoyu
 * @version 1.0
 * @date 2022/2/22
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoVo implements Serializable {

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
     * 积分
     */
    private Long points;

    /**
     * 授权信息
     *
     */
    private String authorization;

    /**
     * 平台
     */
    private String platform;

    /**
     * 用户头像
     */
    private String avatarUrl;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 登录ip
     */
    private String loginIp;

    /**
     * 注册时间
     */
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private LocalDateTime creatTime;

    /**
     * 最后登录时间
     */
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private LocalDateTime lastLoginTime;

    private String token;

    private List<String> roles;

    private List<String> permissions;

    private Map<String,List<String>> resourceAction;


}
