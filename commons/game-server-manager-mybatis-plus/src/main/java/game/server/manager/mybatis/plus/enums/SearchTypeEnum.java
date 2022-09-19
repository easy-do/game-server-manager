package game.server.manager.mybatis.plus.enums;

/**
 * @author laoyu
 * @version 1.0
 * @description 查询类型枚举
 * @date 2022/9/5
 */

public enum SearchTypeEnum {

    /**/
    EQ("EQ","等于"),
    NE("NE","不等于"),
    IN("IN","包含"),
    NOT_IN("NOT_IN","包含"),
    LT("LT","小于"),
    GT("GT","大于"),
    GE("GE","大于等于 >="),
    LE("LE","小于等于 >="),
    BETWEEN("BETWEEN","范围内"),
    NOT_BETWEEN("NOT_BETWEEN","不在范围内"),
    LIKE("LIKE","包含"),
    NOT_LIKE("NOT_LIKE","不包含"),
    LIKE_LEFT("LIKE_LEFT","起始包含"),
    LIKE_RIGHT("LIKE_RIGHT","结尾包含"),
    NULL("NULL","空");


    private final String value;

    private final String desc;

    SearchTypeEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public static SearchTypeEnum matchValue(String value){
        for (SearchTypeEnum searchTypeEnum : SearchTypeEnum.values()) {
            if (searchTypeEnum.value.equals(value)) {
                return searchTypeEnum;
            }
        }
        return SearchTypeEnum.NULL;
    }
}
