package game.server.manager.server.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 应用信息
 * @author yuzhanfeng
 */
@TableName(value ="application_info")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationInfo implements Serializable {

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 应用id
     */
    @TableId(type = IdType.INPUT)
    private String applicationId;


    /**
     * 应用名称
     */
    private String applicationName;

    /**
     * 设备id
     */
    private String deviceId;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 设备类型
     */
    private Integer deviceType;

    /**
     * appId
     */
    private Long appId;

    /**
     * app名称
     */
    private String appName;

    /**
     * 所属用户
     */
    private Long userId;

    /**
     * 应用状态
     */
    private String status;

    /**
     * 通信公钥
     */
    private String publicKey;

    /**
     * 通信私钥
     */
    private String privateKey;

    /**
     * 黑名单
     */
    private Integer isBlack;

    /**
     * 插件信息
     */
    private String pluginsData;

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
     * 最后在线时间
     */
    private LocalDateTime lastUpTime;

    /**
     * 删除标记
     */
    @TableLogic
    private Integer delFlag;


}