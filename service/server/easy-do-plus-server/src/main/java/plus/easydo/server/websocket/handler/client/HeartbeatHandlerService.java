package plus.easydo.server.websocket.handler.client;

import com.alibaba.fastjson2.JSON;
import plus.easydo.common.constant.MessageTypeConstants;
import plus.easydo.common.enums.ServerMessageTypeEnum;
import plus.easydo.common.mode.ClientData;
import plus.easydo.common.mode.socket.ClientMessage;
import plus.easydo.server.util.server.SessionUtils;
import plus.easydo.server.service.ClientInfoService;
import plus.easydo.server.websocket.SocketSessionCache;
import plus.easydo.server.websocket.handler.AbstractHandlerService;
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
        ClientMessage clientMessage = clientHandlerData.getClientMessage();
        Session session = clientHandlerData.getSession();
        SocketSessionCache.saveSession(session);
        SocketSessionCache.saveClientIdAndSid(clientMessage.getClientId(),session.getId());
        SessionUtils.sendSimpleServerMessage(session,session.getId(),"success",ServerMessageTypeEnum.HEARTBEAT);
        ClientData clientData = JSON.parseObject(clientMessage.getData(),ClientData.class);
        clientInfoService.updateHeartbeat(clientData);
        return null;
    }

}
