package plus.easydo.generate.dynamic.core.enums;

/**
 * @author yuzhanfeng
 */
public enum SourceTypeEnum {

    /** mysql数据库*/
    MYSQL("MYSQL数据库","MYSQL"),
    /** http请求*/
    HTTP("http接口","HTTP");

    private  final String name;
    private  final String code;

    SourceTypeEnum(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }
}
