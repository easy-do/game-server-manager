package game.server.manager.handler;

import game.server.manager.common.exception.ExceptionFactory;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 存储执行类的容器
 * @author laoyu
 * @version 1.0
 */
@Component
public class HandlerServiceContainer<T, R> {

    private ConcurrentHashMap<String, AbstractHandlerService<T, R>> map = new ConcurrentHashMap<>();

    public void put(String key, AbstractHandlerService<T, R> handlerService) {
        map.put(key, handlerService);
    }

    public AbstractHandlerService<T, R> getHandlerService(String key){
        AbstractHandlerService<T, R> handlerService = map.get(key);
        if (Objects.isNull(handlerService)) {
            throw ExceptionFactory.bizException(key + " handler service not found!");
        }
        return handlerService;
    }

    public R handler(String key, T t){
        return getHandlerService(key).handler(t);
    }
}
