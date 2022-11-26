package game.server.manager.handler.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author yuzhanfeng
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface HandlerService {

    /**
     * 处理器名称
     *
     * @return java.lang.String
     * @author laoyu
     * @date 2022/11/26
     */
    String value();

}
