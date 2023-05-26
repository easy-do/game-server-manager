package plus.easydo.uc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;


/**
 * @author laoyu
 * @version 1.0
 * @description 登录实体类封装
 * @date 2022/6/13
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String userName;

    private String password;

    private String loginType;
}
