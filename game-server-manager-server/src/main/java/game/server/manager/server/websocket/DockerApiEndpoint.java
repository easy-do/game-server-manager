package game.server.manager.server.websocket;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.alibaba.fastjson2.JSON;
import game.server.manager.auth.AuthorizationUtil;
import game.server.manager.common.enums.DockerSocketTypeEnum;
import game.server.manager.common.exception.BizException;
import game.server.manager.common.mode.socket.DockerSocketMessage;
import game.server.manager.common.mode.socket.DockerSocketPullImageMessage;
import game.server.manager.server.websocket.model.SocketPullImageData;
import game.server.manager.common.result.DataResult;
import game.server.manager.server.service.DockerImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Objects;

import static game.server.manager.server.websocket.SocketSessionCache.SESSION_CACHE;

/**
 * @author yuzhanfeng
 * @Date 2022/9/1 11:20
 */
@Slf4j
@Component
@ServerEndpoint("/wss/docker")
public class DockerApiEndpoint {


    public static DockerImageService dockerImageService;


    /**
     * ServerEndpoint无法直接 Autowired static方式
     *
     * @param dockerImageService dockerImageService
     * @author laoyu
     * @date 2022/9/2
     */
    @Autowired
    private void setDockerImageService(DockerImageService dockerImageService) {
        DockerApiEndpoint.dockerImageService = dockerImageService;
    }

    /**
     * 打开websocket连接
     *
     * @param session session
     * @author laoyu
     * @date 2022/9/1
     */
    @OnOpen
    public void onOpen(Session session) {
        SESSION_CACHE.put(session.getId(),session);
        log.info("【websocket消息】客户端docker相关连接.");
        sendMessage(session,JSON.toJSONString(DataResult.ok(session.getId(),"connect success")));
    }

    /**
     * 关闭 websocket连接
     *
     * @author laoyu
     * @date 2022/9/1
     */
    @OnClose
    public void onClose(Session session) {
        SESSION_CACHE.remove(session.getId());
        log.info("【websocket消息】客户端docker相关连接断开");
    }

    @OnError
    public void onError(Session session,Throwable exception) {
        log.warn("【websocket消息】客户端docker socket通信异常，{}",ExceptionUtil.getMessage(exception));
        sendMessage(session,"服务器异常,将断开连接:," + ExceptionUtil.getMessage(exception));
        try {
            session.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 接收到消息
     *
     * @param message message
     * @author laoyu
     * @date 2022/9/1
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("【websocket消息】客户端docker相关连接请求:{}", message);
        DockerSocketMessage dockerSocketMessage = JSON.parseObject(message, DockerSocketMessage.class);
        //校验token
        AuthorizationUtil.checkTokenOrLoadUserJson(dockerSocketMessage.getToken());
        String type = dockerSocketMessage.getType();
        //拉取镜像
        if(DockerSocketTypeEnum.PULL.getType().equals(type)) {
            pullImage(session,dockerSocketMessage);
        }
    }


    /**
     * 发送消息
     *
     * @param message message
     * @author laoyu
     * @date 2022/9/1
     */
    public void sendMessage(Session session,String message) {
        try {
            if(Objects.nonNull(session) && session.isOpen()){
                session.getBasicRemote().sendText(message);
            }else {
                log.warn("Session is null");
            }
        } catch (IOException e) {
            throw new BizException(ExceptionUtil.getMessage(e));
        }
    }

    /**
     * 推送镜像
     *
     * @param session session
     * @param dockerSocketMessage dockerSocketMessage
     * @author laoyu
     * @date 2022/11/21
     */
    private void pullImage(Session session,DockerSocketMessage dockerSocketMessage){
            String jsonData = dockerSocketMessage.getJsonData();
            DockerSocketPullImageMessage pullData = JSON.parseObject(jsonData, DockerSocketPullImageMessage.class);
            sendMessage(session,"start pull image");
            dockerImageService.socketPullImage(SocketPullImageData.builder()
                    .session(session)
                    .dockerId(pullData.getDockerId())
                    .repository(pullData.getRepository()).build());
    }

}
