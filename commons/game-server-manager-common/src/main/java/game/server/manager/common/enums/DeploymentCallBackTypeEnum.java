package game.server.manager.common.enums;

/**
 * @author yuzhanfeng
 */

public enum DeploymentCallBackTypeEnum {
    /**/
    BEFORE("before","前置"),
    FINISH("finish","完成"),
    EXCEPTION("exception", "异常");

    private final String type;

    private final String desc;

    DeploymentCallBackTypeEnum(String type, String desc) {
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
