package game.server.manager.server.websocket;

import cn.dev33.satoken.jwt.SaJwtUtil;
import cn.dev33.satoken.jwt.StpLogicJwtForSimple;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import game.server.manager.common.constant.SystemConstant;
import game.server.manager.common.vo.DeployLogResultVo;
import game.server.manager.redis.config.RedisUtils;
import game.server.manager.server.application.DeploymentLogServer;
import game.server.manager.server.service.ApplicationInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.web.Link;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * @author yuzhanfeng
 * @Date 2022/9/1 11:20
 */
@Slf4j
@Component
@ServerEndpoint("/wss/deployLog")
public class DeployLogEndpoint {

    private static ApplicationInfoService applicationInfoService;
    private static DeploymentLogServer deploymentLogServer;
    /**
     * session实例
     */
    private Session session;

    /**
     * ServerEndpoint无法直接 Autowired static方式
     *
     * @param applicationInfoService applicationInfoService
     * @author laoyu
     * @date 2022/9/2
     */
    @Autowired
    private void setApplicationInfoService(ApplicationInfoService applicationInfoService) {
        DeployLogEndpoint.applicationInfoService = applicationInfoService;
    }

    /**
     * ServerEndpoint无法直接 Autowired static方式
     *
     * @param deploymentLogServer deploymentLogServer
     * @author laoyu
     * @date 2022/9/2
     */
    @Autowired
    private void setDeploymentLogServer(DeploymentLogServer deploymentLogServer) {
        DeployLogEndpoint.deploymentLogServer = deploymentLogServer;
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
        this.session = session;
        log.info("【websocket消息】建立查询执行日志连接.");
        sendMessage(JSON.toJSONString(DeployLogResultVo.builder().isFinish(false).logs(List.of("连接成功。")).build()));
    }

    /**
     * 关闭 websocket连接
     *
     * @author laoyu
     * @date 2022/9/1
     */
    @OnClose
    public void onClose() {
        log.info("【websocket消息】查询执行日志连接断开");
    }

    @OnError
    public void onError(Throwable exception) {
        exception.printStackTrace();
        sendMessage("服务端错误,断开连接。：" + ExceptionUtil.getMessage(exception));
        close();
    }

    /**
     * 接收到消息
     *
     * @param message message
     * @author laoyu
     * @date 2022/9/1
     */
    @OnMessage
    public void onMessage(String message) throws InterruptedException {
        log.info("【websocket消息】查询执行日志请求:{}", message);
        JSONObject messageObject = JSON.parseObject(message);
        Object logId = messageObject.get("logId");
        Object token = messageObject.get(SystemConstant.TOKEN_NAME);
        if (Objects.isNull(token)) {
            sendMessage(JSON.toJSONString(DeployLogResultVo.builder().isFinish(false).logs(List.of("token错误,断开连接")).build()));
            close();
        }

        StpLogicJwtForSimple stpLogic = (StpLogicJwtForSimple) StpUtil.stpLogic;
        cn.hutool.json.JSONObject payloads = SaJwtUtil.getPayloadsNotCheck((String) token, stpLogic.loginType, stpLogic.jwtSecretKey());
        cn.hutool.json.JSONObject info = payloads.getJSONObject(SystemConstant.TOKEN_USER_INFO);
        boolean isAdmin = info.getJSONArray(SystemConstant.TOKEN_USER_ROLES).contains(SystemConstant.SUPER_ADMIN_ROLE);
        Long userId = info.getLong("id");

        if (!isAdmin) {
            Object applicationId = messageObject.get("applicationId");
            if (Objects.isNull(applicationId)) {
                sendMessage(JSON.toJSONString(DeployLogResultVo.builder().isFinish(false).logs(List.of("应用信息不存在,断开连接")).build()));
                close();
            }
            if (!applicationInfoService.exist((String) applicationId, userId)) {
                sendMessage(JSON.toJSONString(DeployLogResultVo.builder().isFinish(false).logs(List.of("应用不存在或不属于你,断开连接")).build()));
                close();
            }
        }

        if (Objects.isNull(logId) && session.isOpen()) {
            sendMessage(JSON.toJSONString(DeployLogResultVo.builder().isFinish(false).logs(List.of("日志不存在,断开连接")).build()));
            close();
        } else {
            DeployLogResultVo logResult;
            do {
                logResult = deploymentLogServer.getDeploymentLog((Serializable) logId);
                sendMessage(JSON.toJSONString(logResult));
                if (!logResult.isFinish() && session.isOpen()) {
                    Thread.sleep(3000);
                }
            } while (!logResult.isFinish() && session.isOpen());
            close();
        }
    }


    private void close() {
        if (Objects.nonNull(this.session)) {
            try {
                this.session.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 发送消息
     *
     * @param message message
     * @author laoyu
     * @date 2022/9/1
     */
    public void sendMessage(String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
