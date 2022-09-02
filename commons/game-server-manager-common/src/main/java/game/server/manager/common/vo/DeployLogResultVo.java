package game.server.manager.common.vo;

import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;

/**
 * @author laoyu
 * @version 1.0
 * @description 部署日志返回结果
 * @date 2022/7/2
 */
@Data
@Builder
public class DeployLogResultVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private boolean isFinish;

    private Collection<Object> logs;
}
