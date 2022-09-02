package game.server.manager.common.enums;

/**
 * @author laoyu
 * @version 1.0
 */
public enum DataTypeEnum {
    /**/
    UP("up","存活状态"),
    SYSTEM_DATA("systemData","系统信息");

    private final String key;
    private final String desc;

    DataTypeEnum(String key, String desc) {
        this.key = key;
        this.desc = desc;
    }

    public String getKey() {
        return key;
    }

    public String getDesc() {
        return desc;
    }
}
