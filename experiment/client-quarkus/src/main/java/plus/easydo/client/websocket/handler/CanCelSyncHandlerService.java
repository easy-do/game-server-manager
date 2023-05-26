package plus.easydo.client.websocket.handler;

import plus.easydo.client.contants.MessageTypeConstants;
import plus.easydo.client.model.socket.ServerMessage;
import plus.easydo.client.websocket.SessionMessageCache;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;


/**
 * @author yuzhanfeng
 * @Date 2022/11/26 23:29
 * @Description 取消当前同步请求
 */
@Slf4j
@ApplicationScoped()
@Named(MessageTypeConstants.CANCEL_SYNC)
public class CanCelSyncHandlerService implements AbstractHandlerService {


    @Override
    public Void handler(ServerMessage serverMessage) {
        log.info("服务端取消当前同步请求");
        String browserId = serverMessage.getMessageId();
        SessionMessageCache.cancelSync(browserId);
        return null;
    }
}
