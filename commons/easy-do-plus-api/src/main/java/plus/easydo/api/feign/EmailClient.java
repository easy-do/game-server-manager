package plus.easydo.api.feign;

import plus.easydo.api.EmailApi;
import plus.easydo.api.FeignInterceptor;
import plus.easydo.api.feign.fallback.EmailClientFallback;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author laoyu
 * @version 1.0
 * @date 2022/8/31
 */
@FeignClient( url = "${system.server-list.uc:game-server-manager-uc.server}", configuration = FeignInterceptor.class,
        path = "/email", name = "uc-email", fallback = EmailClientFallback.class)
public interface EmailClient extends EmailApi {
}
