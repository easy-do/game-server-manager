package game.server.manager.server.qo;

import game.server.manager.mybatis.plus.qo.MpBaseQo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author laoyu
 * @version 1.0
 * @date 2022/9/5
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDetailsQo extends MpBaseQo<CommentDetailsQo> {

    private Long discussionId;
}
