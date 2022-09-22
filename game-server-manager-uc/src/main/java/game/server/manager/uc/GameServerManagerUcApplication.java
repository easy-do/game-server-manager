package game.server.manager.uc;

import com.alicp.jetcache.anno.config.EnableMethodCache;
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
@EnableMethodCache(basePackages = "game.server.manager")
@EnableFeignClients(basePackages = {"game.server.manager"})
@SpringBootApplication
@ComponentScan(basePackages = {"game.server.manager.**"})
@MapperScan(basePackages = {"game.server.manager.*.mapper"})
public class GameServerManagerUcApplication {

    public static void main(String[] args) {
        SpringApplication.run(GameServerManagerUcApplication.class, args);
    }

}
