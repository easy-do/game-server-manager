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
 * 常见问题
 * @author yuzhanfeng
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiscussionVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 正文
     */
    private String content;

    /**
     * 状态 0草稿 1提交 2审核通过
     */
    private Integer status;

    /**
     * 同意
     */
    private Long agree;

    /**
     * 反对
     */
    private Long oppose;

    /**
     * 描述
     */
    private String description;

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
     * 创建人
     */
    private Long createBy;

    /**
     * 更新人
     */
    private Long updateBy;

    /**
     * 创建用户名
     */
    private String createName;

    /**
     * 头像
     */
    private String avatarUrl;

}
