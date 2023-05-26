package plus.easydo.docker.compose;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author laoyu
 * @version 1.0
 * @description 为给定设备上的读/写操作设置每秒字节数限制
 * @date 2023/5/14
 */
@NoArgsConstructor
@Data
public class DeviceBps {

    /** path：定义受影响设备的符号路径。/dev/sdb */
    @JsonProperty("path")
    private String path;

    /** rate：作为表示字节数的整数值或作为表示字节值的字符串。 '12mb' */
    @JsonProperty("rate")
    private String rate;
}
