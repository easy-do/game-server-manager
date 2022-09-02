package game.server.manager.plugins;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author yuzhanfeng
 */
@EnableScheduling
@SpringBootApplication
public class GameServerManagerPluginsApplication {

    public static void main(String[] args) {
        SpringApplication.run(GameServerManagerPluginsApplication.class, args);
    }

}
