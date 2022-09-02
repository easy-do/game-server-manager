package game.server.manager.common.exception;


/**
 * 异常工厂
 * @author laoyu
 * @version 1.0
 * @date 2022/3/4
 */
public class ExceptionFactory {

    public static BaseException baseException() {
        return new BaseException();
    }

    public static BaseException baseException(String message) {
        return new BaseException(message);
    }

    public static  BaseException baseException(String errCode, String message) {
        return new BaseException(errCode, message);
    }

    public static  BaseException baseException(String errCode, String message, Throwable cause) {
        return new BaseException(errCode, message, cause);
    }

    public static BaseException baseException(String errCode, Throwable cause) {
        return new BaseException(errCode, cause);
    }


    public static BizException bizException() {
        return new BizException();
    }

    public static BizException bizException(String message) {
        return new BizException(message);
    }

    public static BizException bizException(String errCode, String message) {
        return new BizException(errCode, message);
    }

    public static BizException bizException(String errCode, String message, Throwable cause) {
        return new BizException(errCode, message, cause);
    }

    public static BizException bizException(String errCode, Throwable cause) {
        return new BizException(errCode, cause);
    }

    public static SysException sysException() {
        return new SysException();
    }

    public static SysException sysException(String message) {
        return new SysException(message);
    }

    public static SysException sysException(String errCode, String message) {
        return new SysException(errCode, message);
    }

    public static SysException sysException(String errCode, String message, Throwable cause) {
        return new SysException(errCode, message, cause);
    }

    public static SysException sysException(String errCode, Throwable cause) {
        return new SysException(errCode, cause);
    }

    public static HasPermissionException hasPermissionException() {
        return new HasPermissionException();
    }

    public static HasPermissionException hasPermissionException(String message) {
        return new HasPermissionException(message);
    }

    public static HasPermissionException hasPermissionException(String errCode, String message) {
        return new HasPermissionException(errCode, message);
    }

    public static HasPermissionException hasPermissionException(String errCode, String message, Throwable cause) {
        return new HasPermissionException(errCode, message, cause);
    }

    public static HasPermissionException hasPermissionException(String errCode, Throwable cause) {
        return new HasPermissionException(errCode, cause);
    }



}
