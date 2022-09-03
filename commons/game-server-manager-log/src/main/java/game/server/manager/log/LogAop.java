package game.server.manager.log;

/**
 * @author laoyu
 * @version 1.0
 * @description log切面
 * @date 2022/6/18
 */

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.text.CharSequenceUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import game.server.manager.auth.AuthorizationUtil;
import game.server.manager.common.utils.IpRegionSearchUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import game.server.manager.common.result.R;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Aspect
@Slf4j
@Component
public class LogAop {

    /**
     * 审计日志固定logger内容
     */
    public static final String LOG_AOP = "LOG_AOP";

    /**
     * logger=AUDIT_LOG
     */
    private static final Logger logger = LoggerFactory.getLogger(LOG_AOP);

    @Autowired
    private ObjectMapper om;

    /**
     * 系统名称
     */
    @Value("${systemName:${spring.application.name}}")
    private String systemName;

    
    /**
     * 切面：SaveLog注解
     */
    @Pointcut("@annotation(game.server.manager.log.SaveLog)")
    public void saveLog() {
        // DO NOTHING
    }

    /**
     * 创建表达式解析器
     */
    private final SpelExpressionParser parser = new SpelExpressionParser();

    /**
     * 变量占位符
     */
    private static final String SHARP = "?";

    @Autowired
    private LogDbService logDbService;

    /**
     * 日志记录
     *
     * @param pjp pjp
     * @return java.lang.Object
     * @author laoyu
     * @date 2022/6/18
     */
    @Around("saveLog()")
    public Object saveLog(ProceedingJoinPoint pjp) throws Throwable {
        // 获取注解内容
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        SaveLog saveLog = method.getAnnotation(SaveLog.class);
        try {
            Object result = pjp.proceed();
            // 根据接口执行结果得到操作状态
            if (result instanceof R<?> && !((R<?>) result).isSuccess()) {
                // 请求失败，操作状态：失败
                save("失败", ((R<?>) result).getErrorMessage(), saveLog, pjp);
            }else {
                // 请求成功，操作状态：成功
                save("成功", null, saveLog, pjp);
            }
            return result;
        } catch (Throwable t) {
            save("失败", t.getMessage(), saveLog, pjp);
            throw t;
        }
    }

    /**
     * 打印日志
     *
     * @param operationStatus operationStatus
     * @param errMessage errMessage
     * @param saveLog SaveLog
     * @param joinPoint joinPoint
     * @author laoyu
     * @date 2022/6/18
     */
    private void save(String operationStatus, String errMessage, SaveLog saveLog, JoinPoint joinPoint) {
        try {
            // 参数整理，格式为：p0=x,p1=x
            String params = getParams(joinPoint);
            // 描述中支持#参数
            String value = getMessage(saveLog, joinPoint);
            Log logModel = Log.builder()
                    // 源地址：优先取注解内容
                    .sourceIp(StringUtils.hasText(saveLog.sourceIp()) ? saveLog.sourceIp() : IpRegionSearchUtil.searchRequestIp())
                    // 目标地址：优先取注解内容
                    .targetIp(StringUtils.hasText(saveLog.targetIp()) ? saveLog.targetIp() : getServerIp())
                    // path：优先取注解内容
                    .path(StringUtils.hasText(saveLog.path()) ? saveLog.path() : getRequestUri())
                    // 参数：优先取注解内容
                    .params(StringUtils.hasText(saveLog.params()) ? saveLog.params() : params)
                    // 操作人id：优先取注解内容
                    .operatorId(StringUtils.hasText(saveLog.operatorId()) ? saveLog.operatorId() : getUserId())
                    // 操作人名称：优先取注解内容
                    .operatorName(StringUtils.hasText(saveLog.operatorName()) ? saveLog.operatorName() : getUserName())
                    // 操作内容
                    .description(value)
                    // 系统名称：若注解中未指定则取配置内容
                    .systemName(StringUtils.hasText(saveLog.systemName()) ? saveLog.systemName() : systemName)
                    // 模块名称
                    .moduleName(saveLog.moduleName())
                    // 操作状态：优先取注解内容
                    .operationStatus(operationStatus)
                    // 错误信息：优先取注解内容
                    .errMessage(errMessage)
                    // 客户端类型
                    .clientType(saveLog.clientType())
                    // 操作类型
                    .actionType(saveLog.actionType())
                    // 扩展属性
                    .extMap(extMap(saveLog.extMap()))
                    //操作时间
                    .operationTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                    .build();
                    save(logModel,saveLog.saveDb());
        } catch (Throwable t) {
            log.error("save log error", t);
        }
    }

