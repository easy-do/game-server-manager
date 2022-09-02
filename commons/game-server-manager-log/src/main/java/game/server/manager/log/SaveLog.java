package game.server.manager.log;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author laoyu
 * @version 1.0
 * @description 保存日志注解
 * @date 2022/6/18
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SaveLog {

    /**
     * 系统名称，不指定时，取"${systemName:${spring.application.name}}"
     *
     */
    String systemName() default "";

    /**
     * 日志类型1（必填）：操作日志、重要数据变更日志、系统日志、API接口调用日志
     *
     */
    String logType() default "";

    /**
     * 源地址，发起人ip
     *
     */
    String sourceIp() default "";

    /**
     * 目标地址，服务ip
     *
     */
    String targetIp() default "";

    /**
     * 请求地址
     *
     */
    String path() default "";

    /**
     * 请求参数
     *
     */
    String params() default "";

    /**
     *  操作人id
     *
     */
    String operatorId() default "";


    /**
     *  操作人名称
     *
     */
    String operatorName() default "";


    /**
     * 客户端类型：PC端（默认）、移动端
     *
     */
    String clientType() default "PC端";


    /**
     * 模块名称
     *
     */
    String moduleName() default "";

    /**
     * 操作类型（自定义）
     *
     */
    String actionType() default "";

    /**
     * 操作内容（必填）
     *
     */
    @AliasFor("value")
    String description() default "";

    /**
     * 值，同操作内容
     *
     */
    @AliasFor("description")
    String value() default "";

    /**
     * 表达式
     *
     */
    String[] expressions() default {};

    /**
     * 操作状态。成功、失败
     *
     */
    String operationStatus() default "成功";

    /**
     * 错误信息
     *
     */
    String errMessage() default "";

    /**
     * 其他扩展信息，格式为json字符串，如{"a":"1","b":"2"}
     *
     */
    String extMap() default "";

    /**
     * 是否持久化保存至数据库
     *
     */
    boolean saveDb() default true;
}
