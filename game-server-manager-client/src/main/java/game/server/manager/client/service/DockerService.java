package game.server.manager.client.service;

import com.github.dockerjava.api.model.Info;
import com.github.dockerjava.api.model.Version;
import game.server.manager.client.service.base.DockerBaseService;
import game.server.manager.client.utils.DockerUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @author laoyu
 * @version 1.0
 * @description docker service
 * @date 2022/11/19
 */
@Slf4j
@Component
public class DockerService {

    @Autowired
    private DockerBaseService dockerBaseService;


    /**
     * ping
     *
     * @author laoyu
     * @date 2022/11/13
     */
    public Void ping() {
        log.info("Docker ping");
        return dockerBaseService.ping(DockerUtils.creteDockerClient());
    }

    public Info info() {
        log.info("Docker info");
        return dockerBaseService.info(DockerUtils.creteDockerClient());
    }

    public Version version() {
        log.info("Docker info");
        return dockerBaseService.version(DockerUtils.creteDockerClient());
    }

}
