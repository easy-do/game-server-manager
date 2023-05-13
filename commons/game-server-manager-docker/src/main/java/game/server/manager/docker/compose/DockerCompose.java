package game.server.manager.docker.compose;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author laoyu
 * @version 1.0
 * @description DockerCompose抽象对象
 * @link <a href="https://docs.docker.com/compose/compose-file">...</a>
 * @date 2023/5/14
 */

@NoArgsConstructor
@Data
public class DockerCompose {

    /** 版本 */
    @JsonProperty("version")
    private String version;

    /** 网络设置 */
    @JsonProperty("networks")
    private Map<String,Networks> networks;

    /** 服务设置 */
    @JsonProperty("services")
    private Map<String,Service> services;

    /** 全局配置 */
    @JsonProperty("configs")
    private Object configs;

    /** 卷设置 */
    @JsonProperty("volumes")
    private Object volumes;

    /** 敏感数据设置 */
    @JsonProperty("secrets")
    private Object secrets;

}
