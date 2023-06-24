package plus.easydo.api;

import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author laoyu
 * @version 1.0
 * @description api配置扩展
 * @date 2023/6/24
 */
public interface ApiConfigurationExpand {


    void expand(WebClient.Builder webClientBuilder);
}
