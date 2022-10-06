package game.server.manager.log.audit;

import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author yuzhanfeng
 */
@EnableMethodCache(basePackages = "game.server.manager")
@ComponentScan(basePackages = {"game.server.manager"})
@MapperScan(basePackages = {"game.server.manager.*.mapper"})
@SpringBootApplication
public class GameServerManagerLogAuditApplication {

    public static void main(String[] args) {
        SpringApplication.run(GameServerManagerLogAuditApplication.class, args);
    }

}
