package plus.easydo.common.enums;

/**
 * @author laoyu
 * @version 1.0
 */
public enum DockerModelEnum {
    /**/
    HTTP("http","api直连"),
    SOCKET("socket","客户端模式");
    private final String type;
    private final String desc;

    DockerModelEnum(String type, String desc) {
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
