package game.server.manager.server.util.server;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.KeystoreSSLConfig;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;
import game.server.manager.common.exception.ExceptionFactory;
import game.server.manager.server.entity.DockerDetails;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.time.Duration;

/**
 * @author laoyu
 * @version 1.0
 * @description docker工具类
 * @date 2023/3/19
 */
@Slf4j
public class DockerUtils {

    private DockerUtils() {
    }

    /**
     * 初始化docker客户端配置
     *
     * @param dockerDetails dockerDetails
     * @return com.github.dockerjava.core.DockerClientConfig
     * @author laoyu
     * @date 2023/3/19
     */
    private static DockerClientConfig initConfig(DockerDetails dockerDetails) throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException {
        DefaultDockerClientConfig.Builder builder = DefaultDockerClientConfig.createDefaultConfigBuilder();
        builder.withDockerHost(dockerDetails.getDockerHost());
        //安全验证配置

        if (dockerDetails.getSslFlag()) {
            String cert = dockerDetails.getDockerCert();
            File cartFile = FileUtil.createTempFile();
            IoUtil.write(FileUtil.getOutputStream(cartFile), true, cert.getBytes());
            KeystoreSSLConfig keystore = new KeystoreSSLConfig(cartFile, dockerDetails.getDockerCertPassword());
            builder.withCustomSslConfig(keystore);
        }
        return builder.build();

    }

    /**
     * 创建docker客户端
     *
     * @param dockerDetails dockerDetails
     * @return com.github.dockerjava.api.DockerClient
     * @author laoyu
     * @date 2023/3/19
     */
    public static DockerClient createDockerClient(DockerDetails dockerDetails) {
        try {
            DockerClientConfig dockerClientConfig = initConfig(dockerDetails);
            DockerHttpClient httpClient = new ApacheDockerHttpClient.Builder()
                    .dockerHost(dockerClientConfig.getDockerHost())
                    .sslConfig(dockerClientConfig.getSSLConfig())
                    .maxConnections(100)
                    .connectionTimeout(Duration.ofSeconds(30))
                    .responseTimeout(Duration.ofSeconds(45))
                    .build();
            return DockerClientBuilder.getInstance(dockerClientConfig).withDockerHttpClient(httpClient).build();
        } catch (Exception e) {
            throw ExceptionFactory.bizException("创建客户端连接失败:{}", e.getMessage());
        }
    }
}
