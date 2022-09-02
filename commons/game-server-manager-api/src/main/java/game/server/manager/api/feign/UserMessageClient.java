package game.server.manager.api.feign;

import game.server.manager.api.FeignInterceptor;
import game.server.manager.api.UserMessageApi;
import game.server.manager.api.feign.fallback.UserMessageClientFallback;
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
