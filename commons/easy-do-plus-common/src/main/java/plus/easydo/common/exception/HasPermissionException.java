package plus.easydo.common.exception;


/**
 * 鉴权异常
 *
 * @author yuzhanfeng
 */
public class HasPermissionException extends BaseException {

    private static final long serialVersionUID = 1L;

    private static final String DEFAULT_ERR_CODE = "AUTH_ERROR";

    private static final String DEFAULT_ERR_MESSAGE = "鉴权异常";

    public HasPermissionException() {
        super(DEFAULT_ERR_CODE,DEFAULT_ERR_MESSAGE);
    }

    public HasPermissionException(String message) {
        super(message);
    }

    public HasPermissionException(String errCode, String message) {
        super(errCode, message);
    }

    public HasPermissionException(String errCode, String message, Throwable cause) {
        super(errCode, message, cause);
    }

    public HasPermissionException(String errCode, Throwable cause) {
        super(errCode, cause);
    }
}
