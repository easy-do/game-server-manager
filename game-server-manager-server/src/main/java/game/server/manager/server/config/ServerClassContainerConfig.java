package game.server.manager.server.config;

import game.server.manager.server.annotation.SyncServerClass;
import game.server.manager.server.server.AbstractDefaultServer;
import game.server.manager.server.server.DefaultServerContainer;
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
@Configuration
public class ServerClassContainerConfig implements ApplicationContextAware, SmartInitializingSingleton {

    Logger logger = LoggerFactory.getLogger(ServerClassContainerConfig.class);

    @Autowired
    private DefaultServerContainer defaultServerContainer;

    private ConfigurableApplicationContext applicationContext;

    /**
     * bean实例化的后置操作
     *
     * @author laoyu
     */
    @Override
    public void afterSingletonsInstantiated() {
        Map<String, Object> beans = this.applicationContext.getBeansWithAnnotation(SyncServerClass.class);
        beans.forEach(this::registerHttpProxy);
    }

    /**
     * 把所有代理工具注册到容器
     *
     * @param s s
     * @param bean bean
     * @author laoyu
     */
    private void registerHttpProxy(String s, Object bean) {
        Class<?> clazz = AopProxyUtils.ultimateTargetClass(bean);
        SyncServerClass annotation = clazz.getAnnotation(SyncServerClass.class);
        String key = annotation.value();
        defaultServerContainer.put(key, (AbstractDefaultServer) bean);
        logger.info("注册服务类,key:{},class:{}",key, bean.getClass().getSimpleName());
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
