package game.server.manager.client.websocket.handler;

import game.server.manager.client.contants.MessageTypeConstants;
import game.server.manager.client.model.socket.ServerMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


/**
 * @author yuzhanfeng
 * @Date 2022/11/26 23:29
 * @Description 服务端心跳处理服务
 */
@Slf4j
@Service(MessageTypeConstants.ERROR)
public class ServerErrorHandlerService implements AbstractHandlerService {


    @Override
    public Void handler(ServerMessage serverMessage) {
        log.warn("server error {}", serverMessage);
        return null;
    }
}