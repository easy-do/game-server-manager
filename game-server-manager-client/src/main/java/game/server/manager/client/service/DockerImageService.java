package game.server.manager.client.service;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.ListImagesCmd;
import com.github.dockerjava.api.command.PullImageCmd;
import com.github.dockerjava.api.command.PullImageResultCallback;
import com.github.dockerjava.api.command.RemoveImageCmd;
import com.github.dockerjava.api.model.Image;
import com.github.dockerjava.api.model.PullResponseItem;
import game.server.manager.client.server.SyncServer;
import game.server.manager.common.enums.ClientSocketTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
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

    @Resource
    private SyncServer syncServer;

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
     * pull镜像  socket
     *
     * @param messageId messageId
     * @param repository repository
     * @author laoyu
     * @date 2022/11/23
     */
    public void pullImage(String messageId, String repository) {
        log.info("Docker pullImage {}", repository);
        PullImageCmd pullImageCmd = dockerClient.pullImageCmd(repository);
        PullImageResultCallback callback = pullImageCmd.exec(new PullImageResultCallback() {
            @Override
            public void onNext(PullResponseItem item) {
                syncServer.sendMessage(ClientSocketTypeEnum.SYNC_RESULT,item.getStatus());
                log.info("pullImage ==> {},{}", repository, item.getStatus());
                super.onNext(item);
            }
        });
        try {
            callback.awaitCompletion();
            syncServer.sendMessage(ClientSocketTypeEnum.SYNC_RESULT_END,"success");
        } catch (InterruptedException e) {
            syncServer.sendMessage(ClientSocketTypeEnum.ERROR,ExceptionUtil.getMessage(e));
            log.error("执行pull镜像操作异常，{}", ExceptionUtil.getMessage(e));
        }finally {
            //释放锁
            syncServer.getClient().unLock(messageId);
        }
    }

    /**
     * pull镜像
     *
     * @param repository   repository
     * @return java.lang.Void
     * @author laoyu
     * @date 2022/11/19
     */
    public String pullImage(String repository) throws InterruptedException {
        log.info("Docker pullImage {}", repository);
        StringBuilder sb = new StringBuilder();
        PullImageCmd pullImageCmd = dockerClient.pullImageCmd(repository);
        PullImageResultCallback callback = pullImageCmd.exec(new PullImageResultCallback() {
            @Override
            public void onNext(PullResponseItem item) {
                    sb.append(item.getStatus() +"\n");
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
     * @param imageId imageId
     * @return java.lang.Void
     * @author laoyu
     * @date 2022/11/19
     */
    public Object removeImage(String imageId) {
        log.info("Docker removeImage {}", imageId);
        RemoveImageCmd removeImageCmd = dockerClient.removeImageCmd(imageId);
        return removeImageCmd.exec();
    }

}
