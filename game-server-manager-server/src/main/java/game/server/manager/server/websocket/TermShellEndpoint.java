package game.server.manager.server.websocket;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.RuntimeUtil;
import com.jcraft.jsch.JSch;
import game.server.manager.common.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * @author yuzhanfeng
 * @Date 2022/9/1 11:20
 */
@Slf4j
@Component
@ServerEndpoint("/wss/termShell")
public class TermShellEndpoint {


    private Session session;

    private static final StringBuilder history = new StringBuilder();

    /**
     * 打开websocket连接
     *
     * @param session session
     * @author laoyu
     * @date 2022/9/1
     */
    @OnOpen
    public void onOpen(Session session) {
        log.info("【websocket消息】建立termShell连接.");
        this.session = session;
        sendMessage(session,"success\r\n");
    }

    /**
     * 关闭 websocket连接
     *
     * @author laoyu
     * @date 2022/9/1
     */
    @OnClose
    public void onClose() {
        log.info("【websocket消息】termShell意外断开");
    }

    @OnError
    public void onError(Throwable exception) {
        exception.printStackTrace();
//        sendMessage(JSON.toJSONString(DataResult.fail()));
    }

    /**
     * 接收到消息
     *
     * @param message message
     * @author laoyu
     * @date 2022/9/1
     */
    @OnMessage
    public void onMessage(String message) {
        log.info("【websocket消息】termShell通信请求:{}", message);
        if(history.length() == 0 && message.equals("\r")){
            sendMessage(session,message);
            return;
        }
        if(message.contains("\r")){
            String[] cmds = message.split("\r");
            if(cmds.length > 1){
                for (int i = 0; i < cmds.length; i++) {
                    String cmd;
                    if(i == 0){
                        cmd =  history.append(cmds[i]).toString();
                        history.setLength(0);
                    }else {
                        cmd = cmds[i];
                    }
                    execCmd(cmd);
                }
            }else {
                history.append(message);
                sendMessage(session,message);
                execCmd(history.toString());
                history.setLength(0);
            }
            history.setLength(0);
        }else {
            history.append(message);
            sendMessage(session,message);
        }
        System.err.println(history);
    }

    private void execCmd(String cmd){
        log.info(cmd);
        List<String> resilt = RuntimeUtil.execForLines(cmd);
        resilt.forEach(str->{
            sendMessage(session,str+"\r\n");
        });
    }


    /**
     * 发送消息
     *
     * @param message message
     * @author laoyu
     * @date 2022/9/1
     */
    public void sendMessage(Session session,String message) {
        try {
            if(Objects.nonNull(session) && session.isOpen()){
                session.getBasicRemote().sendText(message);
            }else {
                log.warn("Session is null");
            }
        } catch (IOException e) {
            throw new BizException(ExceptionUtil.getMessage(e));
        }
    }


}
