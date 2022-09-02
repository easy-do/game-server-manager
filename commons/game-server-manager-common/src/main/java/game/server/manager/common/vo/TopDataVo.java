package game.server.manager.common.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author laoyu
 * @version 1.0
 * @description top数据
 * @date 2022/5/31
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TopDataVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long userCount;

    private Long onlineCount;

    private Long applicationCount;

    private Long deployCount;
}
