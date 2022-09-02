package game.server.manager.common.exception;

/**
 * @author laoyu
 * @version 1.0
 * @date 2022/5/19
 */

public class AuthorizationException extends BaseException {

    private static final long serialVersionUID = 1L;
    private static final String DEFAULT_ERR_CODE = "AUTHORIZATION_ERROR";
    private static final String DEFAULT_ERR_MESSAGE = "授权异常";
    private final String errCode;

    public AuthorizationException() {
        super(DEFAULT_ERR_MESSAGE);
        this.errCode = DEFAULT_ERR_CODE;
    }

    public AuthorizationException(String message) {
        super(message);
        this.errCode = DEFAULT_ERR_CODE;
    }

    public AuthorizationException(String errCode, String message) {
        super(message);
        this.errCode = errCode;
    }

    public AuthorizationException(String errCode, String message, Throwable cause) {
        super(message, cause);
        this.errCode = errCode;
    }

    public String getErrCode() {
        return this.errCode;
    }
}
