package game.server.manager.auth.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;


/**
 * @author laoyu
 * @version 1.0
 * @description 简易用户信息
 * @date 2023/3/11
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SimpleUserInfoVo  implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
    private Long id;

    /**
     * unionId
     */
    private String unionId;

    /**
     * 平台
     */
    private String platform;

    /**
     * 昵称
     */
    private String nickName;

}

