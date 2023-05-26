package plus.easydo.common.enums;

/**
 * @author laoyu
 * @version 1.0
 * @date 2022/5/21
 */

public enum DeviceTypeEnum {
    /**/
    SERVER(0,"服务器"),

    CLIENT(1,"客户端");


    private final int type;
    private final String desc;

    DeviceTypeEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
