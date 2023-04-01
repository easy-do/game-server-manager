package game.server.manager.client.contants;


/**
 * @author laoyu
 * @version 1.0
 */
public enum ClientSocketTypeEnum {
    /**/
    HEARTBEAT(MessageTypeConstants.HEARTBEAT,"心跳"),

    LOCK(MessageTypeConstants.LOCK,"锁定"),

    ERROR(MessageTypeConstants.ERROR,"异常"),

    SYNC_RESULT(MessageTypeConstants.SYNC_RESULT,"业务数据返回"),

    SYNC_RESULT_END(MessageTypeConstants.SYNC_RESULT_END,"业务数据返回结束"),

    NO_SYNC_RESULT(MessageTypeConstants.NO_SYNC_RESULT,"业务数据返回结束"),

    INSTALL_APPLICATION_RESULT(MessageTypeConstants.INSTALL_APPLICATION_RESULT,"业务数据返回结束"),

    SYNC(MessageTypeConstants.SYNC,"同步");

    private final String type;
    private final String desc;

    ClientSocketTypeEnum(String type, String desc) {
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
