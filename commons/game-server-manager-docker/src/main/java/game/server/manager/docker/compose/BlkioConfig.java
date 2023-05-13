package game.server.manager.docker.compose;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author laoyu
 * @version 1.0
 * @description 定义一组配置选项来设置此服务的块 IO 限制。
 * @date 2023/5/14
 */
@NoArgsConstructor
@Data
public class BlkioConfig {

    /** 修改分配给该服务的带宽相对于其他服务的比例。采用 10 到 1000 之间的整数值，默认值为 500。 */
    @JsonProperty("weight")
    private Integer weight;

    /** 权重设备  按设备微调带宽分配。列表中的每一项都必须有两个键 */
    @JsonProperty("weight_device")
    private WeightDevice weight_device;

    /** 为给定设备上的读操作设置每秒字节数限制。列表中的每个项目必须有两个键 */
    @JsonProperty("device_read_bps")
    private DeviceBps device_read_bps;

    /** 为给定设备上的写操作设置每秒字节数限制。列表中的每个项目必须有两个键 */
    @JsonProperty("device_write_bps")
    private DeviceBps device_write_bps;

    /** 为给定设备上的读操作设置每秒操作数限制。列表中的每个项目必须有两个键 */
    @JsonProperty("device_read_iops")
    private DeviceIops device_read_iops;

    /** 为给定设备上的写操作设置每秒操作数限制。列表中的每个项目必须有两个键 */
    @JsonProperty("device_write_iops")
    private DeviceIops device_write_iops;

}
