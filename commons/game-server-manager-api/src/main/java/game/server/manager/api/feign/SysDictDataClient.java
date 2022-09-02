package game.server.manager.api.feign;

import game.server.manager.api.FeignInterceptor;
import game.server.manager.api.SysDictDataApi;
import game.server.manager.api.feign.fallback.SysDictClientFallback;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author laoyu
 * @version 1.0
 * @date 2022/8/31
 */
@FeignClient( url = "${system.server-list.uc:game-server-manager-uc.server}", configuration = FeignInterceptor.class,
        path = "/dictData",name = "uc-dict" ,fallback = SysDictClientFallback.class)
public interface SysDictDataClient extends SysDictDataApi {
}
