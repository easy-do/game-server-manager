package game.server.manager.handler;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author laoyu
 * @version 1.0
 */
public abstract class AbstractHandlerService<T, R> {

    protected static Logger logger = LoggerFactory.getLogger(AbstractHandlerService.class);

    /**
     * 处理
     *
     * @param t 传递的参数
     * @return java.lang.Object
     * @author laoyu
     */
    public abstract R handler(T t);
}
