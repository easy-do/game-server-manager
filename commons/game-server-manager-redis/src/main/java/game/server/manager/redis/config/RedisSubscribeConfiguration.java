package game.server.manager.redis.config;

import cn.hutool.extra.spring.SpringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import java.util.Map;
import java.util.Objects;

/**
 * redis订阅配置
 *
 * @author laoyu
 * @version 1.0
 * @date 2022/2/20
 */
@Configuration
@ConditionalOnClass({RedisTemplate.class})
@ConditionalOnProperty(name = {"system.redis.subscribe.enable"},havingValue = "true")
@ConfigurationProperties(
        prefix = "system.redis.subscribe"
)
public class RedisSubscribeConfiguration extends CachingConfigurerSupport {

    private static final Logger log = LoggerFactory.getLogger(RedisSubscribeConfiguration.class);

    private int taskNum;

    private Map<String, String> topics;

    @Bean
    public SimpleAsyncTaskExecutor eventAsyncTaskExecutor() {
        SimpleAsyncTaskExecutor taskExecutor = new SimpleAsyncTaskExecutor();
        taskExecutor.setConcurrencyLimit(this.taskNum);
        taskExecutor.setDaemon(true);
        taskExecutor.setThreadNamePrefix("RedisSubscribeListener");
        return taskExecutor;
    }

    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory, SimpleAsyncTaskExecutor simpleAsyncTaskExecutor) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setTaskExecutor(simpleAsyncTaskExecutor);
        log.info("taskNum:{}", taskNum);
        log.info("topic:{}", topics);
        if (Objects.nonNull(topics) && !topics.isEmpty()) {
            this.topics.forEach((key, value) -> {
                try {
                    AbstractMessageHandler listener = SpringUtil.getBean(value);
                    container.addMessageListener(listener, new PatternTopic(key));
                } catch (Exception var4) {
                    log.error("redis.subscribe.topic:" + key + "  value:" + value + " occur error", var4);
                }

            });
        }
        return container;
    }

    public int getTaskNum() {
        return this.taskNum;
    }

    public void setTaskNum(final int taskNum) {
        this.taskNum = taskNum;
    }

    public Map<String, String> getTopics() {
        return this.topics;
    }

    public void setTopics(final Map<String, String> topics) {
        this.topics = topics;
    }

}
