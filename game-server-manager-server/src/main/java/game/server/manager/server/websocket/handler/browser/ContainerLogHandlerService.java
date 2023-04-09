package game.server.manager.server.websocket.handler.browser;

import com.alibaba.fastjson2.JSON;
import game.server.manager.common.constant.MessageTypeConstants;
import game.server.manager.common.mode.socket.BrowserMessage;
import game.server.manager.common.vo.UserInfoVo;
import game.server.manager.server.service.DockerContainerService;
import game.server.manager.server.util.SessionUtils;
import game.server.manager.server.websocket.SocketSessionCache;
import game.server.manager.server.websocket.handler.AbstractHandlerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.websocket.Session;

/**
 * @author yuzhanfeng
 * @Date 2022/11/26 22:45
 * @Description 游览器pull镜像socket消息处理服务
 */
@Service(MessageTypeConstants.CONTAINER_LOG)
public class ContainerLogHandlerService implements AbstractHandlerService<BrowserHandlerData> {

    @Resource
    private DockerContainerService dockerContainerService;

    @Override
    public Void handler(BrowserHandlerData browserHandlerData) {
        BrowserMessage browserMessage = browserHandlerData.getBrowserMessage();
        UserInfoVo userInfo = browserHandlerData.getUserInfo();
        Session session = browserHandlerData.getSession();
        String jsonData = browserMessage.getData();
        BrowserContainerLogData pullData = JSON.parseObject(jsonData, BrowserContainerLogData.class);
        SocketSessionCache.saveBrowserSession(pullData.getDockerId(), session);
        SessionUtils.sendOkServerMessage(session,session.getId(),"waiting client message...");
        dockerContainerService.socketContainerLog(SocketContainerLogData.builder()
                .dockerId(pullData.getDockerId())
                .containerId(pullData.getContainerId()).build(), userInfo);
        return null;
    }

}
