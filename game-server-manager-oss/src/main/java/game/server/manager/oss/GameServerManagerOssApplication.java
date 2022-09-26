package game.server.manager.oss;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author yuzhanfeng
 */
@EnableAsync
@ComponentScan(basePackages = {"game.server.manager"})
@MapperScan(basePackages = {"game.server.manager.*.mapper"})
@SpringBootApplication
public class GameServerManagerOssApplication {

    public static void main(String[] args) {
        SpringApplication.run(GameServerManagerOssApplication.class, args);
    }

}
