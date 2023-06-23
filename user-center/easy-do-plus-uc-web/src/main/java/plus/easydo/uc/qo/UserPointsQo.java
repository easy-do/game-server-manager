package plus.easydo.uc.qo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import plus.easydo.dao.qo.MpBaseQo;
import plus.easydo.uc.entity.UserPointsLog;

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
