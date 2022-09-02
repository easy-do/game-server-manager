package game.server.manager.redis.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.util.Assert;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;


/**
 * Redis使用FastJson序列化
 *
 * @author yuzhanfeng
 */
public class FastJson2RedisSerializer<T> extends FastJsonRedisSerializer<T> {
    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    static {
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
    }

    @SuppressWarnings("unused")
    private ObjectMapper objectMapper = new ObjectMapper();

    public FastJson2RedisSerializer(Class type) {
        super(type);
    }

    public byte[] serializeObject(Object object){
        if (object == null) {
            return new byte[0];
        }
        return JSON.toJSONString(object, SerializerFeature.WriteClassName).getBytes(DEFAULT_CHARSET);
    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        Assert.notNull(objectMapper, "'objectMapper' must not be null");
        this.objectMapper = objectMapper;
    }

    protected JavaType getJavaType(Class<?> clazz) {
        return TypeFactory.defaultInstance().constructType(clazz);
    }
}