    private String getUserId() {
        if(StpUtil.isLogin()){
            return StpUtil.getLoginIdAsString();
        }
        return null;
    }


    private String getUserName() {
        if(StpUtil.isLogin()){
            try {
                AuthorizationUtil.getUser().getNickName();
            }catch (Exception e){
                return null;
            }

        }
        return null;
    }

    /**
     * 获取请求参数，格式为p0=x,p1=x,
     *
     * @param joinPoint
     * @return
     */
    private String getParams(JoinPoint joinPoint) {
        StringBuilder params = new StringBuilder();
        try {
            Object[] args = joinPoint.getArgs();
            if (args != null) {
                for (int i = 0; i < args.length; i++) {
                    params.append("p");
                    params.append(i);
                    params.append("=");
                    params.append(toParamString(args[i]));
                    params.append(",");
                }
            }
        } catch (Exception e) {
            log.error("logAop get params error: {} ", e.getMessage());
        }
        return params.toString();
    }

    /**
     * 参数转string
     *
     * @param arg
     * @return
     * @throws JsonProcessingException
     */
    private String toParamString(Object arg) throws JsonProcessingException {
        String argStr;
        if (arg instanceof String) {
            // 字符串类型
            argStr = (String) arg;
        } else if (arg instanceof ServletRequest || arg instanceof ServletResponse || arg instanceof Model || arg instanceof MultipartFile) {
            // 特殊类型的只展示类型名称
            argStr = arg.getClass().getName();
        } else {
            // 其他类型，转字符串
            argStr = om.writerWithDefaultPrettyPrinter().writeValueAsString(arg);
        }
        return argStr;
    }

    /**
     * 获取变量信息
     *
     * @param saveLog
     * @param joinPoint
     * @return
     */
    private String getMessage(SaveLog saveLog, JoinPoint joinPoint) {
        try {
            // 若操作内容中含#，进行变量替换
            String description = saveLog.description();
            if (description != null && description.contains(SHARP)) {
                // 通过evaluationContext.setVariable可以在上下文中设定变量。
                EvaluationContext context = new StandardEvaluationContext();

                Object[] ps = joinPoint.getArgs();
                if (ps != null) {
                    // 注：#后的变量序号从1开始
                    for (int i = 0; i < ps.length; i++) {
                        context.setVariable("p" + (i + 1), ps[i]);
                    }
                }
                //批量生成表达式并替换对应参数
                String[] expressions = saveLog.expressions();
                for (int i = 0; i < expressions.length; i++) {
                    String value = parser.parseExpression(expressions[i]).getValue(context, String.class);
                    description = CharSequenceUtil.replace(description,SHARP+(i+1),value);
                }

            }
            return description;

        } catch (Exception e) {
            log.error("logAop get message error, template: {} ", saveLog.description(), e);
            return saveLog.description();
        }
    }

    /**
     * 获取服务IP
     *
     * @return
     */
    private String getServerIp() {
        if (RequestContextHolder.getRequestAttributes() == null) {
            return null;
        }
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request.getServerName();
    }

    /**
     * 获取请求uri
     *
     * @return
     */
    private String getRequestUri() {
        if (RequestContextHolder.getRequestAttributes() == null) {
            return null;
        }
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request.getRequestURI();
    }

    /**
     * 保存日志
     *
     * @param log log
     * @return game.server.manager.log.Log
     * @author laoyu
     * @date 2022/6/18
     */
    public Log save(Log log,boolean saveDb) {
        try {
            logger.info(om.writeValueAsString(log));
            if(saveDb && Objects.nonNull(this.logDbService)){
                CompletableFuture.runAsync(()->logDbService.save(log));
            }
        } catch (JsonProcessingException e) {
            logger.error("AuditLog save error, message:{} ", log, e);
        }
        return log;
    }

    /**
     * 其他扩展信息
     *
     * @param extMapStr
     * @return
     */
    public Map<String, String> extMap(String extMapStr) {
        if (StringUtils.hasText(extMapStr)) {
            try {
                return om.readValue(extMapStr, new TypeReference<>() {
                });
            } catch (JsonProcessingException e) {
                log.warn("Log extMap JsonProcessingException:{} ", extMapStr, e);
                return new HashMap<>(16);
            }
        } else {
            return new HashMap<>(16);
        }
    }

}
