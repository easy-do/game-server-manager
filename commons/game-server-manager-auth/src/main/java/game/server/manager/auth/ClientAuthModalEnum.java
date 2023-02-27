package game.server.manager.auth;

import cn.hutool.core.collection.ListUtil;

import java.util.List;

/**
 * @author yuzhanfeng
 * @Date 2023-02-27 9:44
 * @Description 客户端授权模式枚举类
 */
public enum ClientAuthModalEnum {

    /** 授权码模式 */
    AUTHORIZATION_CODE("authorization_code","授权码模式"),
    /** 密码式 */
    PASSWORD("password", "密码式"),
    /** 隐藏式 */
    IMPLICIT("implicit", "隐藏式"),
    /** 凭证式 */
    CLIENT_CREDENTIALS("client_credentials", "凭证式");


    private final String value;

    private final String desc;

    ClientAuthModalEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    /**
     * 获取所有授权模式
     *
     * @return java.util.List<java.lang.String>
     * @author laoyu
     * @date 2023-02-27
     */
    public static List<String> getAllClientAuthModal(){
        return ListUtil.of(AUTHORIZATION_CODE.value, PASSWORD.value, IMPLICIT.value, CLIENT_CREDENTIALS.value);
    }

    public String getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }
}


