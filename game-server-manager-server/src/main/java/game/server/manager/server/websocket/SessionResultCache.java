package game.server.manager.server.websocket;

import game.server.manager.common.mode.socket.ClientMessage;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author laoyu
 * @version 1.0
 * @description 存储session相关数据
 * @date 2022/11/21
 */
public class SessionResultCache {

    private SessionResultCache() {
    }

    /**
     * session集合
     */
    public static final Map<String, ClientMessage> MESSAGE_CACHE = new ConcurrentHashMap<>();

   public static ClientMessage getResultByMessageId(String messageId){
      return MESSAGE_CACHE.get(messageId);
   }

    public static void saveMessageById(String messageId, ClientMessage clientMessage){
         MESSAGE_CACHE.put(messageId,clientMessage);
    }

    public static void removeMessageById(String messageId){
        MESSAGE_CACHE.remove(messageId);
    }

}
