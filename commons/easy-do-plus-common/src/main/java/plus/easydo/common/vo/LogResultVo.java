package plus.easydo.common.vo;

import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;

/**
 * @author laoyu
 * @version 1.0
 * @description 日志返回结果
 * @date 2022/7/2
 */
@Data
@Builder
public class LogResultVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private boolean isFinish;

    private Collection<String> logs;
}
