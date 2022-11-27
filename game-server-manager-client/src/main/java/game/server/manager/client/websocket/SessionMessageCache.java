package game.server.manager.client.websocket;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;

import java.util.Objects;

/**
 * @author yuzhanfeng
 * @Date 2022/11/27 19:49
 * @Description 通信缓存
 */
public class SessionMessageCache {

    /**
     * 被取消的同步请求 缓存30分钟
     */
    public static final TimedCache<String, String> CAN_SYNC_MESSAGE_CACHE = CacheUtil.newTimedCache(30*60*1000);

    static {
        //启动定时任务，每10秒清理一次过期条目
        CAN_SYNC_MESSAGE_CACHE.schedulePrune(10000);
    }

    public static void cancelSync(String messageId){
        CAN_SYNC_MESSAGE_CACHE.put(messageId,messageId);
    }

    public static boolean isCancel(String messageId){
        return Objects.nonNull(CAN_SYNC_MESSAGE_CACHE.get(messageId));
    }
}
