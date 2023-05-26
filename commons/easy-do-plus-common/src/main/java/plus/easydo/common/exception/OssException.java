package plus.easydo.common.exception;


/**
 * 文件存储异常异常
 *
 * @author ruoyi
 */
public class OssException extends BaseException {

    private static final long serialVersionUID = 1L;

    private static final String DEFAULT_ERR_CODE = "FILE_ERROR";

    private static final String DEFAULT_ERR_MESSAGE = "文件存储异常";

    public OssException() {
        super(DEFAULT_ERR_CODE,DEFAULT_ERR_MESSAGE);
    }

    public OssException(String message) {
        super(message);
    }

    public OssException(String errCode, String message) {
        super(errCode, message);
    }

    public OssException(String errCode, String message, Throwable cause) {
        super(errCode, message, cause);
    }

    public OssException(String errCode, Throwable cause) {
        super(errCode, cause);
    }
}
