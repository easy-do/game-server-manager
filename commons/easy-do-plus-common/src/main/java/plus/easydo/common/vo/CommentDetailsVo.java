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
import java.util.List;

/**
 * 评论信息
 * @author yuzhanfeng
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDetailsVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
    private Long id;

    /**
     * 业务主键
     */
    private Long businessId;

    /**
     * 回复的评论id
     */
    private Long commentId;

    /**
     * 所属一级评论id
     */
    private Long parentId;

    /**
     * 类型
     */
    private Integer type;

    /**
     * 正文
     */
    private String content;

    /**
     * 同意
     */
    private Long agree;

    /**
     * 反对
     */
    private Long oppose;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private LocalDateTime createTime;

    /**
     * 回复用户
     */
    private Long userId;

    /**
     * 回复用户名
     */
    private String userName;

    /**
     * 被回复的用户
     */
    private Long toUserId;

    /**
     * 被回复的用户名
     */
    private String toUserName;

    /**
     * 回复用户头像
     */
    private String userAvatar;

    /**
     * 子评论
     */
    private List<CommentDetailsVo> children;

}
