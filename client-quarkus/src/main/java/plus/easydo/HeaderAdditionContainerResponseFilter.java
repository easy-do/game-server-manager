package plus.easydo;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

/**
 * @author yuzhanfeng
 * @Date 2023-04-04 14:55
 * @Description 自定义请求拦截
 * ❶将该类设置为扩展接口
 * ❷根据需要作出改变
 * ❸在响应中添加一个新的头
 */
@Provider
public class HeaderAdditionContainerResponseFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        responseContext.getHeaders().add("X-Header","Header added by JAXRS Filter");
    }
}
