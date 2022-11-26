package game.server.manager.server.websocket.handler.browser;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.alibaba.fastjson2.JSON;
import game.server.manager.common.constant.MessageTypeConstants;
import game.server.manager.common.enums.ServerMessageTypeEnum;
import game.server.manager.common.exception.ExceptionFactory;
import game.server.manager.common.mode.socket.BrowserDeployLogMessage;
import game.server.manager.common.mode.socket.BrowserMessage;
import game.server.manager.common.mode.socket.ServerMessage;
import game.server.manager.common.vo.DeployLogResultVo;
import game.server.manager.common.vo.UserInfoVo;
import game.server.manager.handler.AbstractHandlerService;
import game.server.manager.handler.Void;
import game.server.manager.handler.annotation.HandlerService;
import game.server.manager.server.application.DeploymentLogServer;
import game.server.manager.server.service.ApplicationInfoService;
import game.server.manager.server.websocket.SessionUtils;

import javax.annotation.Resource;
import javax.websocket.Session;
import java.util.List;
import java.util.Objects;

/**
 * @author yuzhanfeng
 * @Date 2022/11/26 22:45
 * @Description 游览器部署日志socket消息处理服务
 */
@HandlerService(MessageTypeConstants.DEPLOY_LOG)
public class DeployLogHandlerService extends AbstractHandlerService<BrowserHandlerData, Void> {

    @Resource
    private ApplicationInfoService applicationInfoService;

    @Resource
    private DeploymentLogServer deploymentLogServer;

    @Override
    public Void handler(BrowserHandlerData browserHandlerData) {

        BrowserMessage browserMessage = browserHandlerData.getBrowserMessage();
        String jsonData = browserMessage.getData();
        BrowserDeployLogMessage browserDeployLogMessage = JSON.parseObject(jsonData, BrowserDeployLogMessage.class);
        String logId = browserDeployLogMessage.getLogId();
        UserInfoVo userInfo = browserHandlerData.getUserInfo();
        Session session = browserHandlerData.getSession();
            if (!userInfo.isAdmin()) {
                String applicationId = browserDeployLogMessage.getApplicationId();
                if (Objects.isNull(applicationId)) {
                    SessionUtils.sendMessage(session, JSON.toJSONString(DeployLogResultVo.builder().isFinish(false).logs(List.of("应用信息不存在,断开连接")).build()));
                    SessionUtils.close(session);
                    returnVoid();
                }
                if (!applicationInfoService.exist(applicationId, userInfo.getId())) {
                    SessionUtils.sendMessage(session, JSON.toJSONString(DeployLogResultVo.builder().isFinish(false).logs(List.of("应用不存在或不属于你,断开连接")).build()));
                    SessionUtils.close(session);
                    returnVoid();
                }
            }

            if (Objects.isNull(logId) && session.isOpen()) {
                SessionUtils.sendMessage(session, JSON.toJSONString(DeployLogResultVo.builder().isFinish(false).logs(List.of("日志不存在,断开连接")).build()));
                SessionUtils.close(session);
            } else {
                DeployLogResultVo logResult;
                do {
                    logResult = deploymentLogServer.getDeploymentLog(logId);
                    ServerMessage serverMessage = ServerMessage.builder()
                            .type(ServerMessageTypeEnum.SUCCESS.getType())
                            .data(JSON.toJSONString(logResult.getLogs()))
                            .build();
                    SessionUtils.sendMessage(session, JSON.toJSONString(serverMessage));
                    if (!logResult.isFinish() && session.isOpen()) {
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            throw ExceptionFactory.bizException(ExceptionUtil.getMessage(e));
                        }
                    }
                } while (!logResult.isFinish() && session.isOpen());
                SessionUtils.close(session);
            }

        return returnVoid();
    }


}
