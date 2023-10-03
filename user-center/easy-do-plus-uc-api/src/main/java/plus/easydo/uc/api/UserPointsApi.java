package plus.easydo.uc.api;

import org.springframework.web.service.annotation.HttpExchange;

/**
 * @author laoyu
 * @version 1.0
 * @description 用户积分api
 * @date 2023/6/24
 */
@HttpExchange
public interface UserPointsApi {
    String apiPath = "/oauthClient";
}
