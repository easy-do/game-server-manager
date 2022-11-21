package game.server.manager.server.websocket;

import javax.websocket.Session;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author laoyu
 * @version 1.0
 * @description 存储session相关数据
 * @date 2022/11/21
 */
public class SocketSessionCache {

    private SocketSessionCache() {
    }

    /**
     * session集合
     */
    public static final Map<String, Session> SESSION_CACHE = new ConcurrentHashMap<>();

    /**
     * 客户端对应session集合
     */
    public static final Map<String,Session> CLIENT_SESSION_CACHE = new ConcurrentHashMap<>();
    

    /**
     * session对应客户端集合
     */
    public static final Map<String,String> SESSION_CLIENT_CACHE = new ConcurrentHashMap<>();
    
}
