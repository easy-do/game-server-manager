package plus.easydo;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;

/**
 * @author yuzhanfeng
 * @Date 2023-04-05 15:04
 * @Description 实现QuarkusApplication 可以打包jar命令行启动
 */
public class Application implements QuarkusApplication {
    @Override
    public int run(String... args) throws Exception {
        Quarkus.waitForExit();
        return 0;
    }
}
