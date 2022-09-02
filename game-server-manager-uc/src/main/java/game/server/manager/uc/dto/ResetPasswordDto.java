package game.server.manager.uc.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author laoyu
 * @version 1.0
 * @date 2022/7/17
 */
@Data
public class ResetPasswordDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long userId;

    private String password;

    private Long updateUserId;
}
