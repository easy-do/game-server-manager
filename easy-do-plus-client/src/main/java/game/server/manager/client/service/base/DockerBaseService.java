package plus.easydo.client.service.base;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.InfoCmd;
import com.github.dockerjava.api.command.PingCmd;
import com.github.dockerjava.api.command.VersionCmd;
import com.github.dockerjava.api.model.Info;
import com.github.dockerjava.api.model.Version;
import org.springframework.stereotype.Component;

/**
 * @author laoyu
 * @version 1.0
 * @description docker service
 * @date 2022/11/19
 */
@Component
public class DockerBaseService {


    /**
     * ping
     *
     * @param dockerClient dockerClient
     * @return java.lang.Void
     * @author laoyu
     * @date 2023/3/19
     */
    public Void ping(DockerClient dockerClient) {
        PingCmd pingCmd = dockerClient.pingCmd();
        return pingCmd.exec();
    }

    /**
     * 详情
     *
     * @param dockerClient dockerClient
     * @return com.github.dockerjava.api.model.Info
     * @author laoyu
     * @date 2023/3/19
     */
    public Info info(DockerClient dockerClient) {
        InfoCmd infoCmd = dockerClient.infoCmd();
        return infoCmd.exec();
    }

    /**
     * 版本
     *
     * @param dockerClient dockerClient
     * @return com.github.dockerjava.api.model.Version
     * @author laoyu
     * @date 2023/3/19
     */
    public Version version(DockerClient dockerClient) {
        VersionCmd versionCmd = dockerClient.versionCmd();
        return versionCmd.exec();
    }

}
