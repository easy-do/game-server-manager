package game.server.manager.redis.config;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * spring redis 工具类
 *
 * @author ruoyi
 **/
@Component
public class RedisUtils<T> {

    private static final Logger log = LoggerFactory.getLogger(RedisUtils.class);

    @Resource
    private RedisTemplate<String, T> redisTemplate;

    private static final Long SUCCESS = 1L;


    public RedisTemplate<String, T> getRedisTemplate() {
        return redisTemplate;
    }

    public void set(String key, T value) {
        try {
            this.redisTemplate.opsForValue().set(key, value);
        } catch (Exception var7) {
            log.error("set error", var7);
        } finally {
            RedisConnectionUtils.unbindConnection( Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

    }

    public void set(String key, T value, long timeout, TimeUnit unit) {
        try {
            this.redisTemplate.opsForValue().set(key, value, timeout, unit);
        } catch (Exception var10) {
            log.error("set error", var10);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

    }

    public Boolean delete(String key) {
        try {
            return this.redisTemplate.delete(key);
        } catch (Exception var6) {
            log.error("delete error", var6);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return false;
    }

    public void delete(Collection<String> keys) {
        try {
            this.redisTemplate.delete(keys);
        } catch (Exception var6) {
            log.error("delete error", var6);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

    }

    public Boolean hasKey(String key) {
        try {
            return this.redisTemplate.hasKey(key);
        } catch (Exception var6) {
            log.error("hasKey error", var6);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return false;
    }

    public Boolean expire(String key, long timeout, TimeUnit unit) {
        try {
            return this.redisTemplate.expire(key, timeout, unit);
        } catch (Exception var9) {
            log.error("expire error", var9);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return false;
    }

    public Boolean expireAt(String key, Date date) {
        try {
            return this.redisTemplate.expireAt(key, date);
        } catch (Exception var7) {
            log.error("expireAt error", var7);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return false;
    }

    public Long getExpire(String key) {
        try {
            return this.redisTemplate.getExpire(key);
        } catch (Exception var6) {
            log.error("getExpire error", var6);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return 0L;
    }

    public Long getExpire(String key, TimeUnit timeUnit) {
        try {
            return this.redisTemplate.getExpire(key, timeUnit);
        } catch (Exception var7) {
            log.error("getExpire error", var7);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return 0L;
    }

    public Set<String> keys(String pattern) {
        try {
            return this.redisTemplate.keys(pattern);
        } catch (Exception var6) {
            log.error("keys error", var6);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return null;
    }

    public Boolean persist(String key) {
        try {
            return this.redisTemplate.persist(key);
        } catch (Exception var6) {
            log.error("persist error", var6);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return false;
    }

    public boolean setIfAbsent(String key, T value, long timeout, TimeUnit unit) {
        try {
            boolean result = this.redisTemplate.opsForValue().setIfAbsent(key, value);
            if (result) {
                this.redisTemplate.expire(key, timeout, unit);
            }

            return result;
        } catch (Exception var11) {
            log.error("setIfAbsent error", var11);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return false;
    }

    public boolean getLock(String key, T value, int expireTime) {
        try {
            String script = "if redis.call('setNX',KEYS[1],ARGV[1]) == 1 then if redis.call('get',KEYS[1])==ARGV[1] then return redis.call('expire',KEYS[1],ARGV[2]) else return 0 end end";
            RedisScript<Long> redisScript = new DefaultRedisScript(script, Long.class);
            Long result = this.redisTemplate.execute(redisScript, Lists.newArrayList(new String[]{key}), new Object[]{value, expireTime});
            return SUCCESS.equals(result);
        } catch (Exception var11) {
            log.error("getLock error", var11);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return false;
    }

    public boolean releaseLock(String key, T value) {
        try {
            String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
            RedisScript<Long> redisScript = new DefaultRedisScript(script, Long.class);
            Long result = this.redisTemplate.execute(redisScript, Lists.newArrayList(new String[]{key}), new Object[]{value});
            return SUCCESS.equals(result);
        } catch (Exception var10) {
            log.error("releaseLock error", var10);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return false;
    }

    public long increment(String key, long delta) {
        try {
            return this.redisTemplate.opsForValue().increment(key, delta);
        } catch (Exception var9) {
            log.error("increment error", var9);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return 0L;
    }

    public long increment(String key, long delta, long timeout, TimeUnit unit) {
        try {
            long result = this.redisTemplate.opsForValue().increment(key, delta);
            if (result == delta) {
                this.redisTemplate.expire(key, timeout, unit);
            }

            return result;
        } catch (Exception var14) {
            log.error("increment error", var14);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return 0L;
    }

    public Integer append(String key, String value) {
        try {
            return this.redisTemplate.opsForValue().append(key, value);
        } catch (Exception var7) {
            log.error("append error", var7);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return 0;
    }

    public Double incrementByFloat(String key, double increment) {
        try {
            return this.redisTemplate.opsForValue().increment(key, increment);
        } catch (Exception var8) {
            log.error("incrementByFloat error", var8);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return null;
    }

    public void multiSet(Map<String, T> map) {
        try {
            this.redisTemplate.opsForValue().multiSet(map);
        } catch (Exception var6) {
            log.error("multiSet error", var6);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

    }

    public void multiSet(Map<String, T> map, long timeout, TimeUnit unit) {
        try {
            this.redisTemplate.opsForValue().multiSet(map);

            for (String key : map.keySet()) {
                this.redisTemplate.expire(key, timeout, unit);
            }
        } catch (Exception var10) {
            log.error("multiSet error", var10);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

    }

    public T get(String key) {
        try {
            return this.redisTemplate.opsForValue().get(key);
        } catch (Exception var6) {
            log.error("get error", var6);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return null;
    }

    public void addByBloomFilter(BloomFilterHelper<T> bloomFilterHelper, String key, T value) {
        try {
            Preconditions.checkArgument(bloomFilterHelper != null, "bloomFilterHelper不能为空");
            int[] offset = bloomFilterHelper.murmurHashOffset(value);
            int length = offset.length;

            for(int i = 0; i < length; ++i) {
                int index = offset[i];
                this.redisTemplate.opsForValue().setBit(key, index, true);
            }
        } catch (Exception var12) {
            log.error("addByBloomFilter error", var12);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

    }

    public boolean includeByBloomFilter(BloomFilterHelper<T> bloomFilterHelper, String key, T value) {
        try {
            Preconditions.checkArgument(bloomFilterHelper != null, "bloomFilterHelper不能为空");
            int[] offset = bloomFilterHelper.murmurHashOffset(value);
            int length = offset.length;

            for(int i = 0; i < length; ++i) {
                int index = offset[i];
                if (Boolean.FALSE.equals(this.redisTemplate.opsForValue().getBit(key, index))) {
                    return false;
                }
            }

            return true;
        } catch (Exception var13) {
            log.error("includeByBloomFilter error", var13);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return false;
    }

    public T hGet(String key, String field) {
        try {
            return (T) this.redisTemplate.opsForHash().get(key, field);
        } catch (Exception var7) {
            log.error("hGet error", var7);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return null;
    }

    public <K, T> Map<K, T> hGetAll(String key) {
        try {
            return (Map<K, T>) this.redisTemplate.opsForHash().entries(key);
        } catch (Exception var6) {
            log.error("hGetAll error", var6);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return null;
    }

    public List<T> hMultiGet(String key, Collection<Object> fields) {
        try {
            return (List) this.redisTemplate.opsForHash().multiGet(key, fields);
        } catch (Exception var7) {
            log.error("hMultiGet error", var7);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return null;
    }

    public void hPut(String key, String hashKey, T value) {
        try {
            this.redisTemplate.opsForHash().put(key, hashKey, value);
        } catch (Exception var8) {
            log.error("hPut error", var8);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

    }

    public void hPutAll(String key, Map<String, T> maps) {
        try {
            this.redisTemplate.opsForHash().putAll(key, maps);
        } catch (Exception var7) {
            log.error("hPutAll error", var7);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

    }

    public Boolean hPutIfAbsent(String key, String hashKey, T value) {
        try {
            return this.redisTemplate.opsForHash().putIfAbsent(key, hashKey, value);
        } catch (Exception var8) {
            log.error("hPutIfAbsent error", var8);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return false;
    }

    public Long hDelete(String key, String... fields) {
        try {
            return this.redisTemplate.opsForHash().delete(key, fields);
        } catch (Exception var7) {
            log.error("hDelete error", var7);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return 0L;
    }

    public boolean hExists(String key, String field) {
        try {
            return this.redisTemplate.opsForHash().hasKey(key, field);
        } catch (Exception var7) {
            log.error("hExists error", var7);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return false;
    }

    public Long hIncrementBy(String key, String field, long increment) {
        try {
            return this.redisTemplate.opsForHash().increment(key, field, increment);
        } catch (Exception var9) {
            log.error("hIncrementBy error", var9);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return 0L;
    }

    public Double hIncrementByFloat(String key, String field, double delta) {
        try {
            return this.redisTemplate.opsForHash().increment(key, field, delta);
        } catch (Exception var9) {
            log.error("hIncrementByFloat error", var9);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return null;
    }

    public Set<T> hKeys(String key) {
        try {
            return (Set) this.redisTemplate.opsForHash().keys(key);
        } catch (Exception var6) {
            log.error("hKeys error", var6);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return null;
    }

    public Long hSize(String key) {
        try {
            return this.redisTemplate.opsForHash().size(key);
        } catch (Exception var6) {
            log.error("hSize error", var6);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return null;
    }

    public List<T> hValues(String key) {
        try {
            return (List) this.redisTemplate.opsForHash().values(key);
        } catch (Exception var6) {
            log.error("hSize error", var6);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return null;
    }

    public Cursor<Map.Entry<Object, Object>> hScan(String key, ScanOptions options) {
        try {
            return this.redisTemplate.opsForHash().scan(key, options);
        } catch (Exception var7) {
            log.error("hScan error", var7);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return null;
    }

    public long size(String key) {
        try {
            return this.redisTemplate.opsForList().size(key);
        } catch (Exception var7) {
            log.error("size error", var7);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return 0L;
    }

    public T rightPop(String key) {
        try {
            return this.redisTemplate.opsForList().rightPop(key);
        } catch (Exception var6) {
            log.error("rightPop error", var6);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return null;
    }

    public T lIndex(String key, long index) {
        try {
            return this.redisTemplate.opsForList().index(key, index);
        } catch (Exception var8) {
            log.error("lIndex error", var8);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return null;
    }

    public List<T> lRange(String key, long start, long end) {
        try {
            return this.redisTemplate.opsForList().range(key, start, end);
        } catch (Exception var10) {
            log.error("lRange error", var10);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return null;
    }

    public Long lLeftPush(String key, T value) {
        try {
            return this.redisTemplate.opsForList().leftPush(key, value);
        } catch (Exception var7) {
            log.error("lLeftPush error", var7);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return null;
    }

    public Long lLeftPushAll(String key, T... value) {
        try {
            return this.redisTemplate.opsForList().leftPushAll(key, value);
        } catch (Exception var7) {
            log.error("lLeftPushAll error", var7);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return null;
    }

    public Long lLeftPushIfPresent(String key, T value) {
        try {
            return this.redisTemplate.opsForList().leftPushIfPresent(key, value);
        } catch (Exception var7) {
            log.error("lLeftPushIfPresent error", var7);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return null;
    }

    public Long lLeftPush(String key, T pivot, T value) {
        try {
            return this.redisTemplate.opsForList().leftPush(key, pivot, value);
        } catch (Exception var8) {
            log.error("lLeftPush error", var8);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return null;
    }

    public Long lRightPush(String key, T value) {
        try {
            return this.redisTemplate.opsForList().rightPush(key, value);
        } catch (Exception var7) {
            log.error("lRightPush error", var7);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return null;
    }

    public Long lRightPushAll(String key, T... value) {
        try {
            return this.redisTemplate.opsForList().rightPushAll(key, value);
        } catch (Exception var7) {
            log.error("lRightPushAll error", var7);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return null;
    }

    public Long lRightPushAll(String key, Collection<T> value) {
        try {
            return this.redisTemplate.opsForList().rightPushAll(key, value);
        } catch (Exception var7) {
            log.error("lRightPushAll error", var7);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return null;
    }

    public Long lRightPushIfPresent(String key, T value) {
        try {
            return this.redisTemplate.opsForList().rightPushIfPresent(key, value);
        } catch (Exception var7) {
            log.error("lRightPushIfPresent error", var7);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return null;
    }

    public Long lRightPush(String key, T pivot, T value) {
        try {
            return this.redisTemplate.opsForList().rightPush(key, pivot, value);
        } catch (Exception var8) {
            log.error("lRightPush error", var8);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return null;
    }

    public void lSet(String key, long index, T value) {
        try {
            this.redisTemplate.opsForList().set(key, index, value);
        } catch (Exception var9) {
            log.error("lSet error", var9);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

    }

    public T lLeftPop(String key) {
        try {
            return this.redisTemplate.opsForList().leftPop(key);
        } catch (Exception var6) {
            log.error("lLeftPop error", var6);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return null;
    }

    public T lBLeftPop(String key, long timeout, TimeUnit unit) {
        try {
            return this.redisTemplate.opsForList().leftPop(key, timeout, unit);
        } catch (Exception var9) {
            log.error("lBLeftPop error", var9);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return null;
    }

    public T lRightPop(String key) {
        try {
            return this.redisTemplate.opsForList().rightPop(key);
        } catch (Exception var6) {
            log.error("lRightPop error", var6);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return null;
    }

    public T lBRightPop(String key, long timeout, TimeUnit unit) {
        try {
            return this.redisTemplate.opsForList().rightPop(key, timeout, unit);
        } catch (Exception var9) {
            log.error("lBRightPop error", var9);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return null;
    }

    public T lRightPopAndLeftPush(String sourceKey, String destinationKey) {
        try {
            return this.redisTemplate.opsForList().rightPopAndLeftPush(sourceKey, destinationKey);
        } catch (Exception var7) {
            log.error("lRightPopAndLeftPush error", var7);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return null;
    }

    public T lBRightPopAndLeftPush(String sourceKey, String destinationKey, long timeout, TimeUnit unit) {
        try {
            return this.redisTemplate.opsForList().rightPopAndLeftPush(sourceKey, destinationKey, timeout, unit);
        } catch (Exception var10) {
            log.error("lBRightPopAndLeftPush error", var10);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return null;
    }

    public Long lRemove(String key, long index, T value) {
        try {
            return this.redisTemplate.opsForList().remove(key, index, value);
        } catch (Exception var9) {
            log.error("lTrim error", var9);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return null;
    }

    public void lTrim(String key, long start, long end) {
        try {
            this.redisTemplate.opsForList().trim(key, start, end);
        } catch (Exception var10) {
            log.error("lTrim error", var10);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

    }

    public Long lLen(String key) {
        try {
            return this.redisTemplate.opsForList().size(key);
        } catch (Exception var6) {
            log.error("lLen error", var6);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return 0L;
    }

    public Long sAdd(String key, T... values) {
        try {
            return this.redisTemplate.opsForSet().add(key, values);
        } catch (Exception var7) {
            log.error("sAdd error", var7);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return 0L;
    }

    public Long sRemove(String key, T... values) {
        try {
            return this.redisTemplate.opsForSet().remove(key, values);
        } catch (Exception var7) {
            log.error("sRemove error", var7);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return 0L;
    }

    public T sPop(String key) {
        try {
            return this.redisTemplate.opsForSet().pop(key);
        } catch (Exception var6) {
            log.error("sPop error", var6);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return null;
    }

    public Boolean sMove(String key, T value, String destKey) {
        try {
            return this.redisTemplate.opsForSet().move(key, value, destKey);
        } catch (Exception var8) {
            log.error("sSize error", var8);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return false;
    }

    public Long sSize(String key) {
        try {
            return this.redisTemplate.opsForSet().size(key);
        } catch (Exception var6) {
            log.error("sSize error", var6);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return 0L;
    }

    public Boolean sIsMember(String key, T value) {
        try {
            return this.redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception var7) {
            log.error("sIsMember error", var7);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return null;
    }

    public Set<T> sIntersect(String key, String otherKey) {
        try {
            return this.redisTemplate.opsForSet().intersect(key, otherKey);
        } catch (Exception var7) {
            log.error("sIntersect error", var7);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return null;
    }

    public Set<T> sIntersect(String key, Collection<String> otherKeys) {
        try {
            return this.redisTemplate.opsForSet().intersect(key, otherKeys);
        } catch (Exception var7) {
            log.error("sIntersect error", var7);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return null;
    }

    public Long sIntersectAndStore(String key, String otherKey, String destKey) {
        try {
            return this.redisTemplate.opsForSet().intersectAndStore(key, otherKey, destKey);
        } catch (Exception var8) {
            log.error("sIntersectAndStore error", var8);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return null;
    }

    public Long sIntersectAndStore(String key, Collection<String> otherKeys, String destKey) {
        try {
            return this.redisTemplate.opsForSet().intersectAndStore(key, otherKeys, destKey);
        } catch (Exception var8) {
            log.error("sIntersectAndStore error", var8);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return null;
    }

    public Set<T> sUnion(String key, String otherKeys) {
        try {
            return this.redisTemplate.opsForSet().union(key, otherKeys);
        } catch (Exception var7) {
            log.error("sUnion error", var7);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return null;
    }

    public Set<T> sUnion(String key, Collection<String> otherKeys) {
        try {
            return this.redisTemplate.opsForSet().union(key, otherKeys);
        } catch (Exception var7) {
            log.error("sUnion error", var7);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return null;
    }

    public Long sUnionAndStore(String key, String otherKey, String destKey) {
        try {
            return this.redisTemplate.opsForSet().unionAndStore(key, otherKey, destKey);
        } catch (Exception var8) {
            log.error("sUnionAndStore error", var8);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return null;
    }

    public Long sUnionAndStore(String key, Collection<String> otherKeys, String destKey) {
        try {
            return this.redisTemplate.opsForSet().unionAndStore(key, otherKeys, destKey);
        } catch (Exception var8) {
            log.error("sUnionAndStore error", var8);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return null;
    }

    public Set<T> sDifference(String key, String otherKey) {
        try {
            return this.redisTemplate.opsForSet().difference(key, otherKey);
        } catch (Exception var7) {
            log.error("sDifference error", var7);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return null;
    }

    public Set<T> sDifference(String key, Collection<String> otherKeys) {
        try {
            return this.redisTemplate.opsForSet().difference(key, otherKeys);
        } catch (Exception var7) {
            log.error("sDifference error", var7);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return null;
    }

    public Long sDifference(String key, String otherKey, String destKey) {
        try {
            return this.redisTemplate.opsForSet().differenceAndStore(key, otherKey, destKey);
        } catch (Exception var8) {
            log.error("sDifference error", var8);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return null;
    }

    public Long sDifference(String key, Collection<String> otherKeys, String destKey) {
        try {
            return this.redisTemplate.opsForSet().differenceAndStore(key, otherKeys, destKey);
        } catch (Exception var8) {
            log.error("sDifference error", var8);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return null;
    }

    public Set<T> setMembers(String key) {
        try {
            return this.redisTemplate.opsForSet().members(key);
        } catch (Exception var6) {
            log.error("setMembers error", var6);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return null;
    }

    public T sRandomMember(String key) {
        try {
            return this.redisTemplate.opsForSet().randomMember(key);
        } catch (Exception var6) {
            log.error("sRandomMember error", var6);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return null;
    }

    public List<T> sRandomMembers(String key, long count) {
        try {
            return this.redisTemplate.opsForSet().randomMembers(key, count);
        } catch (Exception var8) {
            log.error("sRandomMembers error", var8);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return null;
    }

    public Set<T> sDistinctRandomMembers(String key, long count) {
        try {
            return this.redisTemplate.opsForSet().distinctRandomMembers(key, count);
        } catch (Exception var8) {
            log.error("sDistinctRandomMembers error", var8);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return null;
    }

    public Cursor<T> sScan(String key, ScanOptions options) {
        try {
            return this.redisTemplate.opsForSet().scan(key, options);
        } catch (Exception var7) {
            log.error("sScan error", var7);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return null;
    }

    public Boolean zAdd(String key, T value, double score) {
        try {
            return this.redisTemplate.opsForZSet().add(key, value, score);
        } catch (Exception var9) {
            log.error("zAdd error", var9);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return false;
    }

    public Long zAdd(String key, Set<ZSetOperations.TypedTuple<T>> values) {
        try {
            return this.redisTemplate.opsForZSet().add(key, values);
        } catch (Exception var7) {
            log.error("zAdd error", var7);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return null;
    }

    public Long zRemove(String key, T... values) {
        try {
            return this.redisTemplate.opsForZSet().remove(key, values);
        } catch (Exception var7) {
            log.error("zRemove error", var7);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return null;
    }

    public Double zIncrementScore(String key, T value, double delta) {
        try {
            return this.redisTemplate.opsForZSet().incrementScore(key, value, delta);
        } catch (Exception var9) {
            log.error("zIncrementScore error", var9);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return null;
    }

    public Long zRank(String key, T value) {
        try {
            return this.redisTemplate.opsForZSet().rank(key, value);
        } catch (Exception var7) {
            log.error("zRank error", var7);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return null;
    }

    public Long zReverseRank(String key, T value) {
        try {
            return this.redisTemplate.opsForZSet().reverseRank(key, value);
        } catch (Exception var7) {
            log.error("zReverseRank error", var7);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return null;
    }

    public Set<T> zRange(String key, long start, long end) {
        try {
            return this.redisTemplate.opsForZSet().range(key, start, end);
        } catch (Exception var10) {
            log.error("zRange error", var10);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return null;
    }

    public Set<ZSetOperations.TypedTuple<T>> zRangeWithScores(String key, long start, long end) {
        try {
            return this.redisTemplate.opsForZSet().rangeWithScores(key, start, end);
        } catch (Exception var10) {
            log.error("zRangeWithScores error", var10);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return null;
    }

    public Set<T> zRangeByScore(String key, double min, double max) {
        try {
            return this.redisTemplate.opsForZSet().rangeByScore(key, min, max);
        } catch (Exception var10) {
            log.error("zRangeByScore error", var10);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return null;
    }

    public Set<ZSetOperations.TypedTuple<T>> zRangeByScoreWithScores(String key, double min, double max) {
        try {
            return this.redisTemplate.opsForZSet().rangeByScoreWithScores(key, min, max);
        } catch (Exception var10) {
            log.error("zRangeByScoreWithScores error", var10);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return null;
    }

    public Set<ZSetOperations.TypedTuple<T>> zRangeByScoreWithScores(String key, double min, double max, long start, long end) {
        try {
            return this.redisTemplate.opsForZSet().rangeByScoreWithScores(key, min, max, start, end);
        } catch (Exception var14) {
            log.error("zRangeByScoreWithScores error", var14);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return null;
    }

    public Set<T> zReverseRange(String key, long start, long end) {
        try {
            return this.redisTemplate.opsForZSet().reverseRange(key, start, end);
        } catch (Exception var10) {
            log.error("zReverseRange error", var10);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return null;
    }

    public Set<ZSetOperations.TypedTuple<T>> zReverseRangeWithScores(String key, long start, long end) {
        try {
            return this.redisTemplate.opsForZSet().reverseRangeWithScores(key, start, end);
        } catch (Exception var10) {
            log.error("zReverseRangeWithScores error", var10);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return null;
    }

    public Set<T> zReverseRangeByScore(String key, double min, double max) {
        try {
            return this.redisTemplate.opsForZSet().reverseRangeByScore(key, min, max);
        } catch (Exception var10) {
            log.error("zReverseRangeByScore error", var10);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return null;
    }

    public Set<ZSetOperations.TypedTuple<T>> zReverseRangeByScoreWithScores(String key, double min, double max) {
        try {
            return this.redisTemplate.opsForZSet().reverseRangeByScoreWithScores(key, min, max);
        } catch (Exception var10) {
            log.error("zReverseRangeByScoreWithScores error", var10);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return null;
    }

    public Set<T> zReverseRangeByScore(String key, double min, double max, long start, long end) {
        try {
            return this.redisTemplate.opsForZSet().reverseRangeByScore(key, min, max, start, end);
        } catch (Exception var14) {
            log.error("zReverseRangeByScore error", var14);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return null;
    }

    public Long zCount(String key, double min, double max) {
        try {
            return this.redisTemplate.opsForZSet().count(key, min, max);
        } catch (Exception var10) {
            log.error("zCount error", var10);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return 0L;
    }

    public Long zSize(String key) {
        try {
            return this.redisTemplate.opsForZSet().size(key);
        } catch (Exception var6) {
            log.error("zSize error", var6);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return 0L;
    }

    public Long zZCard(String key) {
        try {
            return this.redisTemplate.opsForZSet().zCard(key);
        } catch (Exception var6) {
            log.error("zZCard error", var6);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return 0L;
    }

    public Double zScore(String key, T value) {
        try {
            return this.redisTemplate.opsForZSet().score(key, value);
        } catch (Exception var7) {
            log.error("zScore error", var7);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return null;
    }

    public Long zRemoveRange(String key, long start, long end) {
        try {
            return this.redisTemplate.opsForZSet().removeRange(key, start, end);
        } catch (Exception var10) {
            log.error("zRemoveRange error", var10);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return 0L;
    }

    public Long zRemoveRangeByScore(String key, double min, double max) {
        try {
            return this.redisTemplate.opsForZSet().removeRangeByScore(key, min, max);
        } catch (Exception var10) {
            log.error("zRemoveRangeByScore error", var10);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return 0L;
    }

    public Long zUnionAndStore(String key, String otherKey, String destKey) {
        try {
            return this.redisTemplate.opsForZSet().unionAndStore(key, otherKey, destKey);
        } catch (Exception var8) {
            log.error("zUnionAndStore error", var8);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return 0L;
    }

    public Long zUnionAndStore(String key, Collection<String> otherKeys, String destKey) {
        try {
            return this.redisTemplate.opsForZSet().unionAndStore(key, otherKeys, destKey);
        } catch (Exception var8) {
            log.error("zUnionAndStore error", var8);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return 0L;
    }

    public Long zIntersectAndStore(String key, String otherKey, String destKey) {
        try {
            return this.redisTemplate.opsForZSet().intersectAndStore(key, otherKey, destKey);
        } catch (Exception var8) {
            log.error("zIntersectAndStore error", var8);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return 0L;
    }

    public Long zIntersectAndStore(String key, Collection<String> otherKeys, String destKey) {
        try {
            return this.redisTemplate.opsForZSet().intersectAndStore(key, otherKeys, destKey);
        } catch (Exception var8) {
            log.error("zIntersectAndStore error", var8);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return 0L;
    }

    public Cursor<ZSetOperations.TypedTuple<T>> zScan(String key, ScanOptions options) {
        try {
            return this.redisTemplate.opsForZSet().scan(key, options);
        } catch (Exception var7) {
            log.error("zScan error", var7);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

        return null;
    }

    public String ping() {
        return this.redisTemplate.execute((RedisCallback<String>) connection -> connection.ping());
    }

    public void convertAndSend(String topic, T value) {
        try {
            this.redisTemplate.convertAndSend(topic, value);
        } catch (Exception var7) {
            var7.printStackTrace();
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(this.redisTemplate.getConnectionFactory()));
        }

    }

    public void close() {
    }

    public Cursor<byte[]> scan(ScanOptions options) {
        return this.redisTemplate.execute((RedisCallback<Cursor<byte[]>>) connection -> {
            Cursor<byte[]> cursor = connection.scan(options);
            return cursor;
        });
    }

}
