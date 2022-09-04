package game.server.manager.web.config;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.SaTokenException;
import cn.hutool.core.lang.Validator;
import game.server.manager.common.constant.HttpStatus;
import game.server.manager.common.exception.BaseException;
import game.server.manager.common.exception.CustomException;
import game.server.manager.common.exception.HasPermissionException;
import game.server.manager.common.result.DataResult;
import game.server.manager.common.result.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

/**
 * 全局异常处理器
 *
 * @author ruoyi
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 基础异常
     *
     * @param e e
     * @return R R
     */
    @ExceptionHandler(BaseException.class)
    public R<Object> baseException(BaseException e) {
        return DataResult.fail(e.getMessage());
    }

    /**
     * 业务异常
     *
     * @param e e
     * @return R R
     */
    @ExceptionHandler(CustomException.class)
    public R<Object> businessException(CustomException e) {
        if (Validator.isNull(e.getErrCode())) {
            return DataResult.fail(e.getMessage());
        }
        return DataResult.fail(e.getErrCode(), e.getMessage());
    }

    /**
     * 功能描述
     *
     * @param e e
     * @return plus.easydo.core.result.R
     * @author laoyu
     */
    @ExceptionHandler(Exception.class)
    public R<Object> handleException(Exception e) {
        log.error(e.getMessage(), e);
        return DataResult.fail(e.getMessage());
    }

    /**
     * 自定义验证异常
     *
     * @param e e
     * @return plus.easydo.core.result.R
     * @author laoyu
     */
    @ExceptionHandler(BindException.class)
    public R<Object> validatedBindException(BindException e) {
        log.error(e.getMessage(), e);
        String message = e.getAllErrors().get(0).getDefaultMessage();
        return DataResult.fail(message);
    }

    /**
     * 自定义验证异常
     *
     * @param e e
     * @return java.lang.Object
     * @author laoyu
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object validExceptionHandler(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        String message;
        message = Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage();
        return DataResult.fail(message);
    }

    /**
     * 鉴权失败异常
     *
     * @param e e
     * @return java.lang.Object
     * @author laoyu
     */
    @ExceptionHandler(HasPermissionException.class)
    public Object validExceptionHandler(HasPermissionException e) {
        if (Validator.isNull(e.getMessage())) {
            return DataResult.fail(HttpStatus.FORBIDDEN, "没有访问权限。");
        }
        return DataResult.fail(HttpStatus.FORBIDDEN, e.getMessage());
    }

    /**
     * 鉴权失败异常
     *
     * @param e e
     * @return java.lang.Object
     * @author laoyu
     */
    @ExceptionHandler(SaTokenException.class)
    public Object handlerSaTokenException(SaTokenException e) {
        if (Validator.isNull(e.getMessage())) {
            return DataResult.fail(HttpStatus.FORBIDDEN, "没有访问权限。");
        }
        return DataResult.fail(HttpStatus.FORBIDDEN, e.getMessage());
    }

    /**
     * 鉴权失败异常
     *
     * @param nle nle
     * @return java.lang.Object
     * @author laoyu
     */
    @ExceptionHandler(NotLoginException.class)
    public Object handlerNotLoginException(NotLoginException nle) {
        if (Validator.isNull(nle.getMessage())) {
            return DataResult.fail(HttpStatus.FORBIDDEN, "没有访问权限。");
        }
        return DataResult.fail(HttpStatus.FORBIDDEN, nle.getMessage());
    }


}
