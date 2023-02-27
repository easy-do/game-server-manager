package game.server.manager.uc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 授权客户端信息
 * @author yuzhanfeng
 * @TableName oauth_client_details
 */
@TableName(value ="oauth_client_details")
@Data
public class OauthClientDetails implements Serializable {

    /**
     * 客户端id
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String clientId;

    /**
     * 客户端密钥
     */
    private String clientSecret;

    /**
     * 客户端可申请的权限
     */
    private String scope;

    /**
     * 支持的授权模式:authorization_code,password,refresh_token,implicit,client_credentials
     */
    private String authorizedGrantTypes;

    /**
     * 重定向URI
     */
    private String redirectUri;

    /**
     * access_token的有效时间(秒)
     */
    private Long accessTokenValidity;

    /**
     * 刷新token的有效时间(秒)
     */
    private Long refreshTokenValidity;

    /**
     * 客户端token的有效时间(秒)
     */
    private Long clientTokenValidity;

    /**
     * 拓展数据
     */
    private String additionalInformation;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
