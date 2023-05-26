package plus.easydo.common.enums;

/**
 * @author laoyu
 * @version 1.0
 * @date 2022/5/21
 */

public enum ScriptTypeEnum {
    /**/
    INSTALL("install","部署"),
    RESTART("restart","重启"),
    STOP("stop","停止"),
    UNINSTALL("uninstall","卸载"),
    CUSTOM("custom","自定义"),
    BASIC("basic","基础脚本");


    private final String code;
    private final String desc;

    ScriptTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
