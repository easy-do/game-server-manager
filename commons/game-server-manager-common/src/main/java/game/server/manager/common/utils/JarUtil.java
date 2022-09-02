//package game.server.manager.common.utils;
//
//import cn.hutool.core.exceptions.ExceptionUtil;
//import lombok.extern.slf4j.Slf4j;
//
//import java.io.File;
//import java.lang.reflect.InvocationTargetException;
//import java.net.URL;
//import java.net.URLClassLoader;
//
///**
// * @author yuzhanfeng
// * @Date 2022/8/12 17:53
// */
//@Slf4j
//public class JarUtil {
//
//
//    public static Class<?> loadClass(String jarPath, String className)  {
//        File file = new File(jarPath);
//        URLClassLoader loader;
//        try {
//            loader = new URLClassLoader(new URL[] { file.toURI().toURL() });
//            return loader.loadClass(className);
//        } catch (Exception e) {
//            log.error("动态加载jar包类class异常，{},{},{}", jarPath, className, ExceptionUtil.getMessage(e));
//        }
//        return null;
//    }
//
//    public static Object invokMethod(String jarPath, String className, String methodName, Object ...value){
//        Class<?> clazz = loadClass(jarPath, className);
//        try {
//            Object object = clazz.getConstructor().newInstance();
//            method = clazz.getMethod()
//        } catch (InstantiationException e) {
//            throw new RuntimeException(e);
//        } catch (IllegalAccessException e) {
//            throw new RuntimeException(e);
//        } catch (InvocationTargetException e) {
//            throw new RuntimeException(e);
//        } catch (NoSuchMethodException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//
//
//}
