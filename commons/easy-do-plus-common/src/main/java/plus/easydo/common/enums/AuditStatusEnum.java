package plus.easydo.common.enums;

/**
 * @author laoyu
 * @version 1.0
 * @description 讨论的状态
 * @date 2022/7/9
 */

public enum AuditStatusEnum {

    /***/
    DRAFT(0,"草稿"),
    AUDIT_SUCCESS(1,"审核通过"),
    AUDIT_ING(2,"审核中"),
    AUDIT_FAILED(3,"审核驳回"),

    LOCKED(4,"审核锁定");


    private final Integer state;
    private final String desc;

    AuditStatusEnum(int state, String desc) {
        this.state = state;
        this.desc = desc;
    }

    public Integer getState() {
        return state;
    }

    public String getDesc() {
        return desc;
    }

    public static boolean canCommitAudit(Integer status){
        return status.equals(AuditStatusEnum.DRAFT.getState()) || status.equals(AuditStatusEnum.AUDIT_FAILED.getState());
    }

    public static boolean canAudit(Integer status){
        return !status.equals(AuditStatusEnum.AUDIT_SUCCESS.getState()) && !status.equals(AuditStatusEnum.DRAFT.getState());
    }

    public static String getDesc(Integer status){
        for(AuditStatusEnum auditStatusEnum : AuditStatusEnum.values()){
            if(auditStatusEnum.getState().equals(status)){
                return auditStatusEnum.getDesc();
            }
        }
        return "";
    }
}
