package plus.easydo.common.enums;

/**
 * @author laoyu
 * @version 1.0
 * @description 定时任务类型枚举
 * @date 2022/6/8
 */

public enum JobTypeEnums {
    /**/
    SCRIPT("script","ScriptExecJob.execScript","脚本任务"),
    BEAN("bean","","普通任务");



    private final String type;
    private final String jobClass;
    private final String desc;

    JobTypeEnums(String type, String jobClass, String desc) {
        this.type = type;
        this.jobClass = jobClass;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public String getJobClass() {
        return jobClass;
    }

    public static String getJobClassByType(String code) {
        for (JobTypeEnums jobTypeEnums:JobTypeEnums.values()) {
            if (jobTypeEnums.type.equals(code)){
                return jobTypeEnums.jobClass;
            }
        }
        return "";
    }

    public String getDesc() {
        return desc;
    }
}
