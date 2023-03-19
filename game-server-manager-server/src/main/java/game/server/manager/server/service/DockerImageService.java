package game.server.manager.server.service;

import com.github.dockerjava.api.model.Image;
import game.server.manager.common.result.R;
import game.server.manager.common.vo.UserInfoVo;
import game.server.manager.server.websocket.handler.browser.SocketPullImageData;

import java.util.List;

/**
 * @author laoyu
 * @version 1.0
 * @description dicker 镜像相关
 * @date 2022/11/19
 */

public interface DockerImageService {

    /**
     * 获取镜像列表
     *
     * @param dockerId dockerId
     * @return game.server.manager.common.result.R<java.util.List<com.github.dockerjava.api.model.Image>>
     * @author laoyu
     * @date 2022/11/19
     */
    List<Image> listImages(String dockerId);

    /**
     * 删除镜像
     *
     * @param dockerId dockerId
     * @param imageId imageId
     * @return game.server.manager.common.result.R<java.lang.Void>
     * @author laoyu
     * @date 2022/11/19
     */
    Object removeImage(String dockerId, String imageId);

    /**
     * pull镜像
     *
     * @param dockerId dockerId
     * @param repository repository
     * @return game.server.manager.common.result.R<java.lang.String>
     * @author laoyu
     * @date 2022/11/24
     */
    String pullImage(String dockerId, String repository);

    /**
     * socket方式pull镜像
     *
     * @param pullImageData pullImageData
     * @param userInfo
     * @author laoyu
     * @date 2022/11/24
     */
    void socketPullImage(SocketPullImageData  pullImageData, UserInfoVo userInfo);
}
