package game.server.manager.client.service;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.InfoCmd;
import com.github.dockerjava.api.command.PingCmd;
import com.github.dockerjava.api.command.VersionCmd;
import com.github.dockerjava.api.model.Info;
import com.github.dockerjava.api.model.Version;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

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


    /**
     * ping
     *
     * @author laoyu
     * @date 2022/11/13
     */
    public Void ping() {
        log.info("Docker ping");
        PingCmd pingCmd = dockerClient.pingCmd();
        return pingCmd.exec();
    }

    public Info info() {
        log.info("Docker info");
        InfoCmd infoCmd = dockerClient.infoCmd();
        return infoCmd.exec();
    }

    public Version version() {
        log.info("Docker info");
        VersionCmd versionCmd = dockerClient.versionCmd();
        return versionCmd.exec();
    }

}
