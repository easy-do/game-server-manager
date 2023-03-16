package game.server.manager.common.mode;

import game.server.manager.common.vo.AppInfoVo;
import game.server.manager.common.vo.ScriptDataVo;
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

    private AppInfoVo appInfo;

    private ScriptDataVo appScript;

    private List<ScriptDataVo> basicScript;
}
