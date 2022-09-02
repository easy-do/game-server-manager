package game.server.manager.uc.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @author laoyu
 * @version 1.0
 * @date 2022/7/20
 */
@Data
public class AuthRoleDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotNull(message = "用户id不能为空")
    private Long userId;

    @NotNull(message = "角色id不能为空")
    private List<Long> roleIds;
}
