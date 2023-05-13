package game.server.manager.docker.compose;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @author laoyu
 * @version 1.0
 * @description 网络设置
 * @date 2023/5/14
 */
@NoArgsConstructor
@Data
public class Networks {


    /** 指定应将哪个驱动程序用于该网络 overlay */
    @JsonProperty("driver")
    private String driver;

    /** 是否外部网络 */
    @JsonProperty("external")
    private Boolean external;

    /** host使用主机的网络堆栈 none禁用网络 */
    @JsonProperty("name")
    private String name;

    /** 指定一个选项列表作为键值对传递给该网络的驱动程序 */
    @JsonProperty("driver_opts")
    private Map<String,Object> driverOpts;

    /** 如果attachable设置为true，那么除了服务之外，独立容器应该能够附加到这个网络。如果独立容器连接到网络，它可以与服务和其他也连接到网络的独立容器通信。*/
    @JsonProperty("attachable")
    private Boolean attachable;

    /** 在此网络上启用 IPv6 网络。 */
    @JsonProperty("enable_ipv6")
    private Boolean enableIpv6;

    /** 指定自定义 IPAM 配置 */
    @JsonProperty("ipam")
    private Ipam ipam;

    /** 当设置为 时true，允许创建一个外部隔离的网络。 */
    @JsonProperty("internal")
    private Boolean internal;

    @NoArgsConstructor
    @Data
    public static class Ipam {

        /** 自定义 IPAM 驱动程序，而不是默认的。 */
        @JsonProperty("driver")
        private String driver;

        /** 详细设置 */
        @JsonProperty("config")
        private List<NetworkConfig> config;

        /** 作为键值映射的特定于驱动程序的选项 */
        Map<String,Object> options;
    }
}
