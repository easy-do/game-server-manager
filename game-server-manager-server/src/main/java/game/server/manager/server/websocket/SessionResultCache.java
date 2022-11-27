package game.server.manager.server.websocket;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import game.server.manager.common.mode.socket.ClientMessage;


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
     * 消息缓存 具有过期时间
     */
    public static final TimedCache<String, ClientMessage> MESSAGE_CACHE = CacheUtil.newTimedCache(10000);

    static {
        //启动定时任务，每1秒清理一次过期条目
        MESSAGE_CACHE.schedulePrune(1000);
    }

   public static ClientMessage getResultByMessageId(String messageId){
      return MESSAGE_CACHE.get(messageId,false);
   }

    public static void saveMessageById(String messageId, ClientMessage clientMessage){
         MESSAGE_CACHE.put(messageId,clientMessage);
    }

    public static void removeMessageById(String messageId){
        MESSAGE_CACHE.remove(messageId);
    }

}
