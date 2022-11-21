package game.server.manager.client.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @author laoyu
 * @version 1.0
 * @description web设置
 * @date 2022/11/19
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Resource
    private SecretInterceptor secretInterceptor;

    @Value("${springdoc.swagger-ui.path:/swagger-ui}")
    private String swaggerUi;

    @Value("${springdoc.api-docs.path:/v2/api-docs}")
    private String apiDocs;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(secretInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(swaggerUi+"/**",apiDocs+"/**");
    }
}
