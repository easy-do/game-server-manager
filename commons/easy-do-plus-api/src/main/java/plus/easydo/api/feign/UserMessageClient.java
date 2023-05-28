package plus.easydo.api.feign;

import plus.easydo.api.UserMessageApi;
import plus.easydo.api.FeignInterceptor;
import plus.easydo.api.feign.fallback.UserMessageClientFallback;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author laoyu
 * @version 1.0
 * @date 2022/8/31
 */
@FeignClient( url = "${system.server-list.uc:game-server-manager-uc.server}", configuration = FeignInterceptor.class,
        path = "/userMessage", name = "uc-message", fallback = UserMessageClientFallback.class)
public interface UserMessageClient extends UserMessageApi {
}
