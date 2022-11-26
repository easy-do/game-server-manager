package game.server.manager.client.websocket.handler;

import game.server.manager.common.constant.MessageTypeConstants;
import game.server.manager.common.mode.socket.ServerMessage;
import game.server.manager.handler.AbstractHandlerService;
import game.server.manager.handler.annotation.HandlerService;
import lombok.extern.slf4j.Slf4j;


/**
 * @author yuzhanfeng
 * @Date 2022/11/26 23:29
 * @Description 服务端心跳处理服务
 */
@Slf4j
@HandlerService(MessageTypeConstants.HEARTBEAT)
public class HeartbeatHandlerService extends AbstractHandlerService<ServerMessage, Void> {


    @Override
    public Void handler(ServerMessage serverMessage) {
        log.info("心跳成功");
        return null;
    }
}
