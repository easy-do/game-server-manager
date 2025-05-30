package plus.easydo.server.service;

import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.Container;
import plus.easydo.common.vo.UserInfoVo;
import plus.easydo.docker.model.CreateContainerDto;
import plus.easydo.server.websocket.handler.browser.SocketContainerLogData;

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
     * @return result.plus.easydo.common.R<java.util.List<com.github.dockerjava.api.model.Container>>
     * @author laoyu
     * @date 2022/11/19
     */
    List<Container> containerList(String dockerId);

    /**
     * 启动容器
     *
     * @param dockerId dockerId
     * @param containerId containerId
     * @return result.plus.easydo.common.R<java.lang.Object>
     * @author laoyu
     * @date 2022/11/19
     */
    Object startContainer(Long dockerId, String containerId);

    /**
     * 重启容器
     *
     * @param dockerId dockerId
     * @param containerId containerId
     * @return result.plus.easydo.common.R<java.lang.Object>
     * @author laoyu
     * @date 2022/11/19
     */
    Object restartContainer(String dockerId, String containerId);

    /**
     * 停止容器
     *
     * @param dockerId dockerId
     * @param containerId containerId
     * @return result.plus.easydo.common.R<java.lang.Object>
     * @author laoyu
     * @date 2022/11/19
     */
    Object stopContainer(String dockerId, String containerId);

    /**
     * 删除容器
     *
     * @param dockerId dockerId
     * @param containerId containerId
     * @return result.plus.easydo.common.R<java.lang.Object>
     * @author laoyu
     * @date 2022/11/19
     */
    Object removeContainer(String dockerId, String containerId);

    /**
     * 重命名容器
     *
     * @param dockerId dockerId
     * @param containerId containerId
     * @param name name
     * @return result.plus.easydo.common.R<java.lang.Object>
     * @author laoyu
     * @date 2022/11/19
     */
    Object renameContainer(String dockerId, String containerId, String name);

    /**
     * 创建容器
     *
     * @param dockerId dockerId
     * @param createContainerDto createContainerDto
     * @return result.plus.easydo.common.R<com.github.dockerjava.api.command.Object>
     * @author laoyu
     * @date 2022/11/19
     */
    CreateContainerResponse createContainer(Long dockerId, CreateContainerDto createContainerDto);

    /**
     * 查看容器日志
     *
     * @param dockerId dockerId
     * @param containerId containerId
     * @return result.plus.easydo.common.R<java.lang.String>
     * @author laoyu
     * @date 2022/11/20
     */
    String logContainer(String dockerId, String containerId);

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
