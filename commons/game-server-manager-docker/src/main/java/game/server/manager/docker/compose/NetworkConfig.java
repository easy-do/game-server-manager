package game.server.manager.docker.compose;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author laoyu
 * @version 1.0
 * @description 网络详细设置
 * @date 2023/5/14
 */
@NoArgsConstructor
@Data
public class NetworkConfig {

    /** CIDR格式的子网，表示一个网段  172.28.0.0/16 */
    @JsonProperty("subnet")
    private String subnet;

    /** 从中分配容器 IP 的 IP 范围 172.28.5.0/24 */
    @JsonProperty("ip_range")
    private String ip_range;

    /** 主子网的 IPv4 或 IPv6 网关 172.28.5.254 */
    @JsonProperty("gateway")
    private String gateway;

    /** 网络驱动程序使用的辅助 IPv4 或 IPv6 地址，作为从主机名到 IP 的映射
     host1: 172.28.1.5
     host2: 172.28.1.6
     host3: 172.28.1.7
     */
    @JsonProperty("aux_addresses")
    private Map<String,String> aux_addresses;
}
