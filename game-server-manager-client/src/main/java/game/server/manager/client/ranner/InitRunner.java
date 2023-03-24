package game.server.manager.client.ranner;

import game.server.manager.client.config.SystemUtils;
import game.server.manager.client.websocket.ClientWebsocketEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.net.URI;


/**
 * @author laoyu
 * @version 1.0
 */
@Component
@Slf4j
public class InitRunner implements ApplicationRunner {

    @Autowired
    private SystemUtils systemUtils;

    @Override
    public void run(ApplicationArguments args) {

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
