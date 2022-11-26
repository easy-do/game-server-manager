package game.server.manager.server.websocket;

import cn.hutool.core.exceptions.ExceptionUtil;
import game.server.manager.common.exception.ExceptionFactory;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Objects;

/**
 * @author yuzhanfeng
 * @Date 2022/11/26 23:01
 * @Description session工具
 */
public class SessionUtils {

    /**
     * 发送消息
     *
     * @param message message
     * @author laoyu
     * @date 2022/9/1
     */
    public static void sendMessage(Session session, String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException exception) {
           throw  ExceptionFactory.bizException(ExceptionUtil.getMessage(exception));
        }
    }

    public static void close(Session session) {
        try {
            if(Objects.nonNull(session)){
                session.close();
            }
        } catch (IOException exception) {
            throw  ExceptionFactory.bizException(ExceptionUtil.getMessage(exception));
        }
    }
}
