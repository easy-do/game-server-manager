package plus.easydo.uc.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

/**
 * @author laoyu
 * @version 1.0
 * @description api客户端配置
 * @date 2023/6/24
 */
@Configuration(proxyBeanMethods = false)
public class UcApiConfiguration {


    @Value("${system.server-list.uc:https://game-server-manaegr-uc.server}")
    private String baseUrl;

    @Bean
    public OauthClientDetailsApi oauthClientDetailsApi(WebClient.Builder webClientBuilder) {
        WebClient webClient = webClientBuilder.baseUrl(baseUrl).build();
        return HttpServiceProxyFactory.builder().clientAdapter(WebClientAdapter.forClient(webClient)) //
                .build().createClient(OauthClientDetailsApi.class);
    }

    @Bean
    public EmailApi emailApi(WebClient.Builder webClientBuilder) {
        WebClient webClient = webClientBuilder.baseUrl(baseUrl).build();
        return HttpServiceProxyFactory.builder().clientAdapter(WebClientAdapter.forClient(webClient)) //
                .build().createClient(EmailApi.class);
    }

    @Bean
    public AuthorizationCodeApi authorizationCodeApi(WebClient.Builder webClientBuilder) {
        WebClient webClient = webClientBuilder.baseUrl(baseUrl).build();
        return HttpServiceProxyFactory.builder().clientAdapter(WebClientAdapter.forClient(webClient)) //
                .build().createClient(AuthorizationCodeApi.class);
    }

}
