package game.server.manager.redis.config.stream;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.RandomUtil;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

/**
 * @author laoyu
 * @version 1.0
 * @date 2022/2/20
 */
@Component
@ConditionalOnProperty(value = {"system.redis.stream.enabled"},havingValue = "true")
public class ServiceInstanceConfig implements InitializingBean, DisposableBean {

    @Autowired
    RedisTemplate redisTemplate;

    @Value("${spring.application.name}")
    private String applicationName;

    private String instanceName;

    private int maxSize;

    public ServiceInstanceConfig() {
    }

    @Override
    public void destroy() throws Exception {
        this.redisTemplate.opsForZSet().add(this.getKey(), this.instanceName, 2.0D);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.maxSize = 10;
        if (!this.redisTemplate.hasKey(this.getKey())) {
            for (int i = 0; i < this.maxSize; ++i) {
                this.redisTemplate.opsForZSet().add(this.getKey(), this.applicationName + "-" + i, 0.0D);
            }
        }

        Set<String> set = this.redisTemplate.opsForZSet().rangeByScore(this.getKey(), 2.0D, 2.0D);
        if (CollUtil.isEmpty(set)) {
            set = this.redisTemplate.opsForZSet().rangeByScore(this.getKey(), 0.0D, 0.0D);
        }

        Optional<String> optional = set.stream().findFirst();
        if (optional.isPresent()) {
            this.instanceName = optional.get();
        } else {
            this.instanceName = this.applicationName + "-" + RandomUtil.randomInt(0, this.maxSize);
        }

        this.redisTemplate.opsForZSet().add(this.getKey(), this.instanceName, 1.0D);
    }

    public String getInstanceName() {
        return this.instanceName;
    }

    private String getKey() {
        return this.applicationName + "-instances";
    }
}
