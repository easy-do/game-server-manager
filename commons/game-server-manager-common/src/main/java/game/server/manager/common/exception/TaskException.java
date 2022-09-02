package game.server.manager.common.exception;


/**
 * @author laoyu
 * @version 1.0
 * @description 定时任务异常
 * @date 2022/6/8
 */

public class TaskException extends BaseException {

    private static final long serialVersionUID = 1L;

    private Code code;

    public TaskException(String msg, Code code) {
        this(msg, code, null);
    }

    public TaskException(String msg, Code code, Exception nestedEx) {
        super(msg, nestedEx);
        this.code = code;
    }


    public enum Code {

        /***/
        TASK_EXISTS, NO_TASK_EXISTS, TASK_ALREADY_STARTED, UNKNOWN, CONFIG_ERROR, TASK_NODE_NOT_AVAILABLE
    }
}
