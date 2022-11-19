package game.server.manager.server.service;

import com.github.dockerjava.api.model.Info;
import game.server.manager.common.result.R;

/**
 * @author laoyu
 * @version 1.0
 * @description docker 基础接口
 * @date 2022/11/19
 */

public interface DockerBasicService {

    /**
     * ping
     *
     * @param dockerId dockerId
     * @return java.lang.Object
     * @author laoyu
     * @date 2022/11/19
     */
    Object ping(String dockerId);

    /**
     * info
     *
     * @param dockerId dockerId
     * @return game.server.manager.common.result.R<com.github.dockerjava.api.model.Info>
     * @author laoyu
     * @date 2022/11/19
     */
    R<Info> info(String dockerId);
}
