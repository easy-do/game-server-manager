package game.server.manager.api.feign;

import game.server.manager.api.FeignInterceptor;
import game.server.manager.api.OssApi;
import game.server.manager.api.feign.fallback.OssClientFallback;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author laoyu
 * @version 1.0
 * @date 2022/8/31
 */
@FeignClient( url = "${system.server-list.oss:game-server-manager-oss.server}", configuration = FeignInterceptor.class,
        path = "/", name = "oss", fallback = OssClientFallback.class)
public interface OssClient extends OssApi {


}
