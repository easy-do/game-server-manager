package game.server.manager.common.mode.socket;

import lombok.Data;

/**
 * @author laoyu
 * @version 1.0
 * @description 游览器部署日志消息参数
 * @date 2022/11/21
 */
@Data
public class BrowserDeployLogMessage {

    private String logId;

    private String applicationId;

}
