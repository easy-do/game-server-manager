package game.server.manager.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author yuzhanfeng
 */
@EnableScheduling
@ComponentScan(basePackages = {"game.server.manager"})
@SpringBootApplication
public class GameServerManagerClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(GameServerManagerClientApplication.class, args);
    }

}
