package game.server.manager.mybatis.plus.enums;

/**
 * @author laoyu
 * @version 1.0
 * @description 查询类型枚举
 * @date 2022/9/5
 */

public enum SearchTypeEnum {

    /**/
    EQ("eq","等于"),
    NE("ne","不等于"),
    IN("in","包含"),
    NOT_IN("notIn","包含"),
    LT("lt","小于"),
    GT("gt","大于"),
    GE("ge","大于等于 >="),
    LE("ge","小于等于 >="),
    BETWEEN("between","范围内"),
    NOT_BETWEEN("noBetween","不在范围内"),
    LIKE("like","包含"),
    NOT_LIKE("like","不包含"),
    LIKE_LEFT("likeLeft","起始包含"),
    LIKE_RIGHT("likeRight","结尾包含"),
    NULL("null","空");


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
