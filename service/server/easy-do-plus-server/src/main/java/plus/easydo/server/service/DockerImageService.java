package plus.easydo.server.service;

import com.github.dockerjava.api.model.Image;
import plus.easydo.common.vo.UserInfoVo;
import plus.easydo.server.websocket.handler.browser.SocketPullImageData;

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
     * @return result.plus.easydo.common.R<java.util.List<com.github.dockerjava.api.model.Image>>
     * @author laoyu
     * @date 2022/11/19
     */
    List<Image> listImages(String dockerId);

    /**
     * 删除镜像
     *
     * @param dockerId dockerId
     * @param imageId imageId
     * @return result.plus.easydo.common.R<java.lang.Void>
     * @author laoyu
     * @date 2022/11/19
     */
    Object removeImage(String dockerId, String imageId);

    /**
     * pull镜像
     *
     * @param dockerId dockerId
     * @param repository repository
     * @return result.plus.easydo.common.R<java.lang.String>
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
    void socketPullImage(SocketPullImageData pullImageData, UserInfoVo userInfo);
}
