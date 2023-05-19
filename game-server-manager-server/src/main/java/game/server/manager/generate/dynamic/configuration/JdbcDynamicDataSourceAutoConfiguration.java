package game.server.manager.generate.dynamic.configuration;

import game.server.manager.generate.dynamic.utils.JdbcDataSourceExecTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Objects;

/**
 * 自动注册
 * @author laoyu
 * @version 1.0
 */
@Configuration
public class JdbcDynamicDataSourceAutoConfiguration {

    Logger logger = LoggerFactory.getLogger(JdbcDynamicDataSourceAutoConfiguration.class);


    /**
     * 初始化动态数据源，设置默认数据源
     *
     * @param dataSource dataSource
     * @return plus.easydo.jdbc.config.DynamicDataSource
     * @author laoyu
     */
    @Bean
    public JdbcDynamicDataSource dynamicDataSource(@Autowired(required = false) DataSource dataSource){
        if(Objects.isNull(dataSource)){
            logger.warn("没有找到默认数据源{}",dataSource);
        }
        JdbcDynamicDataSource dynamicDataSource = new JdbcDynamicDataSource();
        dynamicDataSource.setDefault(dataSource);
        return dynamicDataSource;
    }

    /**
     * 数据源执行工具类
     *
     * @return plus.easydo.jdbc.utils.DataSourceExecTool
     * @author laoyu
     */
    @Bean
    @ConditionalOnBean(JdbcDynamicDataSource.class)
    public JdbcDataSourceExecTool dataSourceExecTool(){
        return new JdbcDataSourceExecTool();
    }


}
