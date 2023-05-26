package plus.easydo.docker.compose;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @author laoyu
 * @version 1.0
 * @description 服务设置
 * @date 2023/5/14
 */
@NoArgsConstructor
@Data
public class Service {
    /**
     image指定启动容器的图像。图像必须遵循开放容器规范 可寻址图像格式，如[<registry>/][<project>/]<image>[:<tag>|@<digest>].
     image: redis
     image: redis:5
     image: redis@sha256:0ed5d5928d4737458944eb604cc8509e245c3e19d02ad83935398bc4b991aac7
     image: library/redis
     image: docker.io/library/redis
     image: my_private.registry:5000/redis
     */
    @JsonProperty("image")
    private String image;

    /** 定义一组配置选项来设置此服务的块 IO 限制。 */
    private BlkioConfig blkioConfig;

    /** 定义服务容器的可用 CPU 数量。 */
    @JsonProperty("cpu_count")
    private Integer cpu_count;

    /** 定义可用 CPU 的可用百分比。 */
    @JsonProperty("cpu_percent")
    private Integer cpu_percent;

    /** 定义（作为整数值）服务容器相对于其他容器的 CPU 权重 */
    @JsonProperty("cpu_shares")
    private Integer cpu_shares;

    /** 当平台基于 Linux 内核时，允许 Compose 实现配置 CPU CFS（Completely Fair Scheduler）周期。 */
    @JsonProperty("cpu_period")
    private String cpu_period;

    /** 当平台基于 Linux 内核时，允许 Compose 实现配置 CPU CFS（完全公平调度程序）配额。 */
    @JsonProperty("cpu_quota")
    private String cpu_quota;

    /** 为支持实时调度程序的平台配置 CPU 分配参数。可以是以微秒为单位的整数值，也可以是持续时间。cpu_rt_runtime: '400ms' cpu_rt_runtime: 95000` */
    @JsonProperty("cpu_rt_runtime")
    private String cpu_rt_runtime;

    /** 为支持实时调度程序的平台配置 CPU 分配参数。可以是以微秒为单位的整数值，也可以是持续时间。cpu_rt_period: '1400us' cpu_rt_period: 11000` */
    @JsonProperty("cpu_rt_period")
    private String cpu_rt_period;

    /** 处理器 弃用：使用deploy.limits.cpus
     cpus定义分配给服务容器的（可能是虚拟的）CPU 的数量。这是一个小数。 0.000意味着没有限制。 */
    @JsonProperty("cpus")
    private String cpus;

    /** 电脑组  cpuset定义允许执行的显式 CPU。可以是范围0-3或列表0,1
     cap_add:
     - ALL
     */
    @JsonProperty("cpuset")
    private List<String> cpuSet;

    /**
     cap_drop指定要作为字符串删除的容器功能。
     cap_drop:
     - NET_ADMIN
     - SYS_ADMI
     */
    @JsonProperty("cap_drop")
    private List<String> cap_drop;

    /**
     cgroup指定要加入的 cgroup 命名空间。取消设置时，容器运行时决定选择要使用的 cgroup 命名空间（如果支持）。
     host: 在 Container runtime cgroup 命名空间中运行容器
     private: 在其自己的私有 cgroup 命名空间中运行容器
     */
    @JsonProperty("cgroup")
    private String cgroup;

    /**
     cgroup_parent为容器指定一个可选的父cgroup 。
     cgroup_parent: m-executor-abcd
     */
    @JsonProperty("cgroup_parent")
    private String cgroup_parent;

    /**
     覆盖容器镜像（即 Dockerfile 的）声明的默认命令CMD。
     command: bundle exec thin -p 3000
     该值也可以是一个列表，其方式类似于Dockerfile：
     command: [ "bundle", "exec", "thin", "-p", "3000" ]
     如果值为null，则必须使用图像中的默认命令。
     如果值为[]（空列表）或''（空字符串），必须忽略图像声明的默认命令，即覆盖为空。
     */
    @JsonProperty("command")
    private String command;

    /**
     configs 设置每个服务对配置的访问权限  ShortServiceConfigs/longServiceConfigs
     支持两种不同的语法变体。
     如果配置在平台上不存在或未在此Compose文件的configs部分中定义，则 Compose 实现必须报告错误。
     为配置定义了两种语法。为了保持符合本规范，实现必须支持这两种语法。实现必须允许在同一文档中同时使用短语法和长语法。
     */
    @JsonProperty("configs")
    private Object configs;

