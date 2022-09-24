package game.server.manager.oss.exception;


import game.server.manager.common.exception.BaseException;

/**
 * 文件存储异常异常
 *
 * @author ruoyi
 */
public class OssStoreException extends BaseException {

    private static final long serialVersionUID = 1L;

    private static final String DEFAULT_ERR_CODE = "FILE_ERROR";

    private static final String DEFAULT_ERR_MESSAGE = "文件存储异常";

    public OssStoreException() {
        super(DEFAULT_ERR_CODE,DEFAULT_ERR_MESSAGE);
    }

    public OssStoreException(String message) {
        super(message);
    }

    public OssStoreException(String errCode, String message) {
        super(errCode, message);
    }

    public OssStoreException(String errCode, String message, Throwable cause) {
        super(errCode, message, cause);
    }

    public OssStoreException(String errCode, Throwable cause) {
        super(errCode, cause);
    }
}
