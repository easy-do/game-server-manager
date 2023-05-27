package plus.easydo.generate.configure;

import plus.easydo.generate.dynamic.configuration.JdbcDynamicDataSourceAutoConfiguration;
import plus.easydo.generate.util.VelocityInitializer;
import org.apache.velocity.runtime.resource.loader.DataSourceResourceLoader;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author laoyu
 * @version 1.0
 */
@Configuration
@EnableConfigurationProperties(GenerateConfig.class)
@ConditionalOnBean(JdbcDynamicDataSourceAutoConfiguration.class)
public class GenerateAutoConfigure {



    /**
     * 实例化 Velocity加载类
     * 将数据源注入到Velocity的DataSourceResourceLoader以支持数据源读取模板
     *
     * @param dataSource dataSource
     * @return plus.easydo.starter.plugins.gen.util.VelocityInitializer
     * @author laoyu
     */
    @Bean
    public VelocityInitializer velocityInitializer(DataSource dataSource) {
        DataSourceResourceLoader dataSourceResourceLoader = new DataSourceResourceLoader();
        dataSourceResourceLoader.setDataSource(dataSource);
        return new VelocityInitializer(dataSourceResourceLoader);
    }




}
