package game.server.manager.common.enums;

/**
 * @author laoyu
 * @version 1.0
 * @date 2022/5/21
 */

public enum ExecScriptEnum {
    /**/
    NO_DEPLOYMENT(0,"未部署"),

    QUEUE(1,"排队中"),

    DEPLOYMENT_FAILED(4,"脚本执行失败"),

    STARTING(9,"执行中"),

    WAIT_CLIENT(10,"等待客户端"),

    FINISH(1,"完成"),

    FAILED(2,"失败");


    private final int status;
    private final String desc;

    ExecScriptEnum(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public int getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }
}
