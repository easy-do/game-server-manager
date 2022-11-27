package game.server.manager.server.websocket.handler.browser;

import com.alibaba.fastjson2.JSON;
import game.server.manager.common.constant.MessageTypeConstants;
import game.server.manager.common.enums.ServerMessageTypeEnum;
import game.server.manager.common.mode.socket.BrowserMessage;
import game.server.manager.common.mode.socket.BrowserPulImageMessage;
import game.server.manager.common.mode.socket.ServerMessage;
import game.server.manager.common.vo.UserInfoVo;
import game.server.manager.handler.AbstractHandlerService;
import game.server.manager.handler.annotation.HandlerService;
import game.server.manager.server.service.DockerImageService;
import game.server.manager.server.util.SessionUtils;
import game.server.manager.server.websocket.SocketSessionCache;

import javax.annotation.Resource;
import javax.websocket.Session;

/**
 * @author yuzhanfeng
 * @Date 2022/11/26 22:45
 * @Description 游览器pull镜像socket消息处理服务
 */
@HandlerService(MessageTypeConstants.PULL_IMAGE)
public class PullImageHandlerService extends AbstractHandlerService<BrowserHandlerData, Void> {

    @Resource
    private DockerImageService dockerImageService;

    @Override
    public Void handler(BrowserHandlerData browserHandlerData) {
        BrowserMessage browserMessage = browserHandlerData.getBrowserMessage();
        UserInfoVo userInfo = browserHandlerData.getUserInfo();
        Session session = browserHandlerData.getSession();
        String jsonData = browserMessage.getData();
        BrowserPulImageMessage pullData = JSON.parseObject(jsonData, BrowserPulImageMessage.class);
        SocketSessionCache.saveBrowserSession(pullData.getDockerId(), session);
        SessionUtils.sendOkServerMessage(session,session.getId(),"等待客户端响应,这需要一点时间....");
        dockerImageService.socketPullImage(SocketPullImageData.builder()
                .dockerId(pullData.getDockerId())
                .repository(pullData.getRepository()).build(), userInfo);
        return null;
    }

}
