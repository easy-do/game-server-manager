package plus.easydo.push.client.contants;

/**
 * @author yuzhanfeng
 * @Date 2022/8/11 15:00
 */
public enum ServerMessageTypeEnum {

    /**/
    HEARTBEAT(MessageTypeConstants.HEARTBEAT,"心跳"),

    CONNECT_SUCCESS(MessageTypeConstants.CONNECT_SUCCESS,"连接成功"),

    SUCCESS(MessageTypeConstants.SUCCESS,"服务器异常"),

    ERROR(MessageTypeConstants.ERROR,"服务器异常"),

    SYNC_RESULT_END(MessageTypeConstants.SYNC_RESULT_END,"同步等待数据执行完成"),

    CANCEL_SYNC(MessageTypeConstants.CANCEL_SYNC,"取消当前同步请求"),

    PING_DOCKER(MessageTypeConstants.PING_DOCKER,"删除镜像"),

    DOCKER_INFO(MessageTypeConstants.DOCKER_INFO,"删除镜像"),

    DOCKER_VERSION(MessageTypeConstants.DOCKER_VERSION,"删除镜像"),

    LIST_IMAGE(MessageTypeConstants.LIST_IMAGE,"镜像列表"),

    REMOVE_IMAGE(MessageTypeConstants.REMOVE_IMAGE,"删除镜像"),

    PULL_IMAGE(MessageTypeConstants.PULL_IMAGE,"pull镜像"),

    PUSH_IMAGE(MessageTypeConstants.PUSH_IMAGE,"push镜像"),

    CONTAINER_LIST(MessageTypeConstants.CONTAINER_LIST,"容器列表"),

    CONTAINER_LOG(MessageTypeConstants.CONTAINER_LOG,"容器列表"),

    START_CONTAINER(MessageTypeConstants.START_CONTAINER,"启动容器"),

    RESTART_CONTAINER(MessageTypeConstants.RESTART_CONTAINER,"重启容器"),

    STOP_CONTAINER(MessageTypeConstants.STOP_CONTAINER,"停止容器"),

    REMOVE_CONTAINER(MessageTypeConstants.REMOVE_CONTAINER,"删除容器"),

    RENAME_CONTAINER(MessageTypeConstants.RENAME_CONTAINER,"重命名容器"),

    CREATE_CONTAINER(MessageTypeConstants.CREATE_CONTAINER,"创建容器"),

    EXEC_SCRIPT(MessageTypeConstants.DEPLOY_APP,"执行脚本"),

    INSTALL_APPLICATION(MessageTypeConstants.INSTALL_APPLICATION,"安装应用");

    private final String type;
    private final String desc;

    ServerMessageTypeEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
