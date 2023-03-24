package game.server.manager.server.websocket.handler.client;

import com.alibaba.fastjson2.JSON;
import game.server.manager.common.constant.MessageTypeConstants;
import game.server.manager.common.enums.ServerMessageTypeEnum;
import game.server.manager.common.mode.ClientData;
import game.server.manager.common.mode.socket.ClientMessage;
import game.server.manager.server.service.ClientInfoService;
import game.server.manager.server.util.SessionUtils;
import game.server.manager.server.websocket.SocketSessionCache;
import game.server.manager.server.websocket.handler.AbstractHandlerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.websocket.Session;


/**
 * @author yuzhanfeng
 * @Date 2022/11/26 23:29
 * @Description 客户端心跳处理服务
 */
@Service(MessageTypeConstants.HEARTBEAT)
public class HeartbeatHandlerService implements AbstractHandlerService<ClientHandlerData> {

    @Resource
    private ClientInfoService clientInfoService;

    @Override
    public Void handler(ClientHandlerData clientHandlerData) {
        ClientMessage<String> clientMessage = clientHandlerData.getClientMessage();
        Session session = clientHandlerData.getSession();
        SocketSessionCache.saveSession(session);
        SocketSessionCache.saveClientIdAndSid(clientMessage.getClientId(),session.getId());
        SessionUtils.sendSimpleServerMessage(session,session.getId(),"success",ServerMessageTypeEnum.HEARTBEAT);
        ClientData clientData = JSON.parseObject(clientMessage.getData(),ClientData.class);
        clientInfoService.updateHeartbeat(clientData);
        return null;
    }

}
