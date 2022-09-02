package game.server.manager.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户消息
 * @author yuzhanfeng
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserMessageDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

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

}