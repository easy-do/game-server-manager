package game.server.manager.common.constant;

/**
 * @author yuzhanfeng
 * @Date 2022/11/26 23:08
 * @Description 消息类型常量
 */
public class MessageTypeConstants {

    public static final String HEARTBEAT = "heartbeat";

    public static final String LOCK = "lock";

    public static final String ERROR = "error";

    public static final String NO_SYNC_RESULT = "no_sync_result";

    public static final String SYNC_RESULT = "sync_result";

    public static final String SYNC_RESULT_END = "sync_result_end";

    public static final String SYNC = "sync";

    public static final String CONNECT_SUCCESS = "connect_success";

    public static final String SUCCESS = "success";

    public static final String PUSH_IMAGE = "push_image";

    public static final String DEPLOY_LOG = "deploy_log";

    public static final String PULL_IMAGE = "pullImage";

    public static final String PING_DOCKER = "ping_docker";

    public static final String DOCKER_INFO = "docker_info";

    public static final String DOCKER_VERSION = "docker_version";

    public static final String REMOVE_IMAGE = "removeImage";

    public static final String LIST_IMAGE = "listImage";

    public static final String CONTAINER_LIST = "containerList";
    public static final String START_CONTAINER = "startContainer";
    public static final String RESTART_CONTAINER = "restartContainer";
    public static final String STOP_CONTAINER = "stopContainer";
    public static final String REMOVE_CONTAINER = "removeContainer";
    public static final String RENAME_CONTAINER = "renameContainer";

    public static final String CONTAINER_LOG = "container_log";

    public static final String CREATE_CONTAINER = "createContainer";
    public static final String CANCEL_SYNC = "cancel_sync";

    public static final String DEPLOY_APP = "deploy_app";

    private MessageTypeConstants() {
    }

}
