package plus.easydo.server.qo.server;

import plus.easydo.dao.qo.MpBaseQo;
import plus.easydo.server.entity.CommentDetails;
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
public class CommentDetailsQo extends MpBaseQo<CommentDetails> {

    private Long discussionId;
}
