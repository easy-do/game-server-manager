package game.server.manager.docker.client.service;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.text.StrBuilder;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.api.command.*;
import com.github.dockerjava.api.model.*;
import game.server.manager.docker.model.BindDto;
import game.server.manager.docker.model.CreateContainerDto;
import game.server.manager.docker.model.LinkDto;
import game.server.manager.docker.model.PortBindDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author laoyu
 * @version 1.0
 * @description docker service
 * @date 2022/11/19
 */
@Slf4j
@Component
public class DockerService {

    @Resource
    private DockerClient dockerClient;


    /**
     * ping
     *
     * @author laoyu
     * @date 2022/11/13
     */
    public Void ping() {
        log.info("Docker ping");
        PingCmd pingCmd = dockerClient.pingCmd();
        return pingCmd.exec();
    }

    public Info info() {
        log.info("Docker info");
        InfoCmd infoCmd = dockerClient.infoCmd();
        return infoCmd.exec();
    }

    public Version version() {
        log.info("Docker info");
        VersionCmd versionCmd = dockerClient.versionCmd();
        return versionCmd.exec();
    }

    /**
     * 获取镜像列表
     *
     * @return java.util.List<com.github.dockerjava.api.model.Image>
     * @author laoyu
     * @date 2022/11/12
     */
    public List<Image> listImages() {
        log.info("Docker listImages");
        ListImagesCmd listImagesCmd = dockerClient.listImagesCmd();
        return listImagesCmd.exec();
    }


    /**
     * 删除镜像
     *
     * @param imageId imageId
     * @return java.lang.Void
     * @author laoyu
     * @date 2022/11/19
     */
    public Void removeImage(String imageId) {
        log.info("Docker removeImage {}", imageId);
        RemoveImageCmd removeImageCmd = dockerClient.removeImageCmd(imageId);
        return removeImageCmd.exec();
    }

    /**
     * 容器列表
     *
     * @return java.util.List<com.github.dockerjava.api.model.Container>
     * @author laoyu
     * @date 2022/11/12
     */
    public List<Container> containerList() {
        log.info("Docker containerList");
        ListContainersCmd listContainersCmd = dockerClient.listContainersCmd();
        listContainersCmd.withShowAll(true);
        return listContainersCmd.exec();
    }


    /**
     * 启动容器
     *
     * @param containerId containerId
     * @return java.lang.Void
     * @author laoyu
     * @date 2022/11/19
     */
    public Void startContainer(String containerId) {
        log.info("Docker startContainer {}", containerId);
        StartContainerCmd startContainerCmd = dockerClient.startContainerCmd(containerId);
        return startContainerCmd.exec();
    }

    /**
     * 重启容器
     *
     * @param containerId containerId
     * @return java.lang.Void
     * @author laoyu
     * @date 2022/11/19
     */
    public Void restartContainer(String containerId) {
        log.info("Docker restartContainer {}", containerId);
        RestartContainerCmd restartContainerCmd = dockerClient.restartContainerCmd(containerId);
        return restartContainerCmd.exec();
    }

    /**
     * 停止容器
     *
     * @param containerId containerId
     * @return java.lang.Void
     * @author laoyu
     * @date 2022/11/19
     */
    public Void stopContainer(String containerId) {
        log.info("Docker stopContainer {}", containerId);
        StopContainerCmd stopContainerCmd = dockerClient.stopContainerCmd(containerId);
        return stopContainerCmd.exec();
    }

    /**
     * 重命名容器
     *
     * @param containerId containerId
     * @param name        name
     * @return java.lang.Void
     * @author laoyu
     * @date 2022/11/19
     */
    public Void renameContainer(String containerId, String name) {
        log.info("Docker renameContainer {},{}", containerId, name);
        RenameContainerCmd releaseContainerCmd = dockerClient.renameContainerCmd(containerId);
        releaseContainerCmd.withName(name);
        return releaseContainerCmd.exec();
    }

    /**
     * 删除容器
     *
     * @param containerId containerId
     * @return java.lang.Void
     * @author laoyu
     * @date 2022/11/19
     */
    public Void removeContainer(String containerId) {
        log.info("Docker removeContainer {}", containerId);
        RemoveContainerCmd removeContainerCmd = dockerClient.removeContainerCmd(containerId);
        return removeContainerCmd.exec();
    }

