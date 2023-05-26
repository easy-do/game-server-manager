package plus.easydo.api.feign;

import plus.easydo.api.FeignInterceptor;
import plus.easydo.api.OssApi;
import plus.easydo.api.feign.fallback.OssClientFallback;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author laoyu
 * @version 1.0
 * @date 2022/8/31
 */
@FeignClient( url = "${system.server-list.oss:easy-do-plus-oss.server}", configuration = FeignInterceptor.class,
        path = "/", name = "oss", fallback = OssClientFallback.class)
public interface OssClient extends OssApi {


}
