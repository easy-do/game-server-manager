package game.server.manager.common.exception;


/**
 * 解锁失败异常
 * @author yuzhanfeng
 */
public class LockFailedException extends BaseException {

    private static final long serialVersionUID = 1L;

    private static final String DEFAULT_ERR_CODE = "LOCK_ERROR";

    private static final String DEFAULT_ERR_MESSAGE = "获取锁失败异常";

    public LockFailedException() {
        super(DEFAULT_ERR_CODE,DEFAULT_ERR_MESSAGE);
    }

    public LockFailedException(String message) {
        super(message);
    }

    public LockFailedException(String errCode, String message) {
        super(errCode, message);
    }

    public LockFailedException(String errCode, String message, Throwable cause) {
        super(errCode, message, cause);
    }

    public LockFailedException(String errCode, Throwable cause) {
        super(errCode, cause);
    }
}
