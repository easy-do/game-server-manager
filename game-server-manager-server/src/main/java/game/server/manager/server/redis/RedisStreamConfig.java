package game.server.manager.server.redis;



import com.google.common.collect.Maps;
import game.server.manager.redis.config.RedisStreamUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.stream.Consumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.data.redis.stream.Subscription;

import java.time.Duration;
import java.util.Map;

/**
 * @author laoyu
 * @version 1.0
 * @description redis stream 配置
 * @date 2022/7/2
 */
@Slf4j
@Configuration
public class RedisStreamConfig {

    private static final int subCount = 2;

    @Autowired
    private ApplicationDeployListenerMessage applicationDeployListenerMessage;

    @Autowired
    private RedisStreamUtils<Object> redisStreamUtils;

    @Bean
    public StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String, ?> streamMessageListenerContainerOptions(){
        return StreamMessageListenerContainer
                .StreamMessageListenerContainerOptions
                .builder()
                .pollTimeout(Duration.ofSeconds(1))
                .build();
    }

    @Bean
    public StreamMessageListenerContainer streamMessageListenerContainer(RedisConnectionFactory factory,
                                                                         StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String, ?> streamMessageListenerContainerOptions){
        StreamMessageListenerContainer listenerContainer = StreamMessageListenerContainer.create(factory,
                streamMessageListenerContainerOptions);
        listenerContainer.start();
        return listenerContainer;
    }


    /**
     * 当某个消息被ACK，PEL列表就会减少
     * 如果忘记确认（ACK），则PEL列表会不断增长占用内存
     * 如果服务器发生意外，重启连接后将再次收到PEL中的消息ID列表
     * @param streamMessageListenerContainer
     * @return Subscription
     */
    @Bean
    public Subscription subscription(StreamMessageListenerContainer streamMessageListenerContainer){
        String streamName = ApplicationDeployListenerMessage.DEFAULT_STREAM_NAME;
        String groupName = ApplicationDeployListenerMessage.DEFAULT_GROUP;
        Map<String,Object> map = Maps.newHashMapWithExpectedSize(1);
        map.put("message","initStream");
        redisStreamUtils.add(streamName,map);
        if(!redisStreamUtils.existGroup(streamName,groupName)){
            redisStreamUtils.createGroup(streamName,groupName);
        }
        for (int i = 0; i < subCount; i++) {
            log.info("注册stream消费者" + i);
            streamMessageListenerContainer.receive(
                    Consumer.from(groupName,ApplicationDeployListenerMessage.DEFAULT_NAME + i),
                    StreamOffset.create(streamName, ReadOffset.lastConsumed()),
                    applicationDeployListenerMessage
            );
        }
        return null;
    }

}
