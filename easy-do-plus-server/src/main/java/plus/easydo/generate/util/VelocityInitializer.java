package plus.easydo.generate.util;

import plus.easydo.common.exception.BizException;
import plus.easydo.generate.constant.GenConstants;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.resource.loader.DataSourceResourceLoader;

import java.util.Properties;

/**
 * VelocityEngine工厂
 *
 * @author ruoyi
 */
public class VelocityInitializer {

    private DataSourceResourceLoader dataSourceResourceLoader;

    public VelocityInitializer(DataSourceResourceLoader dataSourceResourceLoader) {
        this.dataSourceResourceLoader = dataSourceResourceLoader;
    }

    /**
     * 初始化vm方法从Classpath加载资源
     *
     * @author laoyu
     */
    public static void initVelocityClasspathResourceLoader() {
        Properties p = new Properties();
        try {
            // 加载classpath目录下的vm文件
            p.setProperty("resource.loader", "class");
            p.setProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
            // 定义字符集
            p.setProperty(Velocity.INPUT_ENCODING, GenConstants.UTF8);
            // 初始化Velocity引擎，指定配置Properties
            Velocity.init(p);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void initVelocityStringResourceLoader() {

        Properties p = new Properties();
        try {
            // 加载classpath目录下的vm文件
            p.setProperty("resource.loader", "string");
            p.setProperty("string.resource.loader.class", "org.apache.velocity.runtime.resource.loader.StringResourceLoader");
            p.setProperty("string.resource.loader.repository.class", "org.apache.velocity.runtime.resource.util.StringResourceRepositoryImpl");
            // 定义字符集
            p.setProperty(Velocity.INPUT_ENCODING, GenConstants.UTF8);
            // 初始化Velocity引擎，指定配置Properties
            Velocity.init(p);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 初始化vm方法从数据库加载
     *
     * @author laoyu
     */
    public void initVelocityDataSourceResourceLoader() {
        Properties p = new Properties();
        try {
            // 加载数据库中的模板信息
            p.setProperty("resource.loader", "ds");
            p.setProperty("ds.resource.loader.resource.table", "template_management");
            p.setProperty("ds.resource.loader.resource.keycolumn", "id");
            p.setProperty("ds.resource.loader.resource.templatecolumn", "template_code");
            p.setProperty("ds.resource.loader.resource.timestampcolumn", "update_time");
            p.setProperty("ds.resource.loader.cache", "true");
            // 定义字符集
            p.setProperty(Velocity.INPUT_ENCODING, GenConstants.UTF8);
            //将数据源放进去
            Velocity.setProperty("ds.resource.loader.instance", dataSourceResourceLoader);
            // 初始化Velocity引擎，指定配置Properties
            Velocity.init(p);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException("500","初始化initVelocityDataSourceResourceLoader异常");
        }
    }

}
