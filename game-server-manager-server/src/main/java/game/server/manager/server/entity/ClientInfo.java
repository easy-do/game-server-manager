package game.server.manager.server.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 客户端信息
 * @author yuzhanfeng
 * @TableName client_info
 */
@TableName(value ="client_info")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientInfo implements Serializable {

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 所属用户
     */
    private String clientName;

    /**
     * 所属服务器
     */
    private Long serverId;

    /**
     * 服务器名称
     */
    private String serverName;

    /**
     * 所属用户
     */
    private String userName;

    /**
     * 状态
     */
    private String status;

    /**
     * 客户端信息
     */
    private String clientData;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 创建人
     */
    private Long createBy;

    /**
     * 更新人
     */
    private Long updateBy;

    /**
     * 最后在线时间
     */
    private LocalDateTime lastUpTime;

    /**
     * 删除标记
     */
    private Integer delFlag;

    /**
     * 通信公钥
     */
    private String publicKey;

    /**
     * 通信私钥
     */
    private String privateKey;

}