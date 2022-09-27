package game.server.manager.server.config;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import game.server.manager.common.constant.HttpStatus;
import game.server.manager.common.exception.BizException;
import game.server.manager.common.utils.IpUtil;
import game.server.manager.redis.config.RedisUtils;
import game.server.manager.server.entity.Blacklist;
import game.server.manager.server.service.BlacklistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author yuzhanfeng
 * @Date 2022/8/24 17:10
 * @Description url请求拦截器
 */
@Component
public class UserUrlInterceptor implements HandlerInterceptor {

    private static final String REDIS_KEY = "back_list:";

    @Value("${system.back-list.back-time:30}")
    private int backTime = 30;

    @Value("${system.back-list.max-req-time:30000}")
    private int maxReqTime = 30000;

    @Value("${system.back-list.max-req-count:10}")
    private int maxReqCount = 10;


    @Autowired
    private RedisUtils<Integer> redisUtils;

    @Autowired
    private BlacklistService blacklistService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 获取请求的url
        String url = request.getRequestURI();
        String ip = IpUtil.getIpAddress();
        Blacklist domain = blacklistService.getByIp(ip);
        if (domain != null) {
            LocalDateTime disableTime = domain.getDisableTime();
            long between = LocalDateTimeUtil.between(LocalDateTime.now(), disableTime).toMillis();
            if(between < 0){
                blacklistService.removeById(domain.getId());
            }else {
                throw new BizException(HttpStatus.UNAUTHORIZED+"","访问频繁,已限制访问," +
                        ""+ DateUtil.format(disableTime, DatePattern.NORM_DATETIME_PATTERN) +"解除。");
            }
        }

        // 先查询redis中是否有这个键
        String key = (REDIS_KEY + ip + ":" + url
                // 先查询redis中是否有这个键
        ).replace("/", ".");
        Integer cacheReqCount = redisUtils.get(key);
        if (Objects.isNull(cacheReqCount)) {
            // 设置请求间隔
            redisUtils.set(key, 1, maxReqTime, TimeUnit.MILLISECONDS);
        } else {
            if (cacheReqCount < maxReqCount) {
                // 累加指定时间内的请求次数
                long redisTime = redisUtils.getExpire(key);
                redisUtils.set(key, cacheReqCount+ 1, Math.toIntExact(redisTime), TimeUnit.SECONDS);
            } else {
                // 超过访问次数 进入黑名单
                Blacklist backList = init(ip);
                blacklistService.save(backList);
                redisUtils.delete(REDIS_KEY+ip);
                throw new BizException(HttpStatus.UNAUTHORIZED+"","访问频繁,已限制访问," +
                        ""+ DateUtil.format(backList.getDisableTime(), DatePattern.NORM_DATETIME_PATTERN) +"解除。");
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

    }

    public Blacklist init(String ip) {
        Blacklist domain = new Blacklist();
        domain.setIp(ip);
        domain.setDisableTime(LocalDateTimeUtil.offset(LocalDateTime.now(), backTime, ChronoUnit.MINUTES));
        return domain;
    }

}
