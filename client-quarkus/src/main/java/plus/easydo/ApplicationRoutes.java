package plus.easydo;


import io.quarkus.vertx.http.runtime.filters.Filters;
import io.vertx.ext.web.Router;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;


/**
 * @author yuzhanfeng
 * @Date 2023-04-04 11:12
 * @Description 响应式路由
 */

@ApplicationScoped //❶将对象实例化到CDI容器中，其作用域为application
public class ApplicationRoutes {


    //❷提供Router对象来注册路由
    public void routes(@Observes Router router) {
        //❸将GET HTTP方法绑定到/hello_route
        router.get("/hello_route")
                //❹处理逻辑
                .handler(routingContext -> routingContext.response().send("OK form Route"));
    }


//    // 需要 quarkus-vertx-web 依赖
//    @Route(path = "/vertx_web_route", methods = Route.HttpMethod.GET)
//    public void greetings(RoutingContext routingContext) {
//        String name = routingContext.request().getParam("name");
//        if (name == null) {
//            name = "world";
//            routingContext.response().end("OK " + name + "you are right");
//        }
//    }

    /**
     * ❶提供Filters对象来注册过滤器
     * ❷修改响应
     * ❸在响应中添加一个新的头
     * ❹继续过滤链
     * ❺设定执行顺序
     */
//    public void filters(@Observes Filters filters) {
//        filters.register(rc -> {
//            rc.response()
//                    .putHeader("V-Header", "Header added by Vertx Filter");
//            rc.next();
//        }, 10);
//    }
}
