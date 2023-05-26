package plus.easydo.server.websocket.handler.client;

import plus.easydo.common.mode.socket.ClientMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.websocket.Session;

/**
 * @author yuzhanfeng
 * @Date 2022/11/26 22:46
 * @Description 客户端消息处理服务参数封装
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientHandlerData {

    private Session session;

    private ClientMessage clientMessage;

}
