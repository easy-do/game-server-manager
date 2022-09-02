package game.server.manager.common.exception;


/**
 * 业务异常
 * @author laoyu
 * @version 1.0
 * @date 2022/3/4
 */
public class BizException extends BaseException {

    private static final long serialVersionUID = 1L;

    private static final String DEFAULT_ERR_CODE = "BIZ_ERROR";

    private static final String DEFAULT_ERR_MESSAGE = "业务异常";

    public BizException() {
        super(DEFAULT_ERR_CODE,DEFAULT_ERR_MESSAGE);
    }

    public BizException(String message) {
        super(message);
    }

    public BizException(String errCode, String message) {
        super(errCode, message);
    }

    public BizException(String errCode, String message, Throwable cause) {
        super(errCode, message, cause);
    }

    public BizException(String errCode, Throwable cause) {
        super(errCode, cause);
    }
}
