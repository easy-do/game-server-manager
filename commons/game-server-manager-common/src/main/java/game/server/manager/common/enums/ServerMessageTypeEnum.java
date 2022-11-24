package game.server.manager.common.enums;

/**
 * @author yuzhanfeng
 * @Date 2022/8/11 15:00
 */
public enum ServerMessageTypeEnum {

    /**/
    HEARTBEAT("heartbeat","心跳"),
    CONNECT_SUCCESS("connect_success","连接成功"),
    SUCCESS("success","服务器异常"),
    ERROR("error","服务器异常"),
    PULL_IMAGE("pull","脚本"),
    PUSH_IMAGE("push","卸载");


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
