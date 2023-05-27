package plus.easydo.server.websocket.handler.browser;

import com.alibaba.fastjson2.JSON;
import plus.easydo.common.mode.socket.BrowserMessage;
import plus.easydo.common.mode.socket.BrowserPulImageMessage;
import plus.easydo.common.vo.UserInfoVo;
import plus.easydo.server.service.DockerImageService;
import plus.easydo.server.util.server.SessionUtils;
import plus.easydo.server.websocket.SocketSessionCache;
import plus.easydo.server.websocket.handler.AbstractHandlerService;

import javax.annotation.Resource;
import javax.websocket.Session;

/**
 * @author yuzhanfeng
 * @Date 2022/11/26 22:45
 * @Description 游览器pull镜像socket消息处理服务
 */
public class PullImageHandlerService implements AbstractHandlerService<BrowserHandlerData> {

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
        SessionUtils.sendOkServerMessage(session,session.getId(),"waiting client message...");
        dockerImageService.socketPullImage(SocketPullImageData.builder()
                .dockerId(pullData.getDockerId())
                .repository(pullData.getRepository()).build(), userInfo);
        return null;
    }

}
