package game.server.manager.client.config;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;
import game.server.manager.client.websocket.ClientWebsocketEndpoint;
import game.server.manager.client.websocket.WebSocketClientHandlerService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;
import java.net.URISyntaxException;
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



//    /**
//     * 实例化websocket客户端
//     *
//     * @param systemUtils systemUtils
//     * @param webSocketClientHandlerService webSocketClientHandlerService
//     * @return game.server.manager.client.websocket.ClientWebsocketEndpoint
//     * @author laoyu
//     * @date 2023/3/26
//     */
//    @Bean
//    public ClientWebsocketEndpoint clientWebsocketEndpoint(SystemUtils systemUtils, WebSocketClientHandlerService webSocketClientHandlerService) throws URISyntaxException {
//        URI serverSocketUrI = new URI(systemUtils.getServerSocketUrl());
//        ClientWebsocketEndpoint clientWebsocketEndpoint = new ClientWebsocketEndpoint(serverSocketUrI);
//        clientWebsocketEndpoint.setSystemUtils(systemUtils);
//        clientWebsocketEndpoint.setHandlerService(webSocketClientHandlerService);
//        return clientWebsocketEndpoint;
//    }


}
