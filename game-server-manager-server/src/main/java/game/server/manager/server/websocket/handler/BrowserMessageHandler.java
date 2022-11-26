package game.server.manager.server.websocket.handler;

import com.alibaba.fastjson2.JSON;
import game.server.manager.auth.AuthorizationUtil;
import game.server.manager.common.mode.socket.BrowserMessage;
import game.server.manager.common.vo.UserInfoVo;
import game.server.manager.handler.HandlerServiceContainer;
import game.server.manager.handler.Void;
import game.server.manager.server.websocket.handler.browser.BrowserHandlerData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.websocket.Session;

/**
 * @author yuzhanfeng
 * @Date 2022/11/24 16:58
 * @Description 游览器消息处理
 */
@Slf4j
@Component
public class BrowserMessageHandler {


    @Resource
    private HandlerServiceContainer<BrowserHandlerData, Void> handlerServiceContainer;


    public void handle(String message, Session session) {
        BrowserMessage browserMessage = JSON.parseObject(message, BrowserMessage.class);
        //校验token
        UserInfoVo userInfo = AuthorizationUtil.checkTokenOrLoadUser(browserMessage.getToken());
        String type = browserMessage.getType();
        BrowserHandlerData browserHandlerData = BrowserHandlerData.builder()
                .session(session)
                .userInfo(userInfo)
                .browserMessage(browserMessage)
                .build();
        handlerServiceContainer.handler(type,browserHandlerData);
    }


}
