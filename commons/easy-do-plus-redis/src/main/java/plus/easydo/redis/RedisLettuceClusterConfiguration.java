package plus.easydo.redis;

import io.lettuce.core.cluster.ClusterClientOptions;
import io.lettuce.core.cluster.ClusterTopologyRefreshOptions;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;

import java.time.Duration;

/**
 * redis集群配置
 *
 * @author laoyu
 * @version 1.0
 * @date 2022/2/20
 */
@Configuration
@ConditionalOnProperty(value = {"spring.redis.lettuce.cluster.refresh.adaptive"}, havingValue = "true")
public class RedisLettuceClusterConfiguration {

    @Bean
    @Order(1)
    public RedisConnectionFactory newLettuceConnectionFactory(RedisProperties redisProperties, GenericObjectPoolConfig poolConfig) {
        ClusterTopologyRefreshOptions clusterTopologyRefreshOptions = ClusterTopologyRefreshOptions.builder().enablePeriodicRefresh(Duration.ofSeconds(60L)).enableAllAdaptiveRefreshTriggers().adaptiveRefreshTriggersTimeout(Duration.ofSeconds(30L)).refreshTriggersReconnectAttempts(5).build();
        ClusterClientOptions clusterClientOptions = ClusterClientOptions.builder().validateClusterNodeMembership(false).topologyRefreshOptions(clusterTopologyRefreshOptions).build();
        LettucePoolingClientConfiguration clientConfig = LettucePoolingClientConfiguration.builder().commandTimeout(redisProperties.getTimeout()).poolConfig(poolConfig).shutdownTimeout(Duration.ZERO).clientOptions(clusterClientOptions).build();
        RedisClusterConfiguration serverConfig = new RedisClusterConfiguration(redisProperties.getCluster().getNodes());
        if (redisProperties.getCluster().getMaxRedirects() != null) {
            serverConfig.setMaxRedirects(redisProperties.getCluster().getMaxRedirects());
        }

        if (redisProperties.getPassword() != null) {
            serverConfig.setPassword(RedisPassword.of(redisProperties.getPassword()));
        }

        return new LettuceConnectionFactory(serverConfig, clientConfig);
    }

    @Bean
    @Order(0)
    public GenericObjectPoolConfig poolConfig(RedisProperties redisProperties) {
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        RedisProperties.Pool properties = redisProperties.getLettuce().getPool();
        config.setMaxTotal(properties.getMaxActive());
        config.setMaxIdle(properties.getMaxIdle());
        config.setMinIdle(properties.getMinIdle());
        return config;
    }
}
