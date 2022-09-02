package game.server.manager.api.feign;

import game.server.manager.api.FeignInterceptor;
import game.server.manager.api.UserInfoApi;
import game.server.manager.api.feign.fallback.UserInfoClientFallback;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author yuzhanfeng
 */
@FeignClient( url = "${system.server-list.uc:game-server-manager-uc.server}", configuration = FeignInterceptor.class,
        path = "/user",name = "uc-userinfo", fallback = UserInfoClientFallback.class)
public interface UserInfoClient extends UserInfoApi {
}
