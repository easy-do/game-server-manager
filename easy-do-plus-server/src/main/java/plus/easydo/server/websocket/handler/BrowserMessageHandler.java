package plus.easydo.server.websocket.handler;

import com.alibaba.fastjson2.JSON;
import plus.easydo.auth.AuthorizationUtil;
import plus.easydo.auth.vo.SimpleUserInfoVo;
import plus.easydo.common.mode.socket.BrowserMessage;
import plus.easydo.server.websocket.handler.browser.BrowserHandlerData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.websocket.Session;
import java.util.Map;

/**
 * @author yuzhanfeng
 * @Date 2022/11/24 16:58
 * @Description 游览器消息处理
 */
@Slf4j
@Component
public class BrowserMessageHandler {


    @Autowired
    private Map<String, AbstractHandlerService<BrowserHandlerData>> handlerServiceContainer;

    @Resource
    private AuthorizationUtil authorizationUtil;


    public void handle(String message, Session session) {
        BrowserMessage browserMessage = JSON.parseObject(message, BrowserMessage.class);
        //校验token
        SimpleUserInfoVo userInfo = AuthorizationUtil.checkTokenOrLoadUser(browserMessage.getToken());
        String type = browserMessage.getType();
        BrowserHandlerData browserHandlerData = BrowserHandlerData.builder()
                .session(session)
                .userInfo(authorizationUtil.getUser(userInfo.getId()))
                .browserMessage(browserMessage)
                .build();
        handlerServiceContainer.get(type).handler(browserHandlerData);
    }


}
