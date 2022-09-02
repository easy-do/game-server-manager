package game.server.manager.uc.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


/**
 * @author laoyu
 * @version 1.0
 * @description 重置密码实体类封装
 * @date 2022/6/13
 */
@Data
public class RestPasswordModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long userId;

    private String password;

    private String emailCode;
}
