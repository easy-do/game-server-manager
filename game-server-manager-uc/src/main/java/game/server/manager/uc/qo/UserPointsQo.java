package game.server.manager.uc.qo;

import game.server.manager.mybatis.plus.qo.MpBaseQo;
import game.server.manager.uc.entity.UserPointsLog;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author laoyu
 * @version 1.0
 * @date 2022/9/5
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserPointsQo extends MpBaseQo<UserPointsLog> {

    private Long userId;
}
