package plus.easydo.common.exception;


/**
 * 自定义异常
 *
 * @author ruoyi
 */
public class CustomException extends BaseException {

    private static final long serialVersionUID = 1L;

    private static final String DEFAULT_ERR_CODE = "CUSTOM_ERROR";

    private static final String DEFAULT_ERR_MESSAGE = "自定义异常";

    public CustomException() {
        super(DEFAULT_ERR_CODE,DEFAULT_ERR_MESSAGE);
    }

    public CustomException(String message) {
        super(message);
    }

    public CustomException(String errCode, String message) {
        super(errCode, message);
    }

    public CustomException(String errCode, String message, Throwable cause) {
        super(errCode, message, cause);
    }

    public CustomException(String errCode, Throwable cause) {
        super(errCode, cause);
    }
}
