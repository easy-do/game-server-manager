package plus.easydo.push.client.model.socket;

import lombok.Data;

/**
 * @author laoyu
 * @version 1.0
 * @description 游览器执行日志消息参数
 * @date 2022/11/21
 */
@Data
public class BrowserExecScriptLogMessage {

    private String logId;

    private String deviceId;

}
