package game.server.manager.uc.dto;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import game.server.manager.common.vaild.Insert;
import game.server.manager.common.vaild.Update;

import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;

/**
 * 授权客户端信息数据传输对象
 * 
 * @author yuzhanfeng
 * @date 2023-02-27 16:01:25
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class OauthClientDetailsDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 客户端id */

    @NotNull(message = "客户端id必填",groups = {Insert.class, Update.class})
    private String clientId;

    /** 客户端名称 */

    @NotNull(message = "客户端名称必填",groups = {Insert.class, Update.class})
    private String clientName;

    /** 客户端密钥 */

    @NotNull(message = "客户端密钥必填",groups = {Insert.class, Update.class})
    private String clientSecret;

    /** 权限 */

    @NotNull(message = "权限必填",groups = {Insert.class, Update.class})
    private String scope;

    /** 授权模式 */

    @NotNull(message = "授权模式必填",groups = {Insert.class, Update.class})
    private String authorizedGrantTypes;

    /** 重定向URI */
    private String redirectUri;

    /** access_token的有效时间(秒) */

    @NotNull(message = "access_token的有效时间(秒)必填",groups = {Insert.class, Update.class})
    private Long accessTokenValidity;

    /** 刷新token的有效时间(秒) */
    private Long refreshTokenValidity;

    /** 客户端token的有效时间(秒) */
    private Long clientTokenValidity;

    /** 拓展数据 */
    private String additionalInformation;

    /** 状态 */

    @NotNull(message = "状态必填",groups = {Insert.class, Update.class})
    private Integer status;

    /** 审核状态 */

    @NotNull(message = "审核状态必填",groups = {Insert.class, Update.class})
    private Integer isAudit;

    /** 备注 */
    private String remark;

    /** 创建者 */
    private Long createBy;

    /** 创建时间 */

    @NotNull(message = "创建时间必填",groups = {Insert.class, Update.class})
    private LocalDateTime createTime;

    /** 更新者 */
    private Long updateBy;

    /** 更新时间 */
    private LocalDateTime updateTime;

}
