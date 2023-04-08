package game.server.manager.client.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @author laoyu
 * @version 1.0
 * @description 创建镜像参数封装
 * @date 2022/11/19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateContainerDto {

    private String image;

    private String name;

    private Map<String,String> env;

    /**
     * 标准输出
     */
    private Boolean attachStdin = Boolean.TRUE;

    /**
     * 标准输入
     */
    private Boolean stdinOpen = Boolean.TRUE;

    /**
     * 开启终端？
     */
    private Boolean tty = Boolean.TRUE;

    /**
     * host模式	-–net=host	容器和宿主机共享 Network namespace。
     * container模式	–-net={id}	容器和另外一个容器共享 Network namespace。 kubernetes 中的pod就是多个容器共享一个 Network namespace。
     * none模式	–-net=none	容器有独立的Network namespace，但并没有对其进行任何网络设置，如分配 veth pair 和网桥连接，配置IP等。
     * bridge模式	-–net=bridge	默认为该模式，通过 -p 指定端口映射。
     */
    private String networkMode = "bridge";

    private Boolean privileged;

    private List<BindDto> binds;

    /** 暴露所有端口 */
    private Boolean publishAllPorts;

    /** 暴露端口 */
    private List<PortBindDto> portBinds;

    /**连接*/
    private String links;

    /** 命令 */
    private String cmd;

    /** 标签 */
    private String labels;

    /** CPU限制 */
    private Long nanoCPUs;

    /** 内存限制 */
    private Long memory;

    /** 共享内存限制 */
    private Long shmSize;

    /** 交换内存限制 */
    private Long memorySwap;

    /** 重启策略 */
    private String restartPolicy;
}
