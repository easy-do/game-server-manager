package plus.easydo.server.websocket.handler.browser;

import plus.easydo.common.mode.socket.BrowserMessage;
import plus.easydo.common.vo.UserInfoVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.websocket.Session;

/**
 * @author yuzhanfeng
 * @Date 2022/11/26 22:46
 * @Description 游览器消息处理服务参数封装
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BrowserHandlerData {

    private Session session;

    private BrowserMessage browserMessage;

    private UserInfoVo userInfo;
}
