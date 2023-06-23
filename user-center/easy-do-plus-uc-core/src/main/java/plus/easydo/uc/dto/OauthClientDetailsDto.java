package plus.easydo.uc.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import plus.easydo.common.vaild.Insert;
import plus.easydo.common.vaild.Update;

import jakarta.validation.constraints.NotNull;
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

    private String clientId;

    /** 客户端名称 */

    @NotNull(message = "客户端名称必填",groups = {Insert.class, Update.class})
    private String clientName;

    /** 客户端密钥 */

    private String clientSecret;

    /** 权限 */

    private String scope;

    /** 授权模式 */

    @NotNull(message = "授权模式必填",groups = {Insert.class, Update.class})
    private String authorizedGrantTypes;

    /** 重定向URI */
    private String redirectUri;

    /** access_token的有效时间(秒) */

    private Long accessTokenValidity;

    /** 刷新token的有效时间(秒) */
    private Long refreshTokenValidity;

    /** 客户端token的有效时间(秒) */
    private Long clientTokenValidity;

    /** 拓展数据 */
    private String additionalInformation;

    /** 状态 */

    private Integer status;

    /** 审核状态 */

    private Integer isAudit;

    /** 备注 */
    private String remark;

    /** 创建者 */
    private Long createBy;

    /** 创建时间 */

    private LocalDateTime createTime;

    /** 更新者 */
    private Long updateBy;

    /** 更新时间 */
    private LocalDateTime updateTime;

}
