package plus.easydo.uc;

import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author yuzhanfeng
 */
@EnableAsync
@EnableMethodCache(basePackages = "plus.easydo")
@SpringBootApplication
@ComponentScan(basePackages = {"plus.easydo.**"})
@MapperScan(basePackages = {"plus.easydo.*.mapper"})
public class GameServerManagerUcApplication {

    public static void main(String[] args) {
        SpringApplication.run(GameServerManagerUcApplication.class, args);
    }

}
