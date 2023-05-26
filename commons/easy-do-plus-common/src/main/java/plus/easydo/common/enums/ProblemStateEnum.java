package plus.easydo.common.enums;

/**
 * @author laoyu
 * @version 1.0
 * @date 2022/5/21
 */

public enum ProblemStateEnum {
    /**/
    DRAFT(0,"草稿"),
    SUBMIT(1,"提交"),
    AUDIT(2,"审核");


    private final int state;
    private final String desc;

    ProblemStateEnum(int state, String desc) {
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
