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
     * 客户端ID对应sessionId
     */
    public static final Map<String, String> CLIENT_ID_CLIENT_SID_CACHE = new ConcurrentHashMap<>();

    /**
     * sessionId对应客户端ID
     */
    public static final Map<String, String> CLIENT_SID_CLIENT_ID_CACHE = new ConcurrentHashMap<>();

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


    /**
     * 保存游览器session 以及dockerId和游览器session的映射关系
     *
     * @param dockerId dockerId
     * @param session session
     * @author laoyu
     * @date 2022/11/24
     */
    public static void saveBrowserSession(String dockerId, Session session){
        SESSION_CACHE.put(session.getId(), session);
        DOCKER_ID_BROWSER_SID_CACHE.put(dockerId,session.getId());
    }

    /**
     * 保存session
     *
     * @param session session
     * @author laoyu
     * @date 2022/11/24
     */
    public static void saveSession(Session session){
        SESSION_CACHE.put(session.getId(), session);
    }

    /**
     * 保存客户端id和客户端session的关联关系
     *
     * @param clientId clientId
     * @param clientSessionId clientSessionId
     * @return void
     * @author laoyu
     * @date 2022/11/24
     */
    public static void saveClientIdSession(String clientId,String clientSessionId){
        CLIENT_ID_CLIENT_SID_CACHE.put(clientId, clientSessionId);
        CLIENT_SID_CLIENT_ID_CACHE.put(clientSessionId,clientId);
    }

    /**
     * 保存游览器session和客户端session的关联关系
     *
     * @param browserSessionId browserSessionId
     * @param clientSessionId clientSessionId
     * @author laoyu
     * @date 2022/11/24
     */
    public static void saveBrowserSIdAndClientSId(String browserSessionId, String clientSessionId){
        CLIENT_SID_BROWSER_SID_CACHE.put(clientSessionId, browserSessionId);
        BROWSER_SID_CLIENT_SID_CACHE.put(browserSessionId, clientSessionId);
    }


    /**
     * 根据sessionId获得游览器session
     *
     * @param browserSessionId browserSessionId
     * @return javax.websocket.Session
     * @author laoyu
     * @date 2022/11/24
     */
    public static Session getBrowserBySessionId(String browserSessionId){
            return SESSION_CACHE.get(browserSessionId);
    }

    /**
     * 根据客户端id获取客户端session
     *
     * @param clientId clientId
     * @return javax.websocket.Session
     * @author laoyu
     * @date 2022/11/24
     */
    public static Session getClientByClientId(String clientId){
        String clientSessionId = CLIENT_ID_CLIENT_SID_CACHE.get(clientId);
        if (Objects.nonNull(clientSessionId)) {
            return SESSION_CACHE.get(clientSessionId);
        }
        return null;
    }

    /**
     * 根据客户端sessionId获取游览器sessionId
     *
     * @param clientSessionId clientSessionId
     * @return javax.websocket.Session
     * @author laoyu
     * @date 2022/11/24
     */
    public static Session getBrowserSessionByClientSessionId(String clientSessionId){
        String browserId = CLIENT_SID_BROWSER_SID_CACHE.get(clientSessionId);
        if (Objects.nonNull(browserId)) {
            return SESSION_CACHE.get(browserId);
        }
        return null;
    }

    /**
     * 根据dockerId获取游览器session
     *
     * @param dockerId dockerId
     * @return javax.websocket.Session
     * @author laoyu
     * @date 2022/11/24
     */
    public static Session getBrowserByDockerId(String dockerId){
        String browserId = DOCKER_ID_BROWSER_SID_CACHE.get(dockerId);
        if (Objects.nonNull(browserId)) {
            return SESSION_CACHE.get(browserId);
        }
        return null;
    }

    /**
     * 根据游览器sessionId获取客户端session
     *
     * @param browserSessionId browserSessionId
     * @return javax.websocket.Session
     * @author laoyu
     * @date 2022/11/24
     */
    public static Session getClientSessionByBrowserSessionId(String browserSessionId){
        String clientId = CLIENT_SID_BROWSER_SID_CACHE.get(browserSessionId);
        if (Objects.nonNull(clientId)) {
            return SESSION_CACHE.get(clientId);
        }
        return null;
    }

    /**
     * 删除游览器session相关的缓存
     *
     * @param browserSessionId browserSessionId
     * @author laoyu
     * @date 2022/11/24
     */
    public static void removeBrowserBySessionId(String browserSessionId){
        SESSION_CACHE.remove(browserSessionId);
        BROWSER_SID_CLIENT_SID_CACHE.remove(browserSessionId);
        String dockerId = BROWSER_SID_DOCKER_ID_CACHE.get(browserSessionId);
        if(Objects.nonNull(dockerId)){
            DOCKER_ID_BROWSER_SID_CACHE.remove(dockerId);
            BROWSER_SID_DOCKER_ID_CACHE.remove(browserSessionId);
        }
    }

    /**
     * 删除客户端session相关的缓存
     *
     * @param clientSessionId clientSessionId
     * @author laoyu
     * @date 2022/11/24
     */
    public static void removeClientBySessionId(String clientSessionId){
        SESSION_CACHE.remove(clientSessionId);
        CLIENT_SID_BROWSER_SID_CACHE.remove(clientSessionId);
        String clientId = CLIENT_SID_CLIENT_ID_CACHE.get(clientSessionId);
        if(Objects.nonNull(clientId)){
            CLIENT_SID_CLIENT_ID_CACHE.remove(clientSessionId);
            CLIENT_ID_CLIENT_SID_CACHE.remove(clientId);
        }

    }

}
