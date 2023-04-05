package plus.easydo.client.service.base;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.ListImagesCmd;
import com.github.dockerjava.api.command.PullImageCmd;
import com.github.dockerjava.api.command.PullImageResultCallback;
import com.github.dockerjava.api.command.RemoveImageCmd;
import com.github.dockerjava.api.model.Image;
import com.github.dockerjava.api.model.PullResponseItem;
import lombok.extern.slf4j.Slf4j;
import javax.enterprise.context.ApplicationScoped;

import java.util.List;

/**
 * @author laoyu
 * @version 1.0
 * @description 镜像相关
 * @date 2022/11/21
 */
@Slf4j
@ApplicationScoped
public class DockerImageBaseService {


    /**
     * 获取镜像列表
     *
     * @param dockerClient dockerClient
     * @return java.util.List<com.github.dockerjava.api.model.Image>
     * @author laoyu
     * @date 2023/3/19
     */
    public List<Image> listImages(DockerClient dockerClient) {
        ListImagesCmd listImagesCmd = dockerClient.listImagesCmd();
        return listImagesCmd.exec();
    }


    /**
     * pull镜像
     *
     * @param repository   repository
     * @return java.lang.Void
     * @author laoyu
     * @date 2022/11/19
     */
    public String pullImage(DockerClient dockerClient,String repository) throws InterruptedException {
        StringBuilder sb = new StringBuilder();
        PullImageCmd pullImageCmd = dockerClient.pullImageCmd(repository);
        PullImageResultCallback callback = pullImageCmd.exec(new PullImageResultCallback() {
            @Override
            public void onNext(PullResponseItem item) {
                    sb.append(item.getStatus()).append("\n");
                log.info("pullImage ==> {},{}", repository, item.getStatus());
                super.onNext(item);
            }
        });
        callback.awaitCompletion();
        return sb.toString();
    }


    /**
     * 删除镜像
     *
     * @param dockerClient dockerClient
     * @param imageId imageId
     * @return java.lang.Object
     * @author laoyu
     * @date 2023/3/19
     */
    public Object removeImage(DockerClient dockerClient, String imageId) {
        RemoveImageCmd removeImageCmd = dockerClient.removeImageCmd(imageId);
        return removeImageCmd.exec();
    }

}
