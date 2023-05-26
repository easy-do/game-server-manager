package plus.easydo.common.enums;

/**
 * @author laoyu
 * @version 1.0
 * @date 2022/5/21
 */

public enum AuthCodeStateEnum {
    /**/
    NO_USE(0,"未使用"),
    USE(1,"已使用"),
    REUSABLE(2,"重复使用"),
    DISCARD(3,"部署失败");


    private final int state;
    private final String desc;

    AuthCodeStateEnum(int state, String desc) {
        this.state = state;
        this.desc = desc;
    }

    public int getState() {
        return state;
    }

    public String getDesc() {
        return desc;
    }
}
