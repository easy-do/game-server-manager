package game.server.manager.api.feign;

import game.server.manager.api.EmailApi;
import game.server.manager.api.FeignInterceptor;
import game.server.manager.api.feign.fallback.EmailClientFallback;
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
