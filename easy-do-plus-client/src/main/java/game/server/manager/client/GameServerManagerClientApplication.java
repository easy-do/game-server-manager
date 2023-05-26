package plus.easydo.push.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author yuzhanfeng
 */
@EnableScheduling
@SpringBootApplication
public class GameServerManagerClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(GameServerManagerClientApplication.class, args);
    }

}
