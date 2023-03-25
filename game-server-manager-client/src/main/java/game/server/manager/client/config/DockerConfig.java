package game.server.manager.client.config;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import game.server.manager.client.websocket.ClientWebsocketEndpoint;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;


/**
 * @author laoyu
 * @version 1.0
 * @description docekr配置
 * @date 2022/11/19
 */
@Slf4j
@Data
@Configuration(proxyBeanMethods = false)
public class DockerConfig {

    @Autowired
    private SystemUtils systemUtils;

    @Bean
    public DockerClient dockerClient(){
        DockerClient client = null;
        DockerClientConfig dockerClientConfig = DefaultDockerClientConfig
//                .createDefaultConfigBuilder().build();
        .createDefaultConfigBuilder().withDockerHost("tcp://192.168.123.88:2375").build();
       return DockerClientBuilder.getInstance(dockerClientConfig)
                .build();
//        try {
//            client =
//            log.info("docker info:{}",client.infoCmd().exec());
//            return client;
//        }catch (Exception exception) {
//            log.error("初始化docker失败:{}", ExceptionUtil.getMessage(exception));
//            return client;
//        }
    }

    @Bean
    public ClientWebsocketEndpoint clientWebsocketEndpoint() {
        try {
            URI uri = new URI(systemUtils.getServerSocketUrl());
            return new ClientWebsocketEndpoint(uri, systemUtils.getClientId());
        }catch (Exception e) {
            log.warn("初始化客户端连接失败,请前往官网寻求帮助：https://push.easydo.plus");
            System.exit(1000);
        }
        return null;
    }
}
