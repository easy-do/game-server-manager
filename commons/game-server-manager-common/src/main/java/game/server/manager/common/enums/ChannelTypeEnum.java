package game.server.manager.common.enums;

/**
 * @author laoyu
 * @version 1.0
 * @description 通道类型
 * @date 2022/7/9
 */

public enum ChannelTypeEnum {

    /** 客户端 */
    CLIENT("client","客户端"),
    /** 游览器 */
    BROWSER("browser","游览器");


    private final String type;
    private final String desc;


    ChannelTypeEnum(String type, String desc) {
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
