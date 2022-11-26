package game.server.manager.common.enums;

import game.server.manager.common.constant.MessageTypeConstants;

/**
 * @author laoyu
 * @version 1.0
 */
public enum ClientSocketTypeEnum {
    /**/
    HEARTBEAT(MessageTypeConstants.HEARTBEAT,"心跳"),

    LOCK(MessageTypeConstants.LOCK,"锁定"),

    ERROR(MessageTypeConstants.ERROR,"异常"),

    RESULT(MessageTypeConstants.RESULT,"业务数据返回"),

    RESULT_END(MessageTypeConstants.RESULT_END,"业务数据返回结束"),


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
