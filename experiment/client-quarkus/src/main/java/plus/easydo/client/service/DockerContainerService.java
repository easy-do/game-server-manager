package plus.easydo.client.service;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.LogContainerCmd;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Frame;
import plus.easydo.client.contants.ClientSocketTypeEnum;
import plus.easydo.client.model.CreateContainerDto;
import plus.easydo.client.server.SyncServer;
import plus.easydo.client.service.base.DockerContainerBaseService;
import plus.easydo.client.utils.DockerUtils;
import lombok.extern.slf4j.Slf4j;
import javax.inject.Inject;
import javax.enterprise.context.ApplicationScoped;

import java.util.List;

/**
 * @author laoyu
 * @version 1.0
 * @description docker容器相关
 * @date 2022/11/21
 */
@Slf4j
@ApplicationScoped
public class DockerContainerService {



    @Inject
    DockerContainerBaseService dockerContainerBaseService;

    @Inject
    SyncServer syncServer;


    /**
     * 容器列表
     *
     * @return java.util.List<com.github.dockerjava.api.model.Container>
     * @author laoyu
     * @date 2022/11/12
     */
    public List<Container> containerList() {
        log.info("Docker containerList");
        return dockerContainerBaseService.containerList(DockerUtils.creteDockerClient());
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
        return dockerContainerBaseService.startContainer(DockerUtils.creteDockerClient(),containerId);
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
        return dockerContainerBaseService.restartContainer(DockerUtils.creteDockerClient(),containerId);
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
        return dockerContainerBaseService.stopContainer(DockerUtils.creteDockerClient(),containerId);
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
        return dockerContainerBaseService.renameContainer(DockerUtils.creteDockerClient(),containerId,name);

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
        return dockerContainerBaseService.removeContainer(DockerUtils.creteDockerClient(),containerId);
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
        return dockerContainerBaseService.logContainer(DockerUtils.creteDockerClient(),containerId);
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
        DockerClient dockerClient = DockerUtils.creteDockerClient();
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
            syncServer.unLock(messageId);
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
       return dockerContainerBaseService.createContainer(DockerUtils.creteDockerClient(),createContainerDto);
    }
}
