package game.server.manager.handler.config;

import game.server.manager.handler.AbstractHandlerService;
import game.server.manager.handler.HandlerServiceContainer;
import game.server.manager.handler.annotation.HandlerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * @author laoyu
 * @version 1.0
 */
@Configuration(proxyBeanMethods = false)
public class HandlerServiceConfig implements ApplicationContextAware, SmartInitializingSingleton {

    Logger logger = LoggerFactory.getLogger(HandlerServiceConfig.class);

    @Autowired
    private HandlerServiceContainer<String, AbstractHandlerService<Object,Object>> handlerServiceContainer;

    private ConfigurableApplicationContext applicationContext;

    /**
     * bean实例化的后置操作
     *
     * @author laoyu
     */
    @Override
    public void afterSingletonsInstantiated() {
        Map<String, Object> beans = this.applicationContext.getBeansWithAnnotation(HandlerService.class);
        beans.forEach(this::registerHttpProxy);
    }

    /**
     * 把所有被注解描述的对象注册到容器
     *
     * @param s s
     * @param bean bean
     * @author laoyu
     */
    private void registerHttpProxy(String s, Object bean) {
        Class<?> clazz = AopProxyUtils.ultimateTargetClass(bean);
        Class<?> superClass = clazz.getSuperclass();
            if (superClass == AbstractHandlerService.class) {
                HandlerService annotation = clazz.getAnnotation(HandlerService.class);
                String key = annotation.value();
                handlerServiceContainer.put(key, (AbstractHandlerService) bean);
                logger.info("register handlerService ,key:{},class:{}",key, bean.getClass().getSimpleName());
            }
    }


    /**
     * 设置applicationContext
     *
     * @param applicationContext applicationContext
     * @author laoyu
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = (ConfigurableApplicationContext) applicationContext;
    }
}
