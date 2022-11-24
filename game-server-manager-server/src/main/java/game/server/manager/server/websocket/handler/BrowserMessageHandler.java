package game.server.manager.server.websocket.handler;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.json.JSONObject;
import com.alibaba.fastjson2.JSON;
import game.server.manager.auth.AuthorizationUtil;
import game.server.manager.common.constant.SystemConstant;
import game.server.manager.common.enums.BrowserMessageTypeEnum;
import game.server.manager.common.exception.BizException;
import game.server.manager.common.mode.socket.BrowserDeployLogMessage;
import game.server.manager.common.mode.socket.BrowserMessage;
import game.server.manager.common.mode.socket.BrowserPulImageMessage;
import game.server.manager.common.vo.DeployLogResultVo;
import game.server.manager.server.service.DockerImageService;
import game.server.manager.server.websocket.SocketSessionCache;
import game.server.manager.server.websocket.model.SocketPullImageData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.websocket.Session;
import java.io.IOException;
import java.io.Serializable;
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
    private DockerImageService dockerImageService;


    public void handle(String message, Session session) {
        BrowserMessage browserMessage = JSON.parseObject(message, BrowserMessage.class);
        //校验token
        JSONObject userInfo = AuthorizationUtil.checkTokenOrLoadUserJson(browserMessage.getToken());
        SocketSessionCache.saveBrowserSession(browserMessage.getDockerId(),session);
        String type = browserMessage.getType();
        //部署日志
        if(BrowserMessageTypeEnum.DEPLOY_LOG.getType().equals(type)) {
            getDeployLog(browserMessage,userInfo);
        }
        //拉取镜像
        if(BrowserMessageTypeEnum.PULL.getType().equals(type)) {
            pullImage(browserMessage);
        }
    }

    private void getDeployLog(BrowserMessage browserMessage, JSONObject userInfo) {
        String jsonData = browserMessage.getJsonData();
        BrowserDeployLogMessage browserDeployLogMessage = JSON.parseObject(jsonData, BrowserDeployLogMessage.class);
        boolean isAdmin = userInfo.getJSONArray(SystemConstant.TOKEN_USER_ROLES).contains(SystemConstant.SUPER_ADMIN_ROLE);
        Long userId = userInfo.getLong("id");

//        if (!isAdmin) {
//            Object applicationId = browserDeployLogMessage.getApplicationId();
//            if (Objects.isNull(applicationId)) {
//                sendMessage(JSON.toJSONString(DeployLogResultVo.builder().isFinish(false).logs(List.of("应用信息不存在,断开连接")).build()));
//                close();
//            }
//            if (!applicationInfoService.exist((String) applicationId, userId)) {
//                sendMessage(JSON.toJSONString(DeployLogResultVo.builder().isFinish(false).logs(List.of("应用不存在或不属于你,断开连接")).build()));
//                close();
//            }
//        }
//
//        if (Objects.isNull(logId) && session.isOpen()) {
//            sendMessage(JSON.toJSONString(DeployLogResultVo.builder().isFinish(false).logs(List.of("日志不存在,断开连接")).build()));
//            close();
//        } else {
//            DeployLogResultVo logResult;
//            do {
//                logResult = deploymentLogServer.getDeploymentLog((Serializable) logId);
//                sendMessage(JSON.toJSONString(logResult));
//                if (!logResult.isFinish() && session.isOpen()) {
//                    Thread.sleep(3000);
//                }
//            } while (!logResult.isFinish() && session.isOpen());
//            close();
//        }
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
    private void pullImage(BrowserMessage browserMessage){
        String jsonData = browserMessage.getJsonData();
        BrowserPulImageMessage pullData = JSON.parseObject(jsonData, BrowserPulImageMessage.class);
        dockerImageService.socketPullImage(SocketPullImageData.builder()
                .dockerId(browserMessage.getDockerId())
                .repository(pullData.getRepository()).build());
    }


}
