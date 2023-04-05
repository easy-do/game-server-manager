package plus.easydo;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

/**
 * @author yuzhanfeng
 * @Date 2023-04-05 14:59
 * @Description 实现main函数 可以打包jar命令行启动
 */

@QuarkusMain
public class ApplicationMain {

    public static void main(String[] args) {
        Quarkus.run(args);
    }

}
