package plus.easydo.server.config.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author yuzhanfeng
 * @Date 2022/8/24 17:09
 * @Description web拦截器配置
 */
@Configuration
public class WebMvcConfigurer extends WebMvcConfigurationSupport {


    @Bean
    public UserUrlInterceptor userUrlInterceptor() {
        return new UserUrlInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userUrlInterceptor()).addPathPatterns("/**");
    }
}
