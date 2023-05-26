package plus.easydo.docker.compose;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author laoyu
 * @version 1.0
 * @description 为给定设备上的读/写操作设置每秒操作数限制
 * @date 2023/5/14
 */

@NoArgsConstructor
@Data
public class DeviceIops{

    /** path：定义受影响设备的符号路径。/dev/sdb */
    @JsonProperty("path")
    private String path;

    /** rate：作为表示每秒允许的操作数的整数值。 30 */
    @JsonProperty("rate")
    private Integer rate;
}
