package plus.easydo.common.enums;

/**
 * @author laoyu
 * @version 1.0
 * @description 应用日志状态
 * @date 2023/4/8
 */

public enum ApplicationInstallLogStatusenum {

    /***/
    START(0,"下发"),
    INSTALL_ING(1,"安装中"),

    SUCCESS(2,"成功"),
    FAILED(3,"失败");


    private final Integer status;
    private final String desc;

    ApplicationInstallLogStatusenum(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public static boolean isFinish(Integer status){
        return status.equals(SUCCESS.getStatus()) || status.equals(FAILED.getStatus());
    }

    public Integer getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }

}
