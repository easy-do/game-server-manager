package game.server.manager.generate;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author yuzhanfeng
 */
@EnableAsync
@EnableFeignClients(basePackages = {"game.server.manager"})
@SpringBootApplication
@ComponentScan(basePackages = {"game.server.manager"})
@MapperScan(basePackages = {"game.server.manager.*.mapper"})
public class GameServerManagerGenerateApplication {

    public static void main(String[] args) {
        SpringApplication.run(GameServerManagerGenerateApplication.class, args);
    }

}
