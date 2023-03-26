package game.server.manager.client.config;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;


/**
 * @author laoyu
 * @version 1.0
 * @description docekr配置
 * @date 2022/11/19
 */
@Slf4j
@Data
@Configuration(proxyBeanMethods = false)
public class SystemConfig {

    @Autowired
    private SystemUtils systemUtils;

    @Bean
    public DockerClient dockerClient(){
        DockerClientConfig dockerClientConfig = DefaultDockerClientConfig
//                .createDefaultConfigBuilder().build();
        .createDefaultConfigBuilder().withDockerHost("tcp://192.168.123.88:2375").build();
        DockerHttpClient httpClient = new ApacheDockerHttpClient.Builder()
                .dockerHost(dockerClientConfig.getDockerHost())
                .sslConfig(dockerClientConfig.getSSLConfig())
                .maxConnections(100)
                .connectionTimeout(Duration.ofSeconds(30))
                .responseTimeout(Duration.ofSeconds(45))
                .build();
        return DockerClientBuilder.getInstance(dockerClientConfig).withDockerHttpClient(httpClient)
                .build();
    }

}
