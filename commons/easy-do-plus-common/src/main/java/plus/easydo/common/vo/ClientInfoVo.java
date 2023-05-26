package plus.easydo.common.vo;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 客户端信息
 * @author yuzhanfeng
 * @TableName client_info
 */
@Data
public class ClientInfoVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
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
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private LocalDateTime updateTime;

    /**
     * 最后在线时间
     */
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private LocalDateTime lastUpTime;

}
