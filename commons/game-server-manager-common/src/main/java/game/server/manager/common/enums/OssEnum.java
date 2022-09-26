package game.server.manager.common.enums;

/**
 * @author yuzhanfeng
 * @Date 2022/9/26 18:11
 */
public enum OssEnum {

    /**/
    LOCAL("local","本地"),
    MINIO("minio","minio");



    private final String type;
    private final String desc;

    OssEnum(String type, String desc) {
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
