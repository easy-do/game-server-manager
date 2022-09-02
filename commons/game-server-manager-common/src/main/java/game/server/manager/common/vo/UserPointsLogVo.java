package game.server.manager.common.vo;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户积分记录
 * @author yuzhanfeng
 * @TableName user_points_log
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPointsLogVo implements Serializable {
    /**
     * 自增主键
     */
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 分数
     */
    private Integer points;

    /**
     * 当前积分
     */
    private Integer currentPoints;

    /**
     * 描述
     */
    private String description;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private Date createTime;

    @Serial
    private static final long serialVersionUID = 1L;
}