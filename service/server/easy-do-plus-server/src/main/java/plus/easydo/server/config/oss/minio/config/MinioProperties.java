package plus.easydo.server.config.oss.minio.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * minio配置
 * @author laoyu
 * @version 1.0
 */
@Data
@ConfigurationProperties(prefix = "oss.minio")
public class MinioProperties {

    /**是否开启*/
    private Boolean enable;

    /**连接地址*/
    private String serverAddress;

    /**用户名*/
    private String accessKey;

    /**登录密码*/
    private String secretKey;

    /**默认bucket捅*/
    private String defaultBucket = "defaultBucket";




}
