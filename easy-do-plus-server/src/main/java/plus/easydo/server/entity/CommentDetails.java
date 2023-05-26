package plus.easydo.server.entity;

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
 * 评论信息
 * @author yuzhanfeng
 * @TableName comment_details
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName(value ="comment_details")
public class CommentDetails implements Serializable {

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
    @TableId(type = IdType.AUTO)
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
    @TableField(fill = FieldFill.INSERT)
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
     * 删除标记
     */
    @TableLogic
    private Integer delFlag;

}
