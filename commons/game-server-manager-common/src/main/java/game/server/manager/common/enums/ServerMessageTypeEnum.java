package game.server.manager.common.enums;

import game.server.manager.common.constant.MessageTypeConstants;

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

    PULL_IMAGE(MessageTypeConstants.PULL_IMAGE,"pull镜像"),

    PUSH_IMAGE(MessageTypeConstants.PUSH_IMAGE,"push镜像");


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
