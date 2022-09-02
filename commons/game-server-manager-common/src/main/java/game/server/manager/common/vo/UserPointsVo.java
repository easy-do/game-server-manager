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
 * 用户积分记录
 * @author yuzhanfeng
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPointsVo implements Serializable {
    /**
     * 自增主键
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 分数
     */
    private Long points;

    /**
     * 当前积分
     */
    private Long currentPoints;

    /**
     * 描述
     */
    private String description;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private LocalDateTime createTime;

    @Serial
    private static final long serialVersionUID = 1L;
}