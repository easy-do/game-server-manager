package plus.easydo.common.constant;

/**
 * @author yuzhanfeng
 */
public enum SystemSourceTypeEnum {

    /** 按钮牛 */
    BUTTON("A","按钮"),
    /** 接口 */
    INTERFACE("I","接口"),
    /** 菜单 */
    MENU("M","菜单");

    private  final String value;

    private  final String desc;

    SystemSourceTypeEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }
}
