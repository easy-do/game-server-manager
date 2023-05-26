package plus.easydo.push.client.service;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.text.CharSequenceUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dockerjava.api.command.PullImageCmd;
import com.github.dockerjava.api.command.PullImageResultCallback;
import com.github.dockerjava.api.model.Image;
import com.github.dockerjava.api.model.PullResponseItem;
import plus.easydo.push.client.contants.ClientSocketTypeEnum;
import plus.easydo.push.client.model.socket.InstallLogResultData;
import plus.easydo.push.client.server.SyncServer;
import plus.easydo.push.client.service.base.DockerImageBaseService;
import plus.easydo.push.client.utils.DockerUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author laoyu
 * @version 1.0
 * @description 镜像相关
 * @date 2022/11/21
 */
@Slf4j
@Component
public class DockerImageService {

    @Autowired
    private DockerImageBaseService dockerImageBaseService;

    @Autowired
    private SyncServer syncServer;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 获取镜像列表
     *
     * @return java.util.List<com.github.dockerjava.api.model.Image>
     * @author laoyu
     * @date 2022/11/12
     */
    public List<Image> listImages() {
        log.info("Docker listImages");
        return dockerImageBaseService.listImages(DockerUtils.creteDockerClient());
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
        PullImageCmd pullImageCmd = DockerUtils.creteDockerClient().pullImageCmd(repository);
        ConcurrentHashMap<String,String> messageCache = new ConcurrentHashMap<>();
        PullImageResultCallback callback = pullImageCmd.exec(new PullImageResultCallback() {
            @Override
            public void onNext(PullResponseItem item) {
                String status = item.getStatus();
                log.info("pullImage ==> {},{}", repository, status);
                try {
                    String firstCache = messageCache.get("first");
                    if(Objects.isNull(firstCache) || !CharSequenceUtil.equals(status,firstCache)){
                        assert status != null;
                        syncServer.sendOkMessage(ClientSocketTypeEnum.SYNC_RESULT, messageId, status);
                        messageCache.put("first",status);
                    }
                }catch (Exception e) {
                    log.warn("发送pull image 消息异常，等待一秒继续，{}", ExceptionUtil.getMessage(e));
                }
                super.onNext(item);
            }
        });
        try {
            callback.awaitCompletion();
            syncServer.sendOkMessage(ClientSocketTypeEnum.SYNC_RESULT_END, messageId,"success");
        } catch ( InterruptedException interruptedException) {
            log.error("执行pull镜像线程异常，{}", ExceptionUtil.getMessage(interruptedException));
            syncServer.sendOkMessage(ClientSocketTypeEnum.SYNC_RESULT_END, messageId,"客户端执行pull镜像线程异常："+ExceptionUtil.getMessage(interruptedException));
        }
    }

    /**
     * pull镜像  socket
     *
     * @param messageId messageId
     * @param repository repository
     * @author laoyu
     * @date 2022/11/23
     */
    public void pullImage(String messageId, String repository, InstallLogResultData resultData) {
        log.info("Docker pullImage {}", repository);
        PullImageCmd pullImageCmd = DockerUtils.creteDockerClient().pullImageCmd(repository);
        ConcurrentHashMap<String,String> messageCache = new ConcurrentHashMap<>();
        PullImageResultCallback callback = pullImageCmd.exec(new PullImageResultCallback() {
            @Override
            public void onNext(PullResponseItem item) {
                String status = item.getStatus();
                log.info("pullImage ==> {},{}", repository, status);
                try {
                    String firstCache = messageCache.get("first");
                    if(Objects.isNull(firstCache) || !CharSequenceUtil.equals(status,firstCache)){
                        assert status != null;
                        resultData.setMessage(status);
                        syncServer.sendOkMessage(ClientSocketTypeEnum.INSTALL_APPLICATION_RESULT,messageId, objectMapper.writeValueAsString(resultData));
                        messageCache.put("first",status);
                    }
                }catch (Exception e) {
                    log.warn("发送pull image 消息异常，等待一秒继续，{}", ExceptionUtil.getMessage(e));
                }
                super.onNext(item);
            }
        });
        try {
            callback.awaitCompletion();
            syncServer.sendOkMessage(ClientSocketTypeEnum.SYNC_RESULT_END, messageId,"success");
            resultData.setMessage("pull image "+repository+" success");
            syncServer.sendOkMessage(ClientSocketTypeEnum.INSTALL_APPLICATION_RESULT,messageId, objectMapper.writeValueAsString(resultData));
        } catch (InterruptedException | JsonProcessingException interruptedException) {
            log.error("执行pull镜像线程异常，{}", ExceptionUtil.getMessage(interruptedException));
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
        return dockerImageBaseService.pullImage(DockerUtils.creteDockerClient(),repository);
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
        return dockerImageBaseService.removeImage(DockerUtils.creteDockerClient(),imageId);
    }

}
