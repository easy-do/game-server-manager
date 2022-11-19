package game.server.manager.docker.client.config;

import cn.hutool.core.text.CharSequenceUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author laoyu
 * @version 1.0
 * @description 密钥校验全局请求拦截器
 * @date 2022/11/19
 */
@Slf4j
@Component
public class SecretInterceptor implements HandlerInterceptor {

    @Resource
    private DockerConfig dockerConfig;

    /**
     * 在访问Controller某个方法之前这个方法会被调用
     *
     * @param request request
     * @param response response
     * @param handler handler
     * @return boolean
     * @author laoyu
     * @date 2022/11/19
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if ( !(handler instanceof HandlerMethod)){
            return true;//如果不是映射到方法直接通过
        }
        log.info(request.getRequestURI());
        String headerSecret = request.getHeader("secret");
        log.info("headerSecret,{}",headerSecret);
        String secret = dockerConfig.getSecret();
        log.info("secret,{}",secret);
        if(CharSequenceUtil.isBlank(headerSecret)){
            log.warn("secret not found");
            return false;
        }
        if(!secret.equals(headerSecret)){
            log.warn("secret check fail");
            return false;
        }
        return true;
    }
}
