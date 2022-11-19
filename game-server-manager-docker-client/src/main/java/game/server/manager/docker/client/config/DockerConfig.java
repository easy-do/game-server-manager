package game.server.manager.docker.client.config;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.text.CharSequenceUtil;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author laoyu
 * @version 1.0
 * @description docekr配置
 * @date 2022/11/19
 */
@Slf4j
@Data
@Configuration
public class DockerConfig {

    @Value("${secret:}")
    private String secret;

    public DockerClient createClient() {
        if (CharSequenceUtil.isBlank(secret)) {
            secret = UUID.fastUUID().toString(true);
            log.info("set default secret :{}",secret);
        }
        DockerClientConfig dockerClientConfig = DefaultDockerClientConfig
                .createDefaultConfigBuilder().build();
        DockerClient client = DockerClientBuilder.getInstance(dockerClientConfig)
                .build();
        log.info("docker info:{}",client.infoCmd().exec());
        return client;
    }

    @Bean
    public DockerClient dockerClient(){
        return createClient();
    }
}
