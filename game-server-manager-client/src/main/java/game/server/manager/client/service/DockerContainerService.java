package game.server.manager.client.service;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.text.StrBuilder;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.api.command.*;
import com.github.dockerjava.api.model.Bind;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.Frame;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.Link;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.PullResponseItem;
import game.server.manager.client.server.SyncServer;
import game.server.manager.common.enums.ClientSocketTypeEnum;
import game.server.manager.docker.model.BindDto;
import game.server.manager.docker.model.CreateContainerDto;
import game.server.manager.docker.model.LinkDto;
import game.server.manager.docker.model.PortBindDto;
import game.server.manager.docker.service.DockerContainerBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author laoyu
 * @version 1.0
 * @description docker容器相关
 * @date 2022/11/21
 */
@Slf4j
@Component
public class DockerContainerService {


    @Autowired(required = false)
    private DockerClient dockerClient;

    @Autowired
    private DockerContainerBaseService dockerContainerBaseService;

    @Resource
    private SyncServer syncServer;


    /**
     * 容器列表
     *
     * @return java.util.List<com.github.dockerjava.api.model.Container>
     * @author laoyu
     * @date 2022/11/12
     */
    public List<Container> containerList() {
        log.info("Docker containerList");
        return dockerContainerBaseService.containerList(dockerClient);
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
        return dockerContainerBaseService.startContainer(dockerClient,containerId);
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
        return dockerContainerBaseService.restartContainer(dockerClient,containerId);
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
        return dockerContainerBaseService.stopContainer(dockerClient,containerId);
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
        return dockerContainerBaseService.renameContainer(dockerClient,containerId,name);

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
        return dockerContainerBaseService.removeContainer(dockerClient,containerId);
    }

    /**
     * 查看日志
     *
     * @param containerId  containerId
     * @return java.lang.Void
     * @author laoyu
     * @date 2022/11/20
     */
    public String logContainer(String containerId) throws InterruptedException {
        return dockerContainerBaseService.logContainer(dockerClient,containerId);
    }


    /**
     * 查看日志
     *
     * @param messageId messageId
     * @param containerId containerId
     * @author laoyu
     * @date 2022/11/28
     */
    public void logContainer(String messageId, String containerId) {
        log.info("Docker logContainer {}", containerId);

        LogContainerCmd logContainerCmd = dockerClient.logContainerCmd(containerId);
        logContainerCmd.withStdOut(true).withStdErr(true).withTail(1000);


        ResultCallback.Adapter<Frame> callback = dockerContainerBaseService.logContainer(dockerClient, containerId, new ResultCallback.Adapter<>() {
            @Override
            public void onNext(Frame frame) {
                syncServer.sendMessage(ClientSocketTypeEnum.SYNC_RESULT, new String(frame.getPayload()));
            }
        });
        try {
            callback.awaitCompletion();
            syncServer.sendMessage(ClientSocketTypeEnum.SYNC_RESULT_END, "success");
        } catch (InterruptedException interruptedException) {
            log.error("执行查看镜像日志线程异常，{}", ExceptionUtil.getMessage(interruptedException));
            syncServer.sendMessage(ClientSocketTypeEnum.SYNC_RESULT_END, "客户端执行查看镜像日志线程异常：" + ExceptionUtil.getMessage(interruptedException));
        } finally {
            //释放锁
            syncServer.getClient().unLock(messageId);
        }
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
       return dockerContainerBaseService.createContainer(dockerClient,createContainerDto);
    }
}