    /** 指定自定义容器名称 [a-zA-Z0-9][a-zA-Z0-9_.-]+ */
    @JsonProperty("container_name")
    private String container_name;


    /**
     credential_spec为托管服务帐户配置凭据规范。
     */
    @JsonProperty("credential_spec")
    private CredentialSpec credential_spec;


    /** 服务之间的启动和关闭依赖关系。 */
    @JsonProperty("depends_on")
    private Object depends_on;

    /** 为此容器定义设备 cgroup 规则列表。该格式与 Linux 内核在控制组设备白名单控制器中指定的格式相同。
     * device_cgroup_rules:
     *   - 'c 1:3 mr'
     *   - 'a 7:* rmw'
     * */
    @JsonProperty("device_cgroup_rules")
    private List<String> device_cgroup_rules;

    /**
     * 为创建的容器定义设备映射列表 HOST_PATH:CONTAINER_PATH[:CGROUP_PERMISSIONS]。
     devices:
     - "/dev/ttyUSB0:/dev/ttyUSB0"
     - "/dev/sda:/dev/xvda:rwm" */
    @JsonProperty("devices")
    private List<String> devices;

    /** 定义要在容器网络接口配置上设置的自定义 DNS 服务器。可以是单个值或列表。
     dns: 8.8.8.8
     dns:
     - 8.8.8.8
     - 9.9.9.9
     */
    @JsonProperty("dns")
    private Object dns;

    /** 列出要传递给容器的 DNS 解析器的自定义 DNS 选项（/etc/resolv.confLinux 上的文件）。
     dns_opt:
     - use-vc
     - no-tld-query
     */
    @JsonProperty("dns_opt")
    private List<String> dns_opt;

    /** 定义自定义 DNS 搜索域以在容器网络接口配置上设置。可以是单个值或列表。
     dns_search: example.com
     dns_search:
     - dc1.example.com
     - dc2.example.com
     */
    @JsonProperty("dns_search")
    private Object dns_search;

    /** 声明用于服务容器的自定义域名。必须是有效的 RFC 1123 主机名。 */
    @JsonProperty("domainname")
    private String domainname;

    /** entrypoint声明服务容器的默认入口点。这将覆盖ENTRYPOINT服务的 Dockerfile 中的指令。
     * 在其缩写形式中，该值可以定义为一个字符串：
     * entrypoint: /code/entrypoint.sh
     * 或者，该值也可以是一个列表，类似于 Dockerfile：
     * entrypoint:
     *   - php
     *   - -d
     *   - zend_extension=/usr/local/lib/php/extensions/no-debug-non-zts-20100525/xdebug.so
     *   - -d
     *   - memory_limit=-1
     *   - vendor/bin/phpunit
     */
    @JsonProperty("entrypoint")
    private Object entrypoint;

    /** 根据文件内容向容器添加环境变量。
     env_file: .env
     env_file也可以是一个列表。列表中的文件必须从上到下处理。对于在两个 env 文件中指定的相同变量，列表中最后一个文件的值必须保持不变。
     env_file:
     - ./a.env
     - ./b.env
     */
    @JsonProperty("env_file")
    private Object env_file;

    /** 定义容器中设置的环境变量
     * map语法：
     * environment:
     *   RACK_ENV: development
     *   SHOW: "true"
     *   USER_INPUT:
     * 数组语法：
     * environment:
     *   - RACK_ENV=development
     *   - SHOW=true
     *   - USER_INPUT
     * */
    @JsonProperty("environment")
    private Object environment;

    /** 定义从容器公开的端口。这些端口必须可供链接服务访问，并且不应发布到主机。只能指定内部容器端口。 */
    @JsonProperty("expose")
    private List<String> expose;


    /** 在当前文件或另一个文件中扩展另一个服务，可选择覆盖配置。您可以 extends在任何服务上与其他配置密钥一起使用 */
    @JsonProperty("extends")
    private Map<String,String> extendS;

    /** 定义容器的注解。annotations可以使用数组或映射。
     annotations:
     com.example.foo: bar

     annotations:
     - com.example.foo=bar
     */
    @JsonProperty("annotations")
    private Object annotations;

    /** 将服务容器链接到此 Compose 应用程序外部管理的服务。
     external_links定义要使用平台查找机制检索的现有服务的名称。SERVICE:ALIAS可以指定表单的别名。
     external_links:
     - redis
     - database:mysql
     - database:postgresql
     */
    @JsonProperty("external_links")
    private List<String> external_links;

    /** 将主机名映射添加到容器网络接口配置（/etc/hosts适用于 Linux）。 */
    @JsonProperty("extra_hosts")
    private Object extra_hosts;

    /** group_add指定容器内的用户必须是其中成员的附加组（按名称或编号）。 */
    @JsonProperty("group_add")
    private List<String> groupAdd;

    /** 声明运行的检查以确定此服务的容器是否“健康”。这会覆盖 由服务的 Docker 映像设置的HEALTHCHECK Dockerfile 指令。
     healthcheck:
     test: ["CMD", "curl", "-f", "http://localhost"]
     interval: 1m30s
     timeout: 10s
     retries: 3
     start_period: 40s
     */
    @JsonProperty("healthcheck")
    private Map<String,Object> healthcheck;

    /** 声明用于服务容器的自定义主机名。必须是有效的 RFC 1123 主机名 */
    @JsonProperty("hostname")
    private String hostname;

    /** init在转发信号和收割进程的容器内运行一个初始化进程（PID 1）。将此选项设置为true为服务启用此功能 */
    @JsonProperty("init")
    private Boolean init;

    /** ipc配置服务容器设置的IPC隔离模式。可用值是特定于平台的，但是 Compose 规范定义了特定的值，如果支持，必须按照描述的方式实现这些值：
     shareable这为容器提供了自己的私有 IPC 命名空间，并有可能与其他容器共享。
     service:{name}这使得容器加入另一个容器的 ( shareable) IPC 命名空间。
     ipc: "shareable"
     ipc: "service:[service name]
     */
    @JsonProperty("ipc")
    private String ipc;

    /** uts配置为服务容器设置的 UTS 命名空间模式。如果未指定，则由运行时决定分配 UTS 命名空间（如果支持）。可用值是：
     'host'这导致容器使用与主机相同的 UTS 命名空间。
     uts: "host
     */
    @JsonProperty("uts")
    private String uts;

    /** isolation指定容器的隔离技术。支持的值是特定于平台的。
     */
    @JsonProperty("isolation")
    private String isolation;

    /** 将元数据添加到容器。可以使用数组或映射。。
     */
    @JsonProperty("labels")
    private Object labels;

    /** 定义到另一个服务中容器的网络链接。要么指定服务名称和链接别名 ( SERVICE:ALIAS)，要么只指定服务名称。
     web:
     links:
     - db
     - db:database
     - redis
     */
    @JsonProperty("links")
    private List<String> links;

    /**  定义服务的日志记录配置。
     logging:
     driver: syslog
     options:
     syslog-address: "tcp://192.168.0.42:123"
     */
    @JsonProperty("logging")
    private Map<String,Object> logging;

    /**
     设置服务容器网络模式。可用值是特定于平台的，但是 Compose 规范定义了特定的值，如果支持，必须按照描述的方式实现这些值：
     none禁用所有容器网络
     host这使容器可以原始访问主机的网络接口
     service:{name}这使容器只能访问指定的服务
     network_mode: "host"
     network_mode: "none"
     network_mode: "service:[service name]"
     */
    @JsonProperty("network_mode")
    private Map<String,Object> network_mode;

    /**  networks定义服务容器所连接的网络，引用 顶级networks键下的条目。 */
    @JsonProperty("networks")
    private Object networks;

    /** 为服务容器设置 MAC 地址。 */
    @JsonProperty("mac_address")
    private String mac_address;

    /** 配置服务容器允许的共享内存（Linux 上的分区）的大小 */
    @JsonProperty("shm_size")
    private String shm_size;


    /**
     定义容器使用的匿名内存页的百分比（0 到 100 之间的值）。
     值为 0 将关闭匿名页面交换。
     值 100 将所有匿名页面设置为可交换。
     */
    @JsonProperty("mem_swappiness")
    private Integer mem_swappiness;

    /** 定义允许交换到磁盘的内存容器量  */
    @JsonProperty("memswap_limit")
    private Integer memswap_limit;


    /** 不会在内存不足的情况下终止容器 */
    @JsonProperty("oom_kill_disable")
    private Boolean oom_kill_disable;

    /** oom_score_adj 内存不足杀死的偏好。值必须在 [-1000,1000] 范围内。 */
    @JsonProperty("oom_score_adj")
    private String oom_score_adj;

    /** 为创建的容器设置 PID 模式。支持的值是特定于平台的 */
    @JsonProperty("pid")
    private String pid;

    /** pids_limit调整容器的 PID 限制。设置为 -1 表示不受限制的 PID。*/
    @JsonProperty("pids_limit")
    private String pids_limit;

    /** 使用语法定义此服务将在其上运行的目标平台容器os[/arch[/variant]]。os、arch和的值必须符合OCI Image Specvariant使用的约定。
     platform: darwin
     platform: windows/amd64
     platform: linux/arm64/v8
     */
    @JsonProperty("platform")
    private String platform;

    /** 将服务容器配置为以提升的权限运行。*/
    @JsonProperty("privileged")
    private Boolean privileged;

    /** 端口映射 支持长短语法 */
    @JsonProperty("ports")
    private Object ports;

    /** profiles定义要在其下启用的服务的命名配置文件列表。 [a-zA-Z0-9][a-zA-Z0-9_.-]+ */
    @JsonProperty("profiles")
    private String profiles;

    /**
     always: Compose 实现应该总是从注册表中提取图像。
     never: Compose 实现不应该从注册表中提取图像并且应该依赖于平台缓存的图像。如果没有缓存图像，则必须报告失败。
     missing：仅当图像在平台缓存中不可用时，Compose 实现才应该拉取图像。这应该是没有构建支持的 Compose 实现的默认选项。 if_not_present为了向后兼容，应该将其视为此值的别名
     build: Compose 实现应该构建图像。Compose 实现应该重建图像（如果已经存在）。
     如果pull_policy和build都存在，Compose 实现应该默认构建图像。Compose 实现可以在工具链中覆盖此行为。
     */
    @JsonProperty("pull_policy")
    private String pull_policy;

    /** 将服务容器配置为使用只读文件系统创建。*/
    @JsonProperty("read_only")
    private Boolean read_only;

    /** restart定义平台将应用于容器终止的策略。
     no：默认重启策略。在任何情况下都不重启容器。
     always：该策略总是重新启动容器，直到它被删除。
     on-failure：如果退出代码指示错误，则策略会重新启动容器。
     unless-stopped：无论退出代码如何，该策略都会重新启动容器，但会在服务停止或删除时停止重新启动。
     */
    @JsonProperty("restart")
    private String restart;

    /** 指定用于服务容器的运行时。 */
    @JsonProperty("runtime")
    private String runtime;

    /** 指定要为此服务部署的默认容器数。 */
    @JsonProperty("scale")
    private Integer scale;

    /** 在每个服务的基础上授予对机密定义的敏感数据的访问权限 */
    @JsonProperty("secrets")
    private Integer secrets;

    /** 覆盖每个容器的默认标签方案。 */
    @JsonProperty("security_opt")
    private List<String> security_opt;

    /** 配置服务容器以使用分配的标准输入运行。 */
    @JsonProperty("stdin_open")
    private Boolean stdin_open;

    /** 指定 Compose 实现在发送 SIGKILL 之前尝试停止容器时必须等待多长时间，如果它不处理 SIGTERM（或任何已指定的停止信号 stop_signal）。指定为持续时间。
     stop_grace_period: 1s
     stop_grace_period: 1m30s */
    @JsonProperty("stop_grace_period")
    private String stop_grace_period;


    /** 定义 Compose 实现必须用来停止服务容器的信号。如果未设置的容器被 Compose Implementation 通过发送停止SIGTERM。
     stop_signal: SIGUSR1 */
    @JsonProperty("stop_signal")
    private String stop_signal;

    /** 定义服务的存储驱动程序选项。
     storage_opt:
     size: '1G' */
    @JsonProperty("storage_opt")
    private String storage_opt;

    /** 定义要在容器中设置的内核参数。sysctls可以使用数组或映射。 */
    @JsonProperty("sysctls")
    private Object sysctls;

    /** tmpfs在容器内挂载一个临时文件系统。可以是单个值或列表。 */
    @JsonProperty("tmpfs")
    private Object tmpfs;

    /** 配置服务容器以使用 TTY 运行。 */
    @JsonProperty("tty")
    private Boolean tty;

    /** 覆盖容器的默认 ulimits。指定为单个限制的整数或软/硬限制的映射。 */
    @JsonProperty("ulimits")
    private Map<String,Object> ulimits;

    /** 覆盖用于运行容器进程的用户 */
    @JsonProperty("user")
    private String user;

    /** 为服务设置用户命名空间 */
    @JsonProperty("userns_mode")
    private String userns_mode;

    /** 定义服务容器必须可以访问的挂载主机路径或命名卷。 */
    @JsonProperty("volumes")
    private Object volumes;

    /** 从指定的目录覆盖容器的工作目录。 */
    @JsonProperty("working_dir")
    private String working_dir;

    /** 内存限制 */
    @JsonProperty("mem_limit")
    private String mem_limit;
}
