package game.server.manager.common.enums;

/**
 * @author laoyu
 * @version 1.0
 * @description 登录类型枚举
 * @date 2022/6/14
 */

public enum LoginTypeEnums {

    /**/
    EMAIL("email","邮箱登录"),
    PASSWORD("password","密码登陆"),
    SECRET("secret","密钥登录");



    private final String type;
    private final String desc;

    LoginTypeEnums(String type, String desc) {
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
