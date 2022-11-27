package game.server.manager.server.service;

import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.Container;
import game.server.manager.common.result.R;
import game.server.manager.common.vo.UserInfoVo;
import game.server.manager.docker.model.CreateContainerDto;
import game.server.manager.server.websocket.handler.browser.SocketContainerLogData;

import java.util.List;

/**
 * @author laoyu
 * @version 1.0
 * @description 容器相关
 * @date 2022/11/19
 */
public interface DockerContainerService {

    /**
     * 获取容器列表
     *
     * @param dockerId dockerId
     * @return game.server.manager.common.result.R<java.util.List<com.github.dockerjava.api.model.Container>>
     * @author laoyu
     * @date 2022/11/19
     */
    R<List<Container>> containerList(String dockerId);

    /**
     * 启动容器
     *
     * @param dockerId dockerId
     * @param containerId containerId
     * @return game.server.manager.common.result.R<java.lang.Void>
     * @author laoyu
     * @date 2022/11/19
     */
    R<Void> startContainer(String dockerId, String containerId);

    /**
     * 重启容器
     *
     * @param dockerId dockerId
     * @param containerId containerId
     * @return game.server.manager.common.result.R<java.lang.Void>
     * @author laoyu
     * @date 2022/11/19
     */
    R<Void> restartContainer(String dockerId, String containerId);

    /**
     * 停止容器
     *
     * @param dockerId dockerId
     * @param containerId containerId
     * @return game.server.manager.common.result.R<java.lang.Void>
     * @author laoyu
     * @date 2022/11/19
     */
    R<Void> stopContainer(String dockerId, String containerId);

    /**
     * 删除容器
     *
     * @param dockerId dockerId
     * @param containerId containerId
     * @return game.server.manager.common.result.R<java.lang.Void>
     * @author laoyu
     * @date 2022/11/19
     */
    R<Void> removeContainer(String dockerId, String containerId);

    /**
     * 重命名容器
     *
     * @param dockerId dockerId
     * @param containerId containerId
     * @param name name
     * @return game.server.manager.common.result.R<java.lang.Void>
     * @author laoyu
     * @date 2022/11/19
     */
    R<Void> renameContainer(String dockerId, String containerId, String name);

    /**
     * 创建容器
     *
     * @param dockerId dockerId
     * @param createContainerDto createContainerDto
     * @return game.server.manager.common.result.R<com.github.dockerjava.api.command.CreateContainerResponse>
     * @author laoyu
     * @date 2022/11/19
     */
    R<CreateContainerResponse> createContainer(String dockerId, CreateContainerDto createContainerDto);

    /**
     * 查看容器日志
     *
     * @param dockerId dockerId
     * @param containerId containerId
     * @return game.server.manager.common.result.R<java.lang.String>
     * @author laoyu
     * @date 2022/11/20
     */
    R<String> logContainer(String dockerId, String containerId);

    /**
     * socket查看容器日志
     *
     * @param build build
     * @param userInfo userInfo
     * @author laoyu
     * @date 2022/11/28
     */
    void socketContainerLog(SocketContainerLogData build, UserInfoVo userInfo);
}
