package plus.easydo.client.utils;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;

import java.time.Duration;
import java.util.Objects;

/**
 * @author laoyu
 * @version 1.0
 * @description dockerUtils
 * @date 2023/3/26
 */

public class DockerUtils {

    private static DockerClient dockerClient;

    public static DockerClient creteDockerClient(){
        if (Objects.nonNull(dockerClient)) {
            return dockerClient;
        }
        DockerClientConfig dockerClientConfig = DefaultDockerClientConfig
                .createDefaultConfigBuilder().build();
        DockerHttpClient httpClient = new ApacheDockerHttpClient.Builder()
                .dockerHost(dockerClientConfig.getDockerHost())
                .sslConfig(dockerClientConfig.getSSLConfig())
                .maxConnections(100)
                .connectionTimeout(Duration.ofSeconds(30))
                .responseTimeout(Duration.ofSeconds(45))
                .build();
        dockerClient = DockerClientBuilder.getInstance(dockerClientConfig).withDockerHttpClient(httpClient)
                .build();
        return dockerClient;
    }
}
