package plus.easydo.push.client.model;

import com.github.dockerjava.api.model.Network;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author laoyu
 * @version 1.0
 * @description docker创建网络参数封装
 * @date 2023/4/1
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateNetworkDto {

    /** 网络名称 */
    private String name;
    /** 模式 */
    private String driver;
    /** 子网设置 */
    private Network.Ipam ipam;
    /** 参数 */
    private Map<String, String> options = new HashMap<>();
    /**  */
    private Boolean checkDuplicate;
    /** 内部网络 */
    private Boolean internal;
    /** 开启ipv6 */
    private Boolean enableIpv6;
    /** 非服务容器将能够连接到网络 */
    private Boolean attachable;
    /** 标签 */
    private Map<String, String> labels = new HashMap<>();
}
