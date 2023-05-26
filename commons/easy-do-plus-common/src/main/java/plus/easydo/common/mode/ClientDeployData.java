package plus.easydo.common.mode;

import plus.easydo.common.vo.ScriptDataVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @author laoyu
 * @version 1.0
 * @date 2022/8/7
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientDeployData implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private ScriptDataVo appScript;

    private List<ScriptDataVo> basicScript;
}
