package game.server.manager;

import com.alicp.jetcache.anno.config.EnableMethodCache;
//import game.server.manager.server.netty.CoordinationNettyServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author yuzhanfeng
 */
@EnableScheduling
@EnableAsync
@EnableMethodCache(basePackages = "game.server.manager")
@EnableFeignClients(basePackages = {"game.server.manager"})
@SpringBootApplication
@MapperScan(basePackages = {"game.server.manager.*.mapper"})
public class GameServerManagerServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(GameServerManagerServerApplication.class, args);
//        try {
//            new CoordinationNettyServer(8804).start();
//        } catch (Exception e) {
//            System.out.println("NettyServerError:" + e.getMessage());
//        }
    }

}
