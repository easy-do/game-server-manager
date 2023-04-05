package plus.easydo;

import io.vertx.ext.web.Router;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.List;

/**
 * @author yuzhanfeng
 * @Date 2023-04-04 15:12
 * @Description 自定义属性注入
 */
public class GreetingResource {

    private static Logger log = LoggerFactory.getLogger(GreetingResource.class);


    /**
     * ❶注入greeting.message属性的值
     * ❷将field放置在包保护的范围内
     */
    @ConfigProperty(name = "greeting.message")
    String message;

    /**
     * 配置集合
     */
    @ConfigProperty(name = "greeting.messages")
    List<String> messages;

    /**
     * 普通配置
     *
     * @param router router
     * @author laoyu
     * @date 2023-04-05
     */

//    @GET("/hello_greeting")
    public void message (@Observes Router router){
        router.get("/hello_greeting_message")
                //❸返回配置值
                .handler(rc->rc.response().send(message));
    }

    /**
     * 配置集合
     *
     * @param router router
     * @author laoyu
     * @date 2023-04-05
     */
    public void messages (@Observes Router router){
        router.get("/hello_greeting_messages")
                .handler(rc->rc.response().send(messages.toString()));
    }

    @Inject
    Config config;

    /**
     * 获取config  依赖注入或直接获得
     *
     * @param router router
     * @author laoyu
     * @date 2023-04-05
     */
    public void helloConfig(@Observes Router router){
        router.get("/hello_config")
                .handler(rc->{
                    //不是用CDI获取config
//                    Config config1 = ConfigProvider.getConfig();
                    Iterable<String> propertyNames = config.getPropertyNames();
                    rc.response().send(propertyNames.toString());
                });
    }

    /**
     * 日志打印
     *
     * @param router router
     * @author laoyu
     * @date 2023-04-05
     */
    public void helloLog(@Observes Router router){
        router.get("/hello_log")
                .handler(rc->{
                    log.info("hello_log");
                    rc.response().send("OK");
                });
    }

}
