package plus.easydo.server.config.oss.local.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 本地存储配置
 * @author laoyu
 * @version 1.0
 */
@Data
@ConfigurationProperties(prefix = "oss.local")
public class LocalOssProperties {

    /**是否开启*/
    private Boolean enable;

    /**存储路径*/
    private String storePath = "/defaultOssPath/";


    /**默认分组名*/
    private String defaultGroupName = "defaultGroup";


}
