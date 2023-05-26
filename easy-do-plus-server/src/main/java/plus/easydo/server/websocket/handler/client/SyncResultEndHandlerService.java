package plus.easydo.server.websocket.handler.client;

import plus.easydo.common.constant.MessageTypeConstants;
import plus.easydo.common.enums.ServerMessageTypeEnum;
import plus.easydo.common.mode.socket.ClientMessage;
import plus.easydo.server.util.server.SessionUtils;
import plus.easydo.server.websocket.SocketSessionCache;
import plus.easydo.server.websocket.handler.AbstractHandlerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.websocket.Session;

/**
 * @author yuzhanfeng
 * @Date 2022/11/26 23:29
 * @Description 客户端返回的连续数据结束时的处理服务
 */
@Slf4j
@Service(MessageTypeConstants.SYNC_RESULT_END)
public class SyncResultEndHandlerService implements AbstractHandlerService<ClientHandlerData> {

    @Override
    public Void handler(ClientHandlerData clientHandlerData) {
        ClientMessage clientMessage = clientHandlerData.getClientMessage();
        //寻找游览器session
        Session browserSession = SocketSessionCache.getBrowserSessionByMessageId(clientMessage.getMessageId());
        //向游览器转发消息
        assert browserSession != null;
        SessionUtils.sendSimpleServerMessage(browserSession,browserSession.getId(),clientMessage.getData(),ServerMessageTypeEnum.SYNC_RESULT_END);
        SessionUtils.close(browserSession);
        return null;
    }
}
