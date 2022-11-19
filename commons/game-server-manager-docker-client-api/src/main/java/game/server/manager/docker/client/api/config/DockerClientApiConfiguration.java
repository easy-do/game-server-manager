package game.server.manager.docker.client.api.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author laoyu
 * @version 1.0
 * @description sdk配置类
 * @date 2022/11/19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DockerClientApiConfiguration {

    private String dateFormat = "yyyy-MM-dd";
    private String timeFormat = "HH:m:ss";
    private String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";
    private int connectTimeoutMillis = 1000;
    private int readTimeoutMillis = 1000;
    private long retryPeriod = 5000L;
    private int retryMaxAttempts = 1;
    private long retryMaxPeriod = 5000L;
}
