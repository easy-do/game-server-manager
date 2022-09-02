package game.server.manager.uc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @author laoyu
 * @version 1.0
 * @date 2022/7/29
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserPointsOperationDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotNull(message = "用户不能为空")
    private List<Long> userId;

    @NotNull(message = "积分必传")
    private Long points;

    @NotNull(message = "描述必传")
    private String description;
}
