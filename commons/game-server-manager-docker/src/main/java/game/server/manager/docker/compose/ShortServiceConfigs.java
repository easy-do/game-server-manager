package game.server.manager.docker.compose;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author laoyu
 * @version 1.0
 * @description
 * 短语法:
 * 短语法变体仅指定配置名称。这将授予容器访问配置的权限并将其安装在/<config_name> 容器内。源名称和目标安装点都设置为配置名称。
 *  my_config:
 *    file: ./my_config.txt
 *  my_other_config:
 *    external: true
 * @date 2023/5/14
 */
@NoArgsConstructor
@Data
public class ShortServiceConfigs {

    @JsonProperty("configs")
    private String file;

    @JsonProperty("configs")
    private Boolean external;

}
