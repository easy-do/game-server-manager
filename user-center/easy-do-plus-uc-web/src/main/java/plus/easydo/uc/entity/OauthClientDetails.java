package plus.easydo.uc.entity;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;


/**
 * 授权客户端信息数据库映射对象
 * 
 * @author yuzhanfeng
 * @date 2023-02-27 16:01:25
 */
@Data
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@TableName("oauth_client_details")
public class OauthClientDetails implements Serializable {

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /** 客户端id */
    @TableId(value = "client_id" , type = IdType.ASSIGN_UUID)
    private String clientId;

    /** 客户端名称 */
    private String clientName;

    /** 客户端密钥 */
    private String clientSecret;

    /** 权限 */
    private String scope;

    /** 授权模式 */
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
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createTime;

    /** 更新者 */
    private Long updateBy;

    /** 更新时间 */
    private LocalDateTime updateTime;

}
