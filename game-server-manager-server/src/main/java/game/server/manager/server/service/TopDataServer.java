package game.server.manager.server.service;

import game.server.manager.api.UserInfoApi;
import game.server.manager.common.constant.SystemConstant;
import game.server.manager.redis.config.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author laoyu
 * @version 1.0
 * @description 统计数据服务
 * @date 2022/5/31
 */
@Component
public class TopDataServer {

    @Autowired
    private RedisUtils<Object> redisUtils;

    @Autowired
    private UserInfoApi userInfoService;

    @Autowired
    private ExecuteLogService executeLogService;

    @Autowired
    private ApplicationService applicationService;


    public long userCount() {
        Object count = redisUtils.get(SystemConstant.COUNT_DATA_KEY_PREFIX + SystemConstant.USER_COUNT);
        if(Objects.nonNull(count)){
            return (long) count;
        }
        long newCount = userInfoService.count().getData();
        redisUtils.set(SystemConstant.COUNT_DATA_KEY_PREFIX + SystemConstant.USER_COUNT,newCount,5L, TimeUnit.MINUTES);
        return newCount;
    }

    public long onlineUserCount() {
        return redisUtils.keys(SystemConstant.COUNT_DATA_KEY_PREFIX + SystemConstant.USER_INFO+"*").size();
    }

    public long deployCount() {
        return executeLogService.count();
    }

    public long applicationCount(){
        return applicationService.count();
    }
}
