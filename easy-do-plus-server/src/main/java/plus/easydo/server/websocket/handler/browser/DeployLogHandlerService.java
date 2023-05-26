package plus.easydo.server.websocket.handler.browser;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.alibaba.fastjson2.JSON;
import plus.easydo.common.constant.MessageTypeConstants;
import plus.easydo.common.enums.ServerMessageTypeEnum;
import plus.easydo.common.exception.ExceptionFactory;
import plus.easydo.common.mode.socket.BrowserExecScriptLogMessage;
import plus.easydo.common.mode.socket.BrowserMessage;
import plus.easydo.common.mode.socket.ServerMessage;
import plus.easydo.common.vo.LogResultVo;
import plus.easydo.common.vo.UserInfoVo;
import plus.easydo.server.application.DeploymentLogServer;
import plus.easydo.server.util.server.SessionUtils;
import plus.easydo.server.websocket.handler.AbstractHandlerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.websocket.Session;
import java.util.List;
import java.util.Objects;

/**
 * @author yuzhanfeng
 * @Date 2022/11/26 22:45
 * @Description 游览器部署日志socket消息处理服务
 */
@Service(MessageTypeConstants.DEPLOY_LOG)
public class DeployLogHandlerService implements AbstractHandlerService<BrowserHandlerData> {

    @Resource
    private DeploymentLogServer deploymentLogServer;

    @Override
    public Void handler(BrowserHandlerData browserHandlerData) {

        BrowserMessage browserMessage = browserHandlerData.getBrowserMessage();
        String jsonData = browserMessage.getData();
        BrowserExecScriptLogMessage browserExecScriptLogMessage = JSON.parseObject(jsonData, BrowserExecScriptLogMessage.class);
        String logId = browserExecScriptLogMessage.getLogId();
        UserInfoVo userInfo = browserHandlerData.getUserInfo();
        Session session = browserHandlerData.getSession();
            if (!userInfo.isAdmin()) {
                String deviceId = browserExecScriptLogMessage.getDeviceId();
                if (Objects.isNull(deviceId)) {
                    SessionUtils.sendMessage(session, JSON.toJSONString(LogResultVo.builder().isFinish(false).logs(List.of("设备不存在,断开连接")).build()));
                    SessionUtils.close(session);
                    return null;
                }
            }

            if (Objects.isNull(logId) && session.isOpen()) {
                SessionUtils.sendMessage(session, JSON.toJSONString(LogResultVo.builder().isFinish(false).logs(List.of("日志不存在,断开连接")).build()));
                SessionUtils.close(session);
            } else {
                LogResultVo logResult;
                do {
                    logResult = deploymentLogServer.getDeploymentLog(logId);
                    ServerMessage serverMessage = ServerMessage.builder()
                            .type(ServerMessageTypeEnum.SUCCESS.getType())
                            .data(String.join("",logResult.getLogs()))
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

        return null;
    }


}
