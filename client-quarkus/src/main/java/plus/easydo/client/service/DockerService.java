package plus.easydo.client.service;

import com.github.dockerjava.api.model.Info;
import com.github.dockerjava.api.model.Version;
import plus.easydo.client.service.base.DockerBaseService;
import plus.easydo.client.utils.DockerUtils;
import lombok.extern.slf4j.Slf4j;
import javax.inject.Inject;
import javax.enterprise.context.ApplicationScoped;


/**
 * @author laoyu
 * @version 1.0
 * @description docker service
 * @date 2022/11/19
 */
@Slf4j
@ApplicationScoped
public class DockerService {

    @Inject
    DockerBaseService dockerBaseService;


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
