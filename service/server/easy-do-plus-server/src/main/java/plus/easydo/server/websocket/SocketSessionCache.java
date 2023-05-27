package plus.easydo.server.websocket;

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
     * sessionId对应客户端消息ID
     */
    public static final Map<String, String> CLIENT_SID_CLIENT_ID_CACHE = new ConcurrentHashMap<>();


    /**
     * 客户端消息id与游览器session关联关系
     */
    public static final Map<String,String> CLIENT_MSG_ID_BROWSER_SID_CACHE = new ConcurrentHashMap<>();


    /**
     * 游览器session与客户端messageId关联关系
     */
    public static final Map<String,String> BROWSER_SID_CLIENT_MSG_ID_CACHE = new ConcurrentHashMap<>();

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
     * @author laoyu
     * @date 2022/11/24
     */
    public static void saveClientIdAndSid(String clientId, String clientSessionId){
        CLIENT_ID_CLIENT_SID_CACHE.put(clientId, clientSessionId);
        CLIENT_SID_CLIENT_ID_CACHE.put(clientSessionId,clientId);
    }

    /**
     * 保存游览器session和客户端消息id的关联关系
     *
     * @param browserSessionId browserSessionId
     * @param clientMessageId clientMessageId
     * @author laoyu
     * @date 2022/11/24
     */
    public static void saveBrowserSIdAndClientMessageId(String browserSessionId, String clientMessageId){
        CLIENT_MSG_ID_BROWSER_SID_CACHE.put(clientMessageId, browserSessionId);
        BROWSER_SID_CLIENT_MSG_ID_CACHE.put(browserSessionId, clientMessageId);
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
     * 根据客户端messageId获取游览器sessionId
     *
     * @param clientMessageId clientMessageId
     * @return javax.websocket.Session
     * @author laoyu
     * @date 2022/11/24
     */
    public static Session getBrowserSessionByMessageId(String clientMessageId){
        String browserId = CLIENT_MSG_ID_BROWSER_SID_CACHE.get(clientMessageId);
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
     * 删除游览器session相关的缓存
     *
     * @param browserSessionId browserSessionId
     * @author laoyu
     * @date 2022/11/24
     */
    public static void removeBrowserBySessionId(String browserSessionId){
        //删除游览器session缓存
        SESSION_CACHE.remove(browserSessionId);
        String clientMessageId = BROWSER_SID_CLIENT_MSG_ID_CACHE.get(browserSessionId);
        if(Objects.nonNull(clientMessageId)){
            //删除客户端消息与游览器关联缓存
            CLIENT_MSG_ID_BROWSER_SID_CACHE.remove(clientMessageId);
        }
        //删除游览器与客户端消息关联缓存
        BROWSER_SID_CLIENT_MSG_ID_CACHE.remove(browserSessionId);
        String dockerId = BROWSER_SID_DOCKER_ID_CACHE.get(browserSessionId);
        if(Objects.nonNull(dockerId)){
            //删除docker和游览器session关联关系
            DOCKER_ID_BROWSER_SID_CACHE.remove(dockerId);
        }
        //删除游览器session和docker关联关系
        BROWSER_SID_DOCKER_ID_CACHE.remove(browserSessionId);
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
        String clientId = CLIENT_SID_CLIENT_ID_CACHE.get(clientSessionId);
        if(Objects.nonNull(clientId)){
            CLIENT_ID_CLIENT_SID_CACHE.remove(clientId);
        }
        CLIENT_SID_CLIENT_ID_CACHE.remove(clientSessionId);

    }

}