    /**
     * 查看日志
     *
     * @param containerId  containerId
     * @return java.lang.Void
     * @author laoyu
     * @date 2022/11/20
     */
    public String logContainer(String containerId) {
        long startTime = System.currentTimeMillis();
        log.info("Docker logContainer start {}", containerId);
        final boolean[] closed = {false};
        StrBuilder strBuilder = CharSequenceUtil.strBuilder();
        LogContainerCmd logContainerCmd = dockerClient.logContainerCmd(containerId);
        logContainerCmd.withStdOut(true).withStdErr(true);
        logContainerCmd.exec(new ResultCallback.Adapter<Frame>() {
            @Override
            public void onNext(Frame frame) {
                strBuilder.append(new String(frame.getPayload()));
            }

            @Override
            public void close() throws IOException {
                closed[0] = true;
                super.close();
            }
        });
        while (!closed[0]) {
        }
        log.info("Docker logContainer end {},{}", containerId,System.currentTimeMillis() - startTime);
        return strBuilder.toString();
    }

    /**
     * 创建镜像
     *
     * @param createContainerDto createContainerDto
     * @return com.github.dockerjava.api.command.CreateContainerResponse
     * @author laoyu
     * @date 2022/11/19
     */
    public CreateContainerResponse createContainer(CreateContainerDto createContainerDto) {
        CreateContainerCmd createContainerCmd = dockerClient.createContainerCmd(createContainerDto.getImage());
        //容器名称
        createContainerCmd.withName(createContainerDto.getName());
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
        //网络模式
        hostConfig.withNetworkMode(createContainerDto.getNetworkMode());
        //绑定目录
        withBinds(hostConfig, createContainerDto);
        //绑定端口
        withPortBindings(hostConfig, createContainerDto);
        //是否特权模式
        hostConfig.withPrivileged(createContainerDto.getPrivileged());
        //连接容器 可使用别名连接容器内部服务
        withLinks(hostConfig, createContainerDto);
        //应用主机配置
        createContainerCmd.withHostConfig(hostConfig);
        //暴露容器端口
        withExposedPorts(createContainerCmd, createContainerDto);
        log.info("Docker createContainer {}", JSON.toJSONString(createContainerDto));
        return createContainerCmd.exec();
    }

    private void withEnvs(CreateContainerCmd createContainerCmd, CreateContainerDto createContainerDto) {
        JSONObject env = createContainerDto.getEnv();
        if (Objects.nonNull(env)) {
            List<String> envs = new ArrayList<>();
            env.forEach((key, value) -> envs.add(key + ":" + value));
            createContainerCmd.withEnv(envs);
        }
    }

    private void withBinds(HostConfig hostConfig, CreateContainerDto createContainerDto) {
        List<BindDto> bindDtoList = createContainerDto.getBinds();
        if (Objects.nonNull(bindDtoList) && !bindDtoList.isEmpty()) {
            List<Bind> binds = new ArrayList<>();
            bindDtoList.forEach(bindDto -> binds.add(Bind.parse(bindDto.getLocalPath() + ":" + bindDto.getContainerPath())));
            hostConfig.withBinds(binds);
        }
    }

    private void withPortBindings(HostConfig hostConfig, CreateContainerDto createContainerDto) {
        List<PortBindDto> portBindDtoList = createContainerDto.getPortBinds();
        if (Objects.nonNull(portBindDtoList) && !portBindDtoList.isEmpty()) {
            List<PortBinding> binds = new ArrayList<>();
            portBindDtoList.forEach(proPortBindDto -> binds.add(PortBinding.parse(proPortBindDto.getLocalPort() + ":" + proPortBindDto.getContainerPort())));
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
        List<LinkDto> linkList = createContainerDto.getLinks();
        if (Objects.nonNull(linkList) && !linkList.isEmpty()) {
            List<Link> links = new ArrayList<>();
            linkList.forEach(linkDto -> links.add(Link.parse(linkDto.getName() + ":" + linkDto.getAlis())));
            hostConfig.withLinks(links);
        }
    }
}
