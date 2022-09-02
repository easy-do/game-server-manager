package game.server.manager.server.job.util;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import game.server.manager.server.entity.SysJob;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * 任务执行工具
 *
 * @author ruoyi
 */
public class JobInvokeUtil {

    /**
     * 执行方法
     *
     * @param sysJob 系统任务
     */
    public static Object invokeMethod(SysJob sysJob) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String invokeTarget = sysJob.getInvokeTarget();
        String[] values = invokeTarget.split("\\.");
        String className = StrUtil.lowerFirst(values[0]);
        String methodName = values[1];
        Object bean = SpringUtil.getBean(className);
        Class<Object> clazz = ClassUtil.getClass(bean);
        Method method = clazz.getMethod(methodName, SysJob.class);
        return method.invoke(bean, sysJob);
    }

}
