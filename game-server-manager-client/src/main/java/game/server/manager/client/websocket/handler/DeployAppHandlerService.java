package game.server.manager.client.websocket.handler;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.alibaba.fastjson2.JSON;
import game.server.manager.client.server.ClientDeploymentServer;
import game.server.manager.client.server.SyncServer;
import game.server.manager.common.application.ExecScriptParam;
import game.server.manager.common.constant.MessageTypeConstants;
import game.server.manager.common.enums.ClientSocketTypeEnum;
import game.server.manager.common.mode.socket.ServerMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * @author yuzhanfeng
 * @Date 2022/11/26 23:29
 * @Description 部署APP消息处理
 */
@Slf4j
@Service(MessageTypeConstants.DEPLOY_APP)
public class DeployAppHandlerService implements AbstractHandlerService {

    @Resource
    private SyncServer syncServer;

    @Resource
    private ClientDeploymentServer clientDeploymentServer;

    @Override
    public Void handler(ServerMessage serverMessage) {
        log.info("OnMessageHandler deploy_app ==> {}",serverMessage);
        String messageId = serverMessage.getMessageId();
        try {
            String data = serverMessage.getData();
            clientDeploymentServer.deployment(JSON.parseObject(data, ExecScriptParam.class));
        }catch (Exception e) {
            syncServer.sendFailMessage(ClientSocketTypeEnum.NO_SYNC_RESULT,messageId, ExceptionUtil.getMessage(e));
        }
        return null;
    }
}
