package plus.easydo.service;

import cn.hutool.core.util.StrUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author laoyu
 * @version 1.0
 * @description AbstractService
 * @date 2023/7/6
 */

public abstract class AbstractService {

    public static final String GET = "get";
    public static final String DELETE = "delete";

    public Object execGet(String method, Map<String, Object> queryParam) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<? extends AbstractService> clazz = this.getClass();
        Method thisMethod = clazz.getMethod(GET+ StrUtil.upperFirst(method),Map.class);
        return thisMethod.invoke(this,queryParam);
    }

    public Object execPost(String method, Map<String, Object> queryParam) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<? extends AbstractService> clazz = this.getClass();
        Method thisMethod = clazz.getMethod(method,Map.class,String.class);
        return thisMethod.invoke(this,queryParam);
    }

    public Object execDelete(Map<String, Object> queryParam) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<? extends AbstractService> clazz = this.getClass();
        Method thisMethod = clazz.getMethod(DELETE,Map.class);
        return thisMethod.invoke(this,queryParam);
    }

    public Object execDelete(String method, Map<String, Object> queryParam) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<? extends AbstractService> clazz = this.getClass();
        Method thisMethod = clazz.getMethod(DELETE+StrUtil.upperFirst(method),Map.class);
        return thisMethod.invoke(this,queryParam);
    }

}
