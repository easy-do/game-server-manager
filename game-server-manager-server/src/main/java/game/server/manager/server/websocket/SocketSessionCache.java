package game.server.manager.server.websocket;

import javax.websocket.Session;
import java.util.Map;
import java.util.Objects;
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
     * 客户端对应sessionId
     */
    public static final Map<String, String> CLIENT_SESSION_CACHE = new ConcurrentHashMap<>();

    /**
     * 客户端session与游览器session关联关系
     */
    public static final Map<String,String> CLIENT_SID_BROWSER_SID_CACHE = new ConcurrentHashMap<>();


    /**
     * 游览器session与客户端session关联关系
     */
    public static final Map<String,String> BROWSER_SID_CLIENT_SID_CACHE = new ConcurrentHashMap<>();

    /**
     * docker与游览器关联关系
     */
    public static final Map<String,String> DOCKER_ID_BROWSER_SID_CACHE = new ConcurrentHashMap<>();

    /**
     * 游览器与docker关联关系
     */
    public static final Map<String,String> BROWSER_SID_DOCKER_ID_CACHE = new ConcurrentHashMap<>();


    public static void saveBrowser(String dockerId,Session session){
        SESSION_CACHE.put(session.getId(), session);
        DOCKER_ID_BROWSER_SID_CACHE.put(dockerId,session.getId());
    }

    public static void saveClientSession(Session session){
        SESSION_CACHE.put(session.getId(), session);
    }

    public static void saveClientIdSession(String clientId,String sessionId){
        CLIENT_SESSION_CACHE.put(clientId, sessionId);
    }

    public static void saveBrowserSIdAndClientSId(String browserId, String clientId){
        CLIENT_SID_BROWSER_SID_CACHE.put(clientId, browserId);
        BROWSER_SID_CLIENT_SID_CACHE.put(browserId, clientId);
    }


    public static Session getBrowser(String browserId){
            return SESSION_CACHE.get(browserId);
    }

    public static Session getClient(String clientId){
        String clientSessionId = CLIENT_SESSION_CACHE.get(clientId);
        if (Objects.nonNull(clientSessionId)) {
            return SESSION_CACHE.get(clientSessionId);
        }
        return null;
    }
    public static Session getBrowserByClientSessionId(String clientId){
        String browserId = CLIENT_SID_BROWSER_SID_CACHE.get(clientId);
        if (Objects.nonNull(browserId)) {
            return SESSION_CACHE.get(browserId);
        }
        return null;
    }

    public static Session getBrowserByDockerId(String dockerId){
        String browserId = DOCKER_ID_BROWSER_SID_CACHE.get(dockerId);
        if (Objects.nonNull(browserId)) {
            return SESSION_CACHE.get(browserId);
        }
        return null;
    }

    public static Session getClientSessionByBrowserSessionId(String browserId){
        String clientId = CLIENT_SID_BROWSER_SID_CACHE.get(browserId);
        if (Objects.nonNull(clientId)) {
            return SESSION_CACHE.get(clientId);
        }
        return null;
    }

    public static void removeBrowser(String browserId){
        SESSION_CACHE.remove(browserId);
        BROWSER_SID_CLIENT_SID_CACHE.remove(browserId);
        String dockerId = BROWSER_SID_DOCKER_ID_CACHE.get(browserId);
        if(Objects.nonNull(dockerId)){
            DOCKER_ID_BROWSER_SID_CACHE.remove(dockerId);
            BROWSER_SID_DOCKER_ID_CACHE.remove(browserId);
        }
    }

    public static void removeClient(String clientId){
        SESSION_CACHE.remove(clientId);
        CLIENT_SID_BROWSER_SID_CACHE.remove(clientId);
    }

}
