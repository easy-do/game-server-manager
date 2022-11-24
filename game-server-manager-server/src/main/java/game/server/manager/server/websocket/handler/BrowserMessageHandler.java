package game.server.manager.server.websocket.handler;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.alibaba.fastjson2.JSON;
import game.server.manager.auth.AuthorizationUtil;
import game.server.manager.common.enums.BrowserMessageTypeEnum;
import game.server.manager.common.enums.ServerMessageTypeEnum;
import game.server.manager.common.exception.BizException;
import game.server.manager.common.mode.socket.BrowserDeployLogMessage;
import game.server.manager.common.mode.socket.BrowserMessage;
import game.server.manager.common.mode.socket.BrowserPulImageMessage;
import game.server.manager.common.mode.socket.ServerMessage;
import game.server.manager.common.vo.DeployLogResultVo;
import game.server.manager.common.vo.UserInfoVo;
import game.server.manager.server.application.DeploymentLogServer;
import game.server.manager.server.service.ApplicationInfoService;
import game.server.manager.server.service.DockerImageService;
import game.server.manager.server.websocket.SocketSessionCache;
import game.server.manager.server.websocket.model.SocketPullImageData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.websocket.Session;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * @author yuzhanfeng
 * @Date 2022/11/24 16:58
 * @Description 游览器消息处理类
 */
@Slf4j
@Component
public class BrowserMessageHandler {

    @Resource
    private ApplicationInfoService applicationInfoService;

    @Resource
    private DeploymentLogServer deploymentLogServer;

    @Resource
    private DockerImageService dockerImageService;


    public void handle(String message, Session session) {
        BrowserMessage browserMessage = JSON.parseObject(message, BrowserMessage.class);
        //校验token
        UserInfoVo userInfo = AuthorizationUtil.checkTokenOrLoadUser(browserMessage.getToken());
        String type = browserMessage.getType();
        //部署日志
        if(BrowserMessageTypeEnum.DEPLOY_LOG.getType().equals(type)) {
            try {
                getDeployLog(session,browserMessage,userInfo);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        //拉取镜像
        if(BrowserMessageTypeEnum.PULL.getType().equals(type)) {
            SocketSessionCache.saveBrowserSession(browserMessage.getDockerId(),session);
            pullImage(browserMessage,userInfo);
        }
    }

    private void getDeployLog(Session session,BrowserMessage browserMessage, UserInfoVo userInfo) throws IOException {
        String jsonData = browserMessage.getData();
        BrowserDeployLogMessage browserDeployLogMessage = JSON.parseObject(jsonData, BrowserDeployLogMessage.class);
        String logId = browserDeployLogMessage.getLogId();
        if (!userInfo.isAdmin()) {
            String applicationId = browserDeployLogMessage.getApplicationId();
            if (Objects.isNull(applicationId)) {
                sendMessage(session,JSON.toJSONString(DeployLogResultVo.builder().isFinish(false).logs(List.of("应用信息不存在,断开连接")).build()));
                session.close();
                return;
            }
            if (!applicationInfoService.exist(applicationId, userInfo.getId())) {
                sendMessage(session,JSON.toJSONString(DeployLogResultVo.builder().isFinish(false).logs(List.of("应用不存在或不属于你,断开连接")).build()));
                session.close();
                return;
            }
        }

        if (Objects.isNull(logId) && session.isOpen()) {
            sendMessage(session,JSON.toJSONString(DeployLogResultVo.builder().isFinish(false).logs(List.of("日志不存在,断开连接")).build()));
            session.close();
        } else {
            DeployLogResultVo logResult;
            do {
                logResult = deploymentLogServer.getDeploymentLog(logId);
                ServerMessage serverMessage  = ServerMessage.builder()
                        .type(ServerMessageTypeEnum.SUCCESS.getType())
                        .data(JSON.toJSONString(logResult.getLogs()))
                        .build();
                sendMessage(session,JSON.toJSONString(serverMessage));
                if (!logResult.isFinish() && session.isOpen()) {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            } while (!logResult.isFinish() && session.isOpen());
            session.close();
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
     * @param browserMessage browserDockerMessage
     * @author laoyu
     * @date 2022/11/21
     */
    private void pullImage(BrowserMessage browserMessage,UserInfoVo userInfo){
        String jsonData = browserMessage.getData();
        BrowserPulImageMessage pullData = JSON.parseObject(jsonData, BrowserPulImageMessage.class);
        dockerImageService.socketPullImage(SocketPullImageData.builder()
                .dockerId(browserMessage.getDockerId())
                .repository(pullData.getRepository()).build(),userInfo);
    }


}
