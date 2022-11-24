package game.server.manager.common.enums;

/**
 * @author yuzhanfeng
 * @Date 2022/11/24 17:12
 * @Description 游览器消息类型枚举
 */
public enum BrowserMessageTypeEnum {

    /**/
    DEPLOY_LOG("deploy_log","部署日志"),
    PULL("pullImage","pull镜像");

    private final String type;
    private final String desc;

    BrowserMessageTypeEnum(String type, String desc) {
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
