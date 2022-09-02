package game.server.manager.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author yuzhanfeng
 */
@EnableAsync
@EnableCaching
@EnableFeignClients(basePackages = {"game.server.manager"})
@SpringBootApplication
@ComponentScan(basePackages = {"game.server.manager"})
@MapperScan(basePackages = {"game.server.manager.*.mapper"})
public class GameServerManagerServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(GameServerManagerServerApplication.class, args);
    }

}
