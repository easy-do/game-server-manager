package plus.easydo.common.enums;

/**
 * @author laoyu
 * @version 1.0
 */
public enum StatusEnum {
    /**/
    ENABLE(0,"开启"),
    DISABLE(1,"禁用");

    private final Integer code;
    private final String desc;

    StatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
