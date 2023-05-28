package plus.easydo.api.feign;

import plus.easydo.api.FeignInterceptor;
import plus.easydo.api.UserInfoApi;
import plus.easydo.api.feign.fallback.UserInfoClientFallback;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author yuzhanfeng
 */
@FeignClient( url = "${system.server-list.uc:game-server-manager-uc.server}", configuration = FeignInterceptor.class,
        path = "/user",name = "uc-userinfo", fallback = UserInfoClientFallback.class)
public interface UserInfoClient extends UserInfoApi {
}
