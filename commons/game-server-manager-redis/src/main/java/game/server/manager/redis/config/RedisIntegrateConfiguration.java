package game.server.manager.redis.config;

import cn.hutool.core.text.CharSequenceUtil;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.Arrays;

/**
 * redis整合配置
 * @author laoyu
 * @version 1.0
 * @date 2022/2/20
 */
@Configuration
@ConditionalOnClass({RedisConnectionFactory.class})
public class RedisIntegrateConfiguration extends CachingConfigurerSupport {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${system.redis.fastJson.package.white}")
    private String whitePackages;

    @Value("${system.redis.cache.timout:60}")
    private Integer cacheTimout;


    private static final String SPLIT = ",";

    @Resource
    FastJson2RedisSerializer fastJsonRedisSerializer;

    public RedisIntegrateConfiguration() {
    }

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        return new RedisCacheManager(
                RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory),
                /*默认策略，未配置的 key 会使用这个*/
                this.getRedisCacheConfigurationWithTtl(cacheTimout)
//                ,
                /*指定 key 策略*/
//                this.getRedisCacheConfigurationMap()
        );
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteClassName);
        fastJsonRedisSerializer.setFastJsonConfig(fastJsonConfig);
        if (CharSequenceUtil.isNotEmpty(this.whitePackages)) {
            String[] packages = whitePackages.split( SPLIT);
            if (packages.length > 0) {
                Arrays.stream(packages).forEach((str) -> ParserConfig.getGlobalInstance().addAccept(str));
            }

            ParserConfig.getGlobalInstance().addAccept("game.server.manager.");
        } else {
            ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        }

        this.logger.info("redis.FastJsonRedisSerializer.whitePackage...{}", this.whitePackages);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        template.setKeySerializer(stringRedisSerializer);
        template.setHashKeySerializer(stringRedisSerializer);
        template.setValueSerializer(fastJsonRedisSerializer);
        template.setHashValueSerializer(fastJsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    @SuppressWarnings(value = { "unchecked", "rawtypes", "deprecation" })
    public FastJson2RedisSerializer<Object> fastJsonRedisSerializer(){
        FastJson2RedisSerializer<Object> serializer = new FastJson2RedisSerializer<>(Object.class);
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        serializer.setObjectMapper(mapper);
        return serializer;
    }


//    private Map<String, RedisCacheConfiguration> getRedisCacheConfigurationMap() {
//        Map<String, RedisCacheConfiguration> redisCacheConfigurationMap = Maps.newHashMapWithExpectedSize(2);
//        //SsoCache和BasicDataCache进行过期时间配置
//        redisCacheConfigurationMap.put("messageCache", this.getRedisCacheConfigurationWithTtl(messageTCache));
//        //自定义设置缓存时间
//        redisCacheConfigurationMap.put("studentCache", this.getRedisCacheConfigurationWithTtl(studentCache ));
//        return redisCacheConfigurationMap;
//    }

    private RedisCacheConfiguration getRedisCacheConfigurationWithTtl(Integer seconds) {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        redisCacheConfiguration = redisCacheConfiguration.serializeValuesWith(
                RedisSerializationContext
                        .SerializationPair
                        .fromSerializer(fastJsonRedisSerializer)
        ).entryTtl(Duration.ofSeconds(seconds));
        return redisCacheConfiguration;
    }

}
