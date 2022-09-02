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
public class AuthRoleMenuDto  implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotNull(message = "角色id不能为空")
    private Long roleId;

    @NotNull(message = "授权菜单不能为空")
    private List<Long> menuIds;
}
