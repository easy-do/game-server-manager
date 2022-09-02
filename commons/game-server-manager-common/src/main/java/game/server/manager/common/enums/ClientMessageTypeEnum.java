package game.server.manager.common.enums;

/**
 * @author yuzhanfeng
 * @Date 2022/8/11 15:00
 */
public enum ClientMessageTypeEnum {

    /**/
    SCRIPT(0,"脚本"),
    UNINSTALL(1,"卸载");


    private final int code;
    private final String desc;

    ClientMessageTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
