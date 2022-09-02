package game.server.manager.common.enums;

/**
 * @author laoyu
 * @version 1.0
 * @date 2022/5/28
 */

public enum ScopeEnum {

    /**/
    PUBLIC("public","公开"),
    PRIVATE("private","私有"),
    SUBSCRIBE("subscribe","订阅");



    private final String scope;
    private final String desc;

    ScopeEnum(String scope, String desc) {
        this.scope = scope;
        this.desc = desc;
    }

    public String getScope() {
        return scope;
    }

    public String getDesc() {
        return desc;
    }
}
