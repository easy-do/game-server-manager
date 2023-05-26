package plus.easydo.docker.client.api.config;

import plus.easydo.docker.client.api.DockerClientApiEndpoint;
import plus.easydo.docker.client.api.impl.DockerClientApiEndpointImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author laoyu
 * @version 1.0
 * @description 自动装配
 * @date 2022/11/19
 */
@Configuration
public class AutoConfiguration {

    @Bean
    public DockerClientApiEndpoint dockerClientApiEndpoint(){
        return new DockerClientApiEndpointImpl();
    }
}
