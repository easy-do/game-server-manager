package game.server.manager.common.mode.socket;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author laoyu
 * @version 1.0
 * @description 游览器消息
 * @date 2022/11/21
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BrowserMessage {

    private String clientId;

    private String type;

    private String secret;

    private String jsonData;
}
