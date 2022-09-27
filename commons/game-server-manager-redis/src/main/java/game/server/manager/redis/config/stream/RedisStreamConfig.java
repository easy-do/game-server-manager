package game.server.manager.redis.config.stream;

import com.google.common.collect.Lists;
import io.lettuce.core.RedisBusyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.RedisSystemException;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.Consumer;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.connection.stream.PendingMessages;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StreamOperations;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author laoyu
 * @version 1.0
 * @date 2022/2/20
 */
@Configuration
@ConditionalOnProperty(value = {"system.redis.stream.enabled"},havingValue = "true")
public class RedisStreamConfig implements ApplicationContextAware {

    private static final Logger log = LoggerFactory.getLogger(RedisStreamConfig.class);

    @Autowired
    ServiceInstanceConfig instanceConfig;

    private ApplicationContext ctx;

    public RedisStreamConfig() {
    }

    @Bean
    public List<StreamListener> redisStreamListenerList() {
        ArrayList<StreamListener> listeners = Lists.newArrayList();
        Map<String, Object> beans = this.ctx.getBeansWithAnnotation(RedisStreamListener.class);
        beans.values().forEach((obj) -> {
            StreamListener<String, MapRecord<String, String, String>> listener = (StreamListener) obj;
            listeners.add(listener);
        });
        return listeners;
    }

    @Bean(
            destroyMethod = "stop"
    )
    @Order
    public StreamMessageListenerContainer streamMessageListenerContainer(RedisConnectionFactory connectionFactory, RedisTemplate redisTemplate) {
        StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String, MapRecord<String, String, String>> containerOptions = StreamMessageListenerContainer.StreamMessageListenerContainerOptions.builder().batchSize(10).pollTimeout(Duration.ofSeconds(2L)).build();
        StreamMessageListenerContainer<String, MapRecord<String, String, String>> container = StreamMessageListenerContainer.create(connectionFactory, containerOptions);
        Map<String, Object> beans = this.ctx.getBeansWithAnnotation(RedisStreamListener.class);
        beans.values().forEach((bean) -> {
            StreamListener<String, MapRecord<String, String, String>> listener = (StreamListener) bean;
            RedisStreamListener annotation = listener.getClass().getAnnotation(RedisStreamListener.class);
            StreamOperations streamOperations = redisTemplate.opsForStream();

            try {
                streamOperations.createGroup(annotation.topic(), annotation.group());
            } catch (RedisBusyException var12) {
                log.info("------------- Redis Stream  register topic:{} ,group:{} already exists ", annotation.topic(), annotation.group());
            } catch (RedisSystemException var13) {
                log.info("------------- Redis Stream register redis stream topic:{} ,group:{} already exists ", annotation.topic(), annotation.group());
            } catch (Exception var14) {
                var14.printStackTrace();
            }

            String consumerName = instanceConfig.getInstanceName();
            StreamOffset<String> offset = StreamOffset.create(annotation.topic(), ReadOffset.lastConsumed());
            Consumer consumer = Consumer.from(annotation.group(), consumerName);
            StreamMessageListenerContainer.StreamReadRequest<String> streamReadRequest = StreamMessageListenerContainer.StreamReadRequest.builder(offset).errorHandler((error) -> {
                log.error(error.getMessage());
            }).cancelOnError((e) -> false).consumer(consumer).autoAcknowledge(false).build();
            container.register(streamReadRequest, listener);
            log.info(">>>>>>>>>>>  Redis Stream  register topic:{}, group:{}, consumer:{} <<<<<<<<<<<<", annotation.topic(), annotation.group(), consumerName);
            PendingMessages pendingMessages = streamOperations.pending(annotation.topic(), consumer);
            pendingMessages.stream().forEach((message) -> {
                RecordId recordId = message.getId();
                Duration elapsedTimeSinceLastDelivery = message.getElapsedTimeSinceLastDelivery();
                long deliveryCount = message.getTotalDeliveryCount();
                log.warn("----Redis Stream  ReOpen消息，id={}, elapsedTimeSinceLastDelivery={}, deliveryCount={}", recordId, elapsedTimeSinceLastDelivery, deliveryCount);
                if (deliveryCount > 3L) {
                    streamOperations.acknowledge(annotation.topic(), consumer.getGroup(), recordId);
                    log.warn("----Redis Stream  acknowledge message={}", recordId);
                } else {
                    log.info("----Redis Stream  xClaim message={}", recordId);
                }

            });
        });
        container.start();
        return container;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }
}
