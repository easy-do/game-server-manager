package game.server.manager.common.constant;

/**
 * @author yuzhanfeng
 * @Date 2022/11/26 23:08
 * @Description 消息类型常量
 */
public class MessageTypeConstants {

    public static final String DEPLOY_LOG = "deploy_log";

    public static final String PULL_IMAGE = "pullImage";

    public static final String HEARTBEAT = "heartbeat";

    public static final String LOCK = "lock";

    public static final String ERROR = "error";

    public static final String RESULT = "result";

    public static final String RESULT_END = "result_end";

    public static final String SYNC = "sync";

    public static final String CONNECT_SUCCESS = "connect_success";

    public static final String SUCCESS = "success";

    public static final String PUSH_IMAGE = "push";

    private MessageTypeConstants() {
    }

}
