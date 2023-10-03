package plus.easydo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author yuzhanfeng
 */
@EnableAsync
@SpringBootApplication
public class EasyDataApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasyDataApplication.class, args);
    }

}
