package plus.easydo.common.exception;


/**
 * 系统异常
 * @author laoyu
 * @version 1.0
 * @date 2022/3/4
 */
public class SysException extends BaseException {

    private static final long serialVersionUID = 1L;

    private static final String DEFAULT_ERR_CODE = "SYS_ERROR";

    private static final String DEFAULT_ERR_MESSAGE = "系统异常";

    public SysException() {
        super(DEFAULT_ERR_CODE,DEFAULT_ERR_MESSAGE);
    }

    public SysException(String message) {
        super(message);
    }

    public SysException(String errCode, String message) {
        super(errCode, message);
    }

    public SysException(String errCode, String message, Throwable cause) {
        super(errCode, message, cause);
    }

    public SysException(String errCode, Throwable cause) {
        super(errCode, cause);
    }
}
