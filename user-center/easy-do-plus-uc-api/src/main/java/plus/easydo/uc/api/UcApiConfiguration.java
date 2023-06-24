package plus.easydo.uc.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import plus.easydo.api.ApiConfigurationExpand;

import java.util.Objects;

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

    @Autowired(required = false)
    private ApiConfigurationExpand apiConfigurationExpand;

    @Bean
    public OauthClientDetailsApi oauthClientDetailsApi(WebClient.Builder webClientBuilder) {
        WebClient webClient = buidlWebclient(webClientBuilder);
        return HttpServiceProxyFactory.builder().clientAdapter(WebClientAdapter.forClient(webClient)) //
                .build().createClient(OauthClientDetailsApi.class);
    }

    @Bean
    public EmailApi emailApi(WebClient.Builder webClientBuilder) {
        WebClient webClient = buidlWebclient(webClientBuilder);
        return HttpServiceProxyFactory.builder().clientAdapter(WebClientAdapter.forClient(webClient)) //
                .build().createClient(EmailApi.class);
    }

    @Bean
    public AuthorizationCodeApi authorizationCodeApi(WebClient.Builder webClientBuilder) {
        WebClient webClient = buidlWebclient(webClientBuilder);
        return HttpServiceProxyFactory.builder().clientAdapter(WebClientAdapter.forClient(webClient)) //
                .build().createClient(AuthorizationCodeApi.class);
    }

    /**
     * 构建webclient
     *
     * @param webClientBuilder webClientBuilder
     * @return org.springframework.web.reactive.function.client.WebClient
     * @author laoyu
     * @date 2023/6/24
     */
    private WebClient buidlWebclient(WebClient.Builder webClientBuilder) {
        webClientBuilder.baseUrl(baseUrl);
        if(Objects.nonNull(apiConfigurationExpand)){
            apiConfigurationExpand.expand(webClientBuilder);
        }
        return webClientBuilder.build();
    }

}
