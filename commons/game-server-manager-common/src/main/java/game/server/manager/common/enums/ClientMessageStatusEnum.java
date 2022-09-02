package game.server.manager.common.enums;

/**
 * @author laoyu
 * @version 1.0
 * @date 2022/5/21
 */

public enum ClientMessageStatusEnum {
    /**/
    WAIT_CLIENT(0,"等待客户端"),
    ACCEPT(1,"客户端接收"),
    CONSUME(2,"客户端消费");


    private final int code;
    private final String desc;

    ClientMessageStatusEnum(int code, String desc) {
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
