package plus.easydo.generate.dynamic.utils;

import plus.easydo.generate.dynamic.configuration.JdbcDynamicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Map;


/**
 * @author laoyu
 * @version 1.0
 */
@Component
public class JdbcDataSourceExecTool {

    Logger logger = LoggerFactory.getLogger(JdbcDataSourceExecTool.class);

    @Autowired
    private JdbcDynamicDataSource dynamicDataSource;


    /**
     * 执行查询语句
     *
     * @param id id
     * @param sql sql
     * @param extractData extractData
     * @param <T> t
     * @return T
     * @author laoyu
     */
    public <T> T query(String id, String sql, ResultSetExtractor<T> extractData){
        JdbcDynamicDataSource.DataSourceNode dataSource = dynamicDataSource.getDataSource(id);
        logger.info("exec dataSourceId:{}, dataSource: {}, sql:{}",id, dataSource.toString(),sql);
        return dataSource.getJdbcTemplate().query(sql,extractData);
    }

    /**
     * 更新以及修改操作
     *
     * @param id id
     * @param sql sql
     * @return int
     * @author laoyu
     */
    public int update(String id, String sql) {
        JdbcDynamicDataSource.DataSourceNode dataSource = dynamicDataSource.getDataSource(id);
        return dataSource.getJdbcTemplate().update(sql);
    }

    /**
     * 测试数据源
     *
     * @return java.lang.Boolean
     * @author laoyu
     */
    public static Boolean testDataSource(Map<String,Object> dataSourceMap){
        DataSource dataSource = JdbcDynamicDataSource.createDataSource(dataSourceMap);
        return JdbcDynamicDataSource.testDataSource(dataSource,true);
    }

    /**
     * 删除数据源
     *
     * @param dataSourceId dataSourceId
     * @return java.lang.Boolean
     * @author laoyu
     */
    public static Boolean removeDataSource(String dataSourceId){
        return JdbcDynamicDataSource.delete(dataSourceId);
    }

}
