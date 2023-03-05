package game.server.manager.server.config.oss.minio.config;

import game.server.manager.server.config.oss.minio.MinioOssStoreServer;
import game.server.manager.server.config.oss.minio.MinioTemplate;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * minio自动装配
 * @author laoyu
 * @version 1.0
 */
@Configuration
@EnableConfigurationProperties(MinioProperties.class)
public class MinioAutoConfiguration {

    @Autowired
    private MinioProperties minioProperties;


    /**
     * 初始化minio
     *
     * @return io.minio.MinioClient
     * @author laoyu
     */
    @Bean
    @ConditionalOnProperty(prefix = "oss.minio", name = "enable", havingValue = "true")
    public MinioClient minioClient(){
        return MinioClient.builder().endpoint(minioProperties.getServerAddress()).credentials(minioProperties.getAccessKey(),minioProperties.getSecretKey()).build();
    }

    @Bean
    @ConditionalOnProperty(prefix = "oss.minio", name = "enable", havingValue = "true")
    public MinioTemplate minioTemplate(){
        return new MinioTemplate();
    }

    @Bean
    @ConditionalOnProperty(prefix = "oss.minio", name = "enable", havingValue = "true")
    public MinioOssStoreServer fileStoreService(){
        return new MinioOssStoreServer();
    }
}
