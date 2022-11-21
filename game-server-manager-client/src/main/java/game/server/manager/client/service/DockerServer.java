package game.server.manager.client.service;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.io.FileUtil;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.ListContainersCmd;
import com.github.dockerjava.api.command.ListImagesCmd;
import com.github.dockerjava.api.command.PingCmd;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Image;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.KeystoreSSLConfig;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.List;

/**
 * @author laoyu
 * @version 1.0
 * @description docker api 操作服务类
 * @date 2022/11/12
 */
@Slf4j
@Data
public class DockerServer {

//    private DockerClient dockerClient;

//    @Value("${docker.serverUrl:tcp:localhost:2375}")

    private String serverUrl;

    private DockerClient dockerClient;

    public DockerServer(String serverUrl) {
        this.serverUrl = serverUrl;
        this.dockerClient = createClient();
    }

    private DockerClientConfig initConfig() {
        DefaultDockerClientConfig.Builder builder = DefaultDockerClientConfig.createDefaultConfigBuilder();
        builder.withDockerHost(serverUrl);
        //安全验证配置
        try{
            File file = FileUtil.file("D:/dockerCert/keystore.p12");
            KeystoreSSLConfig keystore = new KeystoreSSLConfig(file,"123456") ;
            builder.withCustomSslConfig(keystore);
            return builder.build();
        }catch (Exception e){
            log.error(ExceptionUtil.getMessage(e));
        }
        return null;
    }

    public DockerClient createClient() {
        DockerClientConfig dockerClientConfig = initConfig();
        DockerClient client = DockerClientBuilder.getInstance(dockerClientConfig).build();
        log.info("docker info:{}",client.infoCmd().exec());
        return client;
    }

    /**
     * ping
     *
     * @author laoyu
     * @date 2022/11/13
     */
    public void ping(){
        PingCmd pingCmd = dockerClient.pingCmd();
        pingCmd.exec();
    }

    /**
     * 获取镜像列表
     *
     * @return java.util.List<com.github.dockerjava.api.model.Image>
     * @author laoyu
     * @date 2022/11/12
     */
    public List<Image> listImages(){
        ListImagesCmd listImagesCmd = dockerClient.listImagesCmd();
        return listImagesCmd.exec();
    }

    /**
     * 容器列表
     *
     * @return java.util.List<com.github.dockerjava.api.model.Container>
     * @author laoyu
     * @date 2022/11/12
     */
    public List<Container> containerList(){
        ListContainersCmd listContainersCmd = dockerClient.listContainersCmd();
        return listContainersCmd.exec();
    }


    public static void main(String[] args) {
        DockerServer dockerServer = new DockerServer("tcp://192.168.123.89:2375");
        List<Image> list = dockerServer.listImages();
        log.info(list.toString());
    }


}
