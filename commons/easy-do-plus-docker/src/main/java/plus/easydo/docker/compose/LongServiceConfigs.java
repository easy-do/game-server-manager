package plus.easydo.docker.compose;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author laoyu
 * @version 1.0
 * @description
 *          长语法:
 *          长语法为如何在服务的任务容器中创建配置提供了更多粒度。
 *          source：平台中存在的配置名称。
 *          target：要挂载到服务的任务容器中的文件的路径和名称。如果未指定，则默认为/<source>。
 *          uid和gid：在服务的任务容器中拥有已安装配置文件的数字 UID 或 GID。未指定时的默认值为 USER 运行容器。
 *          mode：以八进制表示法在服务的任务容器中安装的文件的权限。默认值是世界可读的 ( 0444)。可写位必须被忽略。可以设置可执行位。
 * services:
 *   redis:
 *     image: redis:latest
 *     configs:
 *       - source: my_config
 *         target: /redis_config
 *         uid: "103"
 *         gid: "103"
 *         mode: 0440
 * configs:
 *   my_config:
 *     external: true
 *   my_other_config:
 *     external: true
 *
 * @date 2023/5/14
 */
@NoArgsConstructor
@Data
public class LongServiceConfigs {

    /** 平台中存在的配置名称。 */
    @JsonProperty("source")
    private String source;

    /** 要挂载到服务的任务容器中的文件的路径和名称。如果未指定，则默认为/<source>。。。 */
    @JsonProperty("target")
    private String target;

    /** 在服务的任务容器中拥有已安装配置文件的数字 UID 或 GID。未指定时的默认值为 USER 运行容器。。 */
    @JsonProperty("uid")
    private String uid;

    /** 在服务的任务容器中拥有已安装配置文件的数字 UID 或 GID。未指定时的默认值为 USER 运行容器。。 */
    @JsonProperty("gid")
    private String gid;

    /** 以八进制表示法在服务的任务容器中安装的文件的权限。默认值是世界可读的 ( 0444)。可写位必须被忽略。可以设置可执行位。。 */
    @JsonProperty("mode")
    private String mode;

}
