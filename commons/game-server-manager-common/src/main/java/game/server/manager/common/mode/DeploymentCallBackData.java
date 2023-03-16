package game.server.manager.common.mode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author laoyu
 * @version 1.0
 * @date 2022/8/8
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeploymentCallBackData implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String deviceId;

    private String scriptId;

    private String type;

    private ExecuteLogModal executeLogModal;

    private boolean uninstall = false;

}
