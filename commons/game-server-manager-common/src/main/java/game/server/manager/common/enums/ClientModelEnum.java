package game.server.manager.common.enums;

/**
 * @author laoyu
 * @version 1.0
 */
public enum ClientModelEnum {
    /**/
    HTTP("http","http通信模式"),
    SOCKET("socket","socket通信模式");
    private final String type;
    private final String desc;

    ClientModelEnum(String type, String desc) {
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
