package plus.easydo.server.service;

import plus.easydo.api.UserInfoApi;
import plus.easydo.common.constant.SystemConstant;
import plus.easydo.redis.RedisUtils;
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

    public long deployCount() {
        return executeLogService.count();
    }

    public long applicationCount(){
        return applicationService.count();
    }
}
