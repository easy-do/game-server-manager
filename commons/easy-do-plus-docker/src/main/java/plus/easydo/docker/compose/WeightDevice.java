package plus.easydo.docker.compose;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author laoyu
 * @version 1.0
 * @description 权重设备
 * @date 2023/5/14
 */
@NoArgsConstructor
@Data
public class WeightDevice {

    /** 定义受影响设备的符号路径。 */
    @JsonProperty("path")
    private String path;

    /** 10 到 1000 之间的整数值。 */
    @JsonProperty("rate")
    private Integer rate;
}
