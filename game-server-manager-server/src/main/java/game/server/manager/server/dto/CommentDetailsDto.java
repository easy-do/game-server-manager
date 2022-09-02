package game.server.manager.server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import game.server.manager.common.vaild.Insert;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;

/**
 * 评论信息
 * @author yuzhanfeng
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDetailsDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
    private Long id;

    /**
     * 类型
     */
    private Integer type;

    /**
     * 业务主键
     */
    @NotNull(message = "未指定要评论的主题",groups = {Insert.class})
    private Long businessId;

    /**
     * 所属一级评论
     */
    private Long parentId;

    /**
     * 回复的评论id
     */
    private Long commentId;

    /**
     * 正文
     */
    @NotNull(message = "评论内容不能为空",groups = {Insert.class})
    @Size(min = 2,max = 80,message = "评论内容为10-80字")
    private String content;


}