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

/**
 * 执行日志
 * @author yuzhanfeng
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExecuteLogVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    
    
    /**
     * 自增主键
     */
    private Long id;


    /**
     * 脚本id
     */
    private Long scriptId;

    /**
     * 脚本名称
     */
    private String scriptName;

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
     * 开始时间
     */
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private LocalDateTime endTime;

    /**
     * 日志详情
     */
    private String logData;

    /**
     * 执行状态
     */
    private String executeState;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    private Long createBy;

}
