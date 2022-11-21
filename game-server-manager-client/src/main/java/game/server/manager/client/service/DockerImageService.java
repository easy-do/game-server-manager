package game.server.manager.client.service;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.ListImagesCmd;
import com.github.dockerjava.api.command.PullImageCmd;
import com.github.dockerjava.api.command.PullImageResultCallback;
import com.github.dockerjava.api.command.RemoveImageCmd;
import com.github.dockerjava.api.model.Image;
import com.github.dockerjava.api.model.PullResponseItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * @author laoyu
 * @version 1.0
 * @description 镜像相关
 * @date 2022/11/21
 */
@Slf4j
@Component
public class DockerImageService {


    @Resource
    private DockerClient dockerClient;

    /**
     * 获取镜像列表
     *
     * @return java.util.List<com.github.dockerjava.api.model.Image>
     * @author laoyu
     * @date 2022/11/12
     */
    public List<Image> listImages() {
        log.info("Docker listImages");
        ListImagesCmd listImagesCmd = dockerClient.listImagesCmd();
        return listImagesCmd.exec();
    }

    /**
     * pull镜像
     *
     * @param repository   repository
     * @param outputStream
     * @return java.lang.Void
     * @author laoyu
     * @date 2022/11/19
     */
    public void pullImage(String repository, ServletOutputStream outputStream) throws InterruptedException {
        log.info("Docker pullImage {}", repository);
        PullImageCmd pullImageCmd = dockerClient.pullImageCmd(repository);
        PullImageResultCallback callback = pullImageCmd.exec(new PullImageResultCallback() {
            @Override
            public void onNext(PullResponseItem item) {
                try {
                    outputStream.write((item.getStatus() +"\n").getBytes());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                log.info("pullImage ==> {},{}", repository, item.getStatus());
                super.onNext(item);
            }
        });
        callback.awaitCompletion();
    }


    /**
     * 删除镜像
     *
     * @param imageId imageId
     * @return java.lang.Void
     * @author laoyu
     * @date 2022/11/19
     */
    public Void removeImage(String imageId) {
        log.info("Docker removeImage {}", imageId);
        RemoveImageCmd removeImageCmd = dockerClient.removeImageCmd(imageId);
        return removeImageCmd.exec();
    }

}
