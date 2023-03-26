package game.server.manager.client.service;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Info;
import com.github.dockerjava.api.model.Version;
import game.server.manager.client.service.base.DockerBaseService;
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

    @Autowired(required = false)
    private DockerClient dockerClient;

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
        return dockerBaseService.ping(dockerClient);
    }

    public Info info() {
        log.info("Docker info");
        return dockerBaseService.info(dockerClient);
    }

    public Version version() {
        log.info("Docker info");
        return dockerBaseService.version(dockerClient);
    }

}
