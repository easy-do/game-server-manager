package plus.easydo.common.vo;

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
 * 
 * @author yuzhanfeng
 * @TableName client_message
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientMessageVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 客户端id
     */
    private String clientId;

    /**
     * 消息类型
     */
    private Integer messageType;

    /**
     * 消息内容
     */
    private String message;

    /**
     * 状态 0发布 1接收 2消费
     */
    private Integer status;

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

}
