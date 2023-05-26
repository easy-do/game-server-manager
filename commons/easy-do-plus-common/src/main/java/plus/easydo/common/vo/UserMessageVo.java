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
 * 用户消息
 * @author yuzhanfeng
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserMessageVo implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 标题
     */
    private String title;

    /**
     * 消息类型
     */
    private Integer type;

    /**
     * 状态：0未读，1已读
     */
    private Integer status;

    /**
     * 消息详情
     */
    private String content;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private LocalDateTime createTime;

    @Serial
    private static final long serialVersionUID = 1L;
}
