package game.server.manager.client.model.socket;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author laoyu
 * @version 1.0
 * @description 客户端消息
 * @date 2022/11/22
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientMessage {

    private String clientId;

    private String messageId;

    private String type;

    private String data;

    private boolean success;

}
