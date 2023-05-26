package plus.easydo.common.enums;

/**
 * @author yuzhanfeng
 * @Date 2022/9/14 10:49
 * @Description 资源类型枚举
 */
public enum ResourceTypeEnum {

    /** 菜单 */
    MENU("M","菜单"),
    /** 功能 */
    FUNCTION("F","功能");


    private final String type;
    private final String desc;

    ResourceTypeEnum(String type, String desc) {
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
