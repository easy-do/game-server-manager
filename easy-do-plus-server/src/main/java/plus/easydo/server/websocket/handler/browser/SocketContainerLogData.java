package plus.easydo.server.websocket.handler.browser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author laoyu
 * @version 1.0
 * @date 2022/11/21
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SocketContainerLogData {


    private String dockerId;

    private String containerId;
}
