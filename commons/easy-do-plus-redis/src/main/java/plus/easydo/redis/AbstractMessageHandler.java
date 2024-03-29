package plus.easydo.redis;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

/**
 * @author laoyu
 * @version 1.0
 * @date 2022/2/20
 */
public abstract class AbstractMessageHandler<T> implements MessageListener {
    @Resource
    private RedisTemplate<String, T> redisTemplate;

    public AbstractMessageHandler() {
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        T convertedMessage = this.extractMessage(message);
        String convertedChannel = redisTemplate.getStringSerializer().deserialize(pattern);
        this.handleMessage(convertedMessage, convertedChannel);
    }

    /**
     * 功能描述
     *
     * @param message message
     * @param topic topic
     * @author laoyu
     * @date 2022/9/27
     */
    public abstract void handleMessage(T message, String topic);

    private T extractMessage(Message message) {
        return (T) redisTemplate.getValueSerializer().deserialize(message.getBody());
    }
}
