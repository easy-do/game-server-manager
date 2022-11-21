package game.server.manager.common.enums;

/**
 * @author laoyu
 * @version 1.0
 */
public enum DockerSocketTypeEnum {
    /**/
    PULL("pullImage","pull镜像");

    private final String type;
    private final String desc;

    DockerSocketTypeEnum(String type, String desc) {
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
