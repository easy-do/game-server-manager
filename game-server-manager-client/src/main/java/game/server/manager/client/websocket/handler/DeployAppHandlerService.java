//package game.server.manager.client.websocket.handler;
//
//import cn.hutool.core.exceptions.ExceptionUtil;
//import com.alibaba.fastjson2.JSON;
//import game.server.manager.client.server.ClientDeploymentServer;
//import game.server.manager.client.server.SyncServer;
//import game.server.manager.common.application.ExecScriptParam;
//import game.server.manager.client.contants.MessageTypeConstants;
//import import game.server.manager.client.contants.ClientSocketTypeEnum;
//import game.server.manager.client.model.socket.ServerMessage;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//
///**
// * @author yuzhanfeng
// * @Date 2022/11/26 23:29
// * @Description 部署APP消息处理
// */
//@Slf4j
//@Service(MessageTypeConstants.DEPLOY_APP)
//public class DeployAppHandlerService implements AbstractHandlerService {
//
//    @Autowired
//    private SyncServer syncServer;
//
//    @Autowired
//    private ClientDeploymentServer clientDeploymentServer;
//
//    @Override
//    public Void handler(ServerMessage serverMessage) {
//        log.info("OnMessageHandler deploy_app ==> {}",serverMessage);
//        String messageId = serverMessage.getMessageId();
//        try {
//            String data = serverMessage.getData();
//            clientDeploymentServer.deployment(JSON.parseObject(data, ExecScriptParam.class));
//        }catch (Exception e) {
//            syncServer.sendFailMessage(ClientSocketTypeEnum.NO_SYNC_RESULT,messageId, ExceptionUtil.getMessage(e));
//        }
//        return null;
//    }
//}
