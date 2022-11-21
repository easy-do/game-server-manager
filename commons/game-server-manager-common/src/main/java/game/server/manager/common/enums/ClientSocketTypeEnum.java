package game.server.manager.common.enums;

/**
 * @author laoyu
 * @version 1.0
 */
public enum ClientSocketTypeEnum {
    /**/
    CONNECT("connect","建立链接"),

    HEARTBEAT("heartbeat","同步"),

    SYNC("sync","同步");

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
