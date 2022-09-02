package game.server.manager.common.vo;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 应用信息
 * @author yuzhanfeng
 */
@Data
public class ApplicationInfoVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 应用id
     */
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
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private LocalDateTime createTime;


    /**
     * 最后在线时间
     */
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private LocalDateTime lastUpTime;




}