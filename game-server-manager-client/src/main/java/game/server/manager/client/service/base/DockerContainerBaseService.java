package game.server.manager.client.service.base;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.text.StrBuilder;
import cn.hutool.core.util.RuntimeUtil;
import cn.hutool.system.oshi.OshiUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.ListContainersCmd;
import com.github.dockerjava.api.command.LogContainerCmd;
import com.github.dockerjava.api.command.RemoveContainerCmd;
import com.github.dockerjava.api.command.RenameContainerCmd;
import com.github.dockerjava.api.command.RestartContainerCmd;
import com.github.dockerjava.api.command.StartContainerCmd;
import com.github.dockerjava.api.command.StopContainerCmd;
import com.github.dockerjava.api.model.Bind;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.Frame;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.Link;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.RestartPolicy;
import com.google.common.collect.Maps;
import com.sun.jna.Platform;
import game.server.manager.client.config.JacksonObjectMapper;
import game.server.manager.client.config.SystemUtils;
import game.server.manager.client.model.BindDto;
import game.server.manager.client.model.CreateContainerDto;
import game.server.manager.client.model.LinkDto;
import game.server.manager.client.model.PortBindDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author laoyu
 * @version 1.0
 * @description docker容器相关
 * @date 2022/11/21
 */
@Slf4j
@Component
public class DockerContainerBaseService {


    @Autowired
    private JacksonObjectMapper mapper;

    @Autowired
    private SystemUtils systemUtils;

    /**
     * 容器列表
     *
     * @param dockerClient dockerClient
     * @return java.util.List<com.github.dockerjava.api.model.Container>
     * @author laoyu
     * @date 2023/3/19
     */
    public List<Container> containerList(DockerClient dockerClient) {
        ListContainersCmd listContainersCmd = dockerClient.listContainersCmd();
        listContainersCmd.withShowAll(true);
        return listContainersCmd.exec();
    }


    /**
     * 启动容器
     *
     * @param dockerClient dockerClient
     * @param containerId containerId
     * @return java.lang.Void
     * @author laoyu
     * @date 2023/3/19
     */
    public Void startContainer(DockerClient dockerClient, String containerId) {
        StartContainerCmd startContainerCmd = dockerClient.startContainerCmd(containerId);
        return startContainerCmd.exec();
    }

    /**
     * 重启容器
     *
     * @param dockerClient dockerClient
     * @param containerId containerId
     * @return java.lang.Void
     * @author laoyu
     * @date 2023/3/19
     */
    public Void restartContainer(DockerClient dockerClient, String containerId) {
        RestartContainerCmd restartContainerCmd = dockerClient.restartContainerCmd(containerId);
        return restartContainerCmd.exec();
    }

    /**
     * 停止容器
     *
     * @param dockerClient dockerClient
     * @param containerId containerId
     * @return java.lang.Void
     * @author laoyu
     * @date 2023/3/19
     */
    public Void stopContainer(DockerClient dockerClient, String containerId) {
        StopContainerCmd stopContainerCmd = dockerClient.stopContainerCmd(containerId);
        return stopContainerCmd.exec();
    }

    /**
     * 重命名容器
     *
     * @param dockerClient dockerClient
     * @param containerId containerId
     * @param name name
     * @return java.lang.Void
     * @author laoyu
     * @date 2023/3/19
     */
    public Void renameContainer(DockerClient dockerClient, String containerId, String name) {
        RenameContainerCmd releaseContainerCmd = dockerClient.renameContainerCmd(containerId);
        releaseContainerCmd.withName(name);
        return releaseContainerCmd.exec();
    }

    /**
     * 删除容器
     *
     * @param dockerClient dockerClient
     * @param containerId containerId
     * @return java.lang.Void
     * @author laoyu
     * @date 2023/3/19
     */
    public Void removeContainer(DockerClient dockerClient, String containerId) {
        RemoveContainerCmd removeContainerCmd = dockerClient.removeContainerCmd(containerId);
        return removeContainerCmd.exec();
    }

    /**
     * 查看日志
     *
     * @param dockerClient dockerClient
     * @param containerId containerId
     * @return java.lang.String
     * @author laoyu
     * @date 2023/3/19
     */
    public String logContainer(DockerClient dockerClient, String containerId) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        log.info("Docker logContainer start {}", containerId);
        StrBuilder strBuilder = CharSequenceUtil.strBuilder();
        LogContainerCmd logContainerCmd = dockerClient.logContainerCmd(containerId);
        logContainerCmd.withStdOut(true).withStdErr(true).withTail(500);
        ResultCallback.Adapter<Frame> result = logContainerCmd.exec(new ResultCallback.Adapter<Frame>() {
            @Override
            public void onNext(Frame frame) {
                strBuilder.append(new String(frame.getPayload()));
            }
        });
        result.awaitCompletion();
        log.info("Docker logContainer end {},{}", containerId,System.currentTimeMillis() - startTime);
        return strBuilder.toString();
    }

    public ResultCallback.Adapter<Frame> logContainer(DockerClient dockerClient, String containerId, ResultCallback.Adapter<Frame> resultCallback) {
        LogContainerCmd logContainerCmd = dockerClient.logContainerCmd(containerId);
        logContainerCmd.withStdOut(true).withStdErr(true).withTail(500);
        return logContainerCmd.exec(resultCallback);
    }


    /**
     * 创建镜像
     *
     * @param dockerClient dockerClient
     * @param createContainerDto createContainerDto
     * @return com.github.dockerjava.api.command.CreateContainerResponse
     * @author laoyu
     * @date 2023/3/19
     */
    public CreateContainerResponse createContainer(DockerClient dockerClient, CreateContainerDto createContainerDto) {
        CreateContainerCmd createContainerCmd = dockerClient.createContainerCmd(createContainerDto.getImage());
        //容器名称
        if(CharSequenceUtil.isNotEmpty(createContainerDto.getName())){
            createContainerCmd.withName(createContainerDto.getName());
        }
        //命令
        if(CharSequenceUtil.isNotEmpty(createContainerDto.getCmd())){
            createContainerCmd.withCmd(createContainerDto.getCmd().split(","));
        }
        //标签
        if(Objects.nonNull(createContainerDto.getLabels())){
            List<String> lableList = CharSequenceUtil.split(createContainerDto.getLabels(), ",");
            Map<String,String> labelMap = Maps.newHashMapWithExpectedSize(lableList.size());
            lableList.forEach(labelStr->{
                List<String> label = CharSequenceUtil.split(labelStr, "=");
                if(label.size() == 2){
                    labelMap.put(label.get(0),label.get(1));
                }
            });
            if(!labelMap.isEmpty()){
                createContainerCmd.withLabels(labelMap);
            }
        }
        //标准输出
        createContainerCmd.withAttachStdin(createContainerDto.getAttachStdin());
        //标准输入
        createContainerCmd.withStdinOpen(createContainerDto.getAttachStdin());
        //开启终端？
        createContainerCmd.withTty(createContainerDto.getTty());
        //变量
        withEnvs(createContainerCmd, createContainerDto);
        //主机设置----------------------------------------------------
        HostConfig hostConfig = HostConfig.newHostConfig();
        //CPU限制
        if(Objects.nonNull(createContainerDto.getNanoCPUs())){
            hostConfig.withNanoCPUs(createContainerDto.getNanoCPUs());
        }
        //内存限制
        if(Objects.nonNull(createContainerDto.getMemory())){
            hostConfig.withMemory(createContainerDto.getMemory() * 1024 * 1024);
        }
        //共享内存限制
        if(Objects.nonNull(createContainerDto.getShmSize())){
            hostConfig.withShmSize(createContainerDto.getShmSize() * 1024 * 1024);
        }
        //交换内存限制
        if(Objects.nonNull(createContainerDto.getMemorySwap())){
            hostConfig.withMemorySwap(createContainerDto.getMemorySwap() * 1024 * 1024);
        }
        //重启策略
        if(CharSequenceUtil.isNotEmpty(createContainerDto.getRestartPolicy())){
            hostConfig.withRestartPolicy(RestartPolicy.parse(createContainerDto.getRestartPolicy()));
        }
        //网络模式
        hostConfig.withNetworkMode(createContainerDto.getNetworkMode());
        //绑定目录
        withBinds(hostConfig, createContainerDto);
        //暴露容器端口
        withExposedPorts(createContainerCmd, createContainerDto);
        //端口设置
        if(Objects.nonNull(createContainerDto.getPublishAllPorts()) && createContainerDto.getPublishAllPorts()){
            //发布所有端口
            hostConfig.withPublishAllPorts(true);
        }else {
            //发布指定端口
            withPortBindings(hostConfig, createContainerDto);
        }
        //是否特权模式
        hostConfig.withPrivileged(createContainerDto.getPrivileged());
        //连接容器 可使用别名连接容器内部服务
        withLinks(hostConfig, createContainerDto);
        //应用主机配置
        createContainerCmd.withHostConfig(hostConfig);

        try {
            log.info("Docker createContainer {}", mapper.writeValueAsString(createContainerDto));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return createContainerCmd.exec();
    }

    private void withEnvs(CreateContainerCmd createContainerCmd, CreateContainerDto createContainerDto) {
        Map<String, String> env = createContainerDto.getEnv();
        if (Objects.nonNull(env)) {
            List<String> envs = new ArrayList<>();
            env.forEach((key, value) -> envs.add(key + "=" + value));
            createContainerCmd.withEnv(envs);
        }
    }

    private void withBinds(HostConfig hostConfig, CreateContainerDto createContainerDto) {
        List<BindDto> bindDtoList = createContainerDto.getBinds();

        String type = systemUtils.getClientType();
        if (Objects.nonNull(bindDtoList) && !bindDtoList.isEmpty()) {
            List<Bind> binds = new ArrayList<>();
            bindDtoList.forEach(bindDto -> {
                String localPath = bindDto.getLocalPath();
                if(type.equals("docker")){
                    //容器模式
                    boolean isHost = FileUtil.isDirectory("/host");
                    if(isHost){
                        //存在宿主机映射目录 并且文件夹不存在
                        if(!FileUtil.isDirectory("/host"+localPath)){
                            RuntimeUtil.execForStr("chroot /host mkdir "+localPath);
                            RuntimeUtil.execForStr("chroot /host chmod -R 777 "+localPath);
                        }
                    }
                }else {
                    //宿主机模式
                    if (Platform.isMac() || Platform.isLinux()){
                        if(!FileUtil.isDirectory(localPath)){
                            RuntimeUtil.execForStr("mkdir "+localPath);
                            RuntimeUtil.execForStr("chmod -R 777 "+localPath);
                        }
                    }
                }
                binds.add(Bind.parse(bindDto.getLocalPath() + ":" + bindDto.getContainerPath()));
            });
            hostConfig.withBinds(binds);
        }
    }

    private void withPortBindings(HostConfig hostConfig, CreateContainerDto createContainerDto) {
        List<PortBindDto> portBindDtoList = createContainerDto.getPortBinds();
        if (Objects.nonNull(portBindDtoList) && !portBindDtoList.isEmpty()) {
            List<PortBinding> binds = new ArrayList<>();
            portBindDtoList.forEach(proPortBindDto -> binds.add(PortBinding.parse(proPortBindDto.getLocalPort() + ":" + proPortBindDto.getContainerPort()+ "/" + proPortBindDto.getProtocol())));
            hostConfig.withPortBindings(binds);
        }
    }

    private void withExposedPorts(CreateContainerCmd createContainerCmd, CreateContainerDto createContainerDto) {
        List<PortBindDto> portBindDtoList = createContainerDto.getPortBinds();
        List<ExposedPort> exposedPorts = new ArrayList<>();
        if (Objects.nonNull(portBindDtoList) && !portBindDtoList.isEmpty()) {
            portBindDtoList.forEach(portBindDto -> exposedPorts.add(ExposedPort.parse(portBindDto.getContainerPort() + "/" + portBindDto.getProtocol())));
            createContainerCmd.withExposedPorts(exposedPorts);
        }
    }

    private void withLinks(HostConfig hostConfig, CreateContainerDto createContainerDto) {
        String link = createContainerDto.getLinks();
        if (Objects.nonNull(link) && !link.isEmpty()) {
            List<String> links = CharSequenceUtil.split(link, ",");
            ArrayList<Link> withLinks = new ArrayList<>();
            links.forEach(linkStr -> withLinks.add(Link.parse(linkStr)));
            hostConfig.withLinks(withLinks);
        }
    }
}
