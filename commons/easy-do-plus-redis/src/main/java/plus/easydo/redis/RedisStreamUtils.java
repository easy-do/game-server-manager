package plus.easydo.redis;

import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.connection.stream.StreamInfo;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author laoyu
 * @version 1.0
 * @description redis stream 工具类
 * @date 2022/7/1
 */
@Component
public class RedisStreamUtils<T> {

    @Resource
    private RedisTemplate<String, T> redisTemplate;

    public RedisTemplate<String, T> getRedisTemplate() {
        return redisTemplate;
    }


    /**
     * 创建消费组
     * @param stream
     * @param groupName
     * @return
     */
    public String createGroup(String stream, String groupName){
        return redisTemplate.opsForStream().createGroup(stream, groupName);
    }

    /**
     * 获得stream的消费组
     * @param stream
     * @return
     */
    public StreamInfo.XInfoGroups groups(String stream){
        return redisTemplate.opsForStream().groups(stream);
    }

    /**
     * 消费组是否存在
     * @param stream
     * @param groupName
     * @return
     */
    public boolean existGroup(String stream, String groupName){
        StreamInfo.XInfoGroups xInfoGroups = redisTemplate.opsForStream().groups(stream);
        if(xInfoGroups.groupCount() == 0){
            return false;
        }
        return xInfoGroups.stream().anyMatch(xInfoGroup -> xInfoGroup.groupName().equals(groupName));
    }

    /**
     * 消费组信息
     * @param stream
     * @param groupName
     * @return
     */
    public StreamInfo.XInfoConsumers consumers(String stream, String groupName){
        return redisTemplate.opsForStream().consumers(stream, groupName);
    }

    /**
     * 确认已消费
     * @param stream
     * @param group
     * @param recordIds
     * @return
     */
    public Long ack(String stream, String group, String... recordIds){
        return redisTemplate.opsForStream().acknowledge(stream, group, recordIds);
    }

    /**
     * 追加消息
     * @param stream
     * @param value
     * @return
     */
    public String add(String stream, Map<String, Object> value){
        return redisTemplate.opsForStream().add(stream, value).getValue();
    }

    /**
     * 删除消息，这里的删除仅仅是设置了标志位，不影响消息总长度
     * 消息存储在stream的节点下，删除时仅对消息做删除标记，当一个节点下的所有条目都被标记为删除时，销毁节点
     * @param stream
     * @param recordIds
     * @return
     */
    public Long del(String stream, String... recordIds){
        return redisTemplate.opsForStream().delete(stream, recordIds);
    }

    /**
     * 消息长度
     * @param key
     * @return
     */
    public Long len(String key){
        return redisTemplate.opsForStream().size(key);
    }

    /**
     * 从开始读
     * @param stream
     * @return
     */
    public List<MapRecord<String, Object, Object>> read(String stream){
        return redisTemplate.opsForStream().read(StreamOffset.fromStart(stream));
    }

    /**
     * 从指定的ID开始读
     * @param stream
     * @param recordId
     * @return
     */
    public List<MapRecord<String, Object, Object>> read(String stream, String recordId){
        return redisTemplate.opsForStream().read(StreamOffset.from(MapRecord.create(stream, new HashMap<>(1)).withId(RecordId.of(recordId))));
    }

}
