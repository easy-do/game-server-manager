package plus.easydo;

import com.alicp.jetcache.anno.config.EnableMethodCache;
//import plus.easydo.push.server.netty.CoordinationNettyServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author yuzhanfeng
 */
@EnableScheduling
@EnableAsync
@EnableMethodCache(basePackages = "plus.easydo")
@EnableFeignClients(basePackages = {"plus.easydo"})
@SpringBootApplication
@MapperScan(basePackages = {"plus.easydo.*.mapper"})
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
