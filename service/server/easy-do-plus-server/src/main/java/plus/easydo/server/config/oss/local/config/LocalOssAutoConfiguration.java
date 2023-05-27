package plus.easydo.server.config.oss.local.config;

import plus.easydo.server.config.oss.local.LocalOssService;
import plus.easydo.server.config.oss.local.LocalOssTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * minio自动装配
 * @author laoyu
 * @version 1.0
 */
@Configuration
public class LocalOssAutoConfiguration {


    @Bean
    @ConditionalOnProperty(prefix = "oss.minio", name = "enable", havingValue = "true")
    public LocalOssTemplate localOssTemplate(){
        return new LocalOssTemplate();
    }

    @Bean
    @ConditionalOnProperty(prefix = "oss.minio", name = "enable", havingValue = "true")
    public LocalOssService localOssService(){
        return new LocalOssService();
    }
}
