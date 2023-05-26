package plus.easydo.redis;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.JSONWriter;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;


/**
 * Redis使用FastJson序列化
 *
 * @author yuzhanfeng
 */
public class FastJson2RedisSerializer implements RedisSerializer<Object> {
    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    @Override
    public byte[] serialize(Object object) throws SerializationException {
        if(Objects.isNull(object)){
            return new byte[0];
        }
        return JSON.toJSONString(object, JSONWriter.Feature.WriteClassName).getBytes(DEFAULT_CHARSET);
    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        if (bytes != null && bytes.length != 0) {
            return JSON.parseObject(bytes, Object.class, JSONReader.Feature.SupportAutoType);
        } else {
            return null;
        }
    }


}
