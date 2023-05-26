package plus.easydo.event.model;

import plus.easydo.common.dto.AuthorizationConfigDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author laoyu
 * @version 1.0
 * @date 2022/7/12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorizationCodeEvent {

    private Long userId;

    private AuthorizationConfigDto authorizationConfigDto;

}
