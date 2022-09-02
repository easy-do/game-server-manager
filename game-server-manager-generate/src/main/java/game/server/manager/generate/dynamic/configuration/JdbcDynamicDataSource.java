package game.server.manager.generate.dynamic.configuration;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.text.CharSequenceUtil;
import game.server.manager.common.exception.BizException;
import game.server.manager.generate.dynamic.core.vo.DataSourceVo;
import game.server.manager.generate.dynamic.utils.JdbcDataSourceExecTool;
import game.server.manager.generate.service.DataSourceManagerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;
import org.springframework.boot.context.properties.source.ConfigurationPropertyNameAliases;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;
import org.springframework.boot.context.properties.source.MapConfigurationPropertySource;
import org.springframework.boot.jdbc.DatabaseDriver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import javax.sql.DataSource;
import java.io.Closeable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 动态数据源
 * @author laoyu
 * @version 1.0
 */
@Component
public class JdbcDynamicDataSource {

    public static final String DEFAULT = "default";

    private static final Logger logger = LoggerFactory.getLogger(JdbcDynamicDataSource.class);

    private static final Map<String, DataSourceNode> DATA_SOURCE_MAP = new HashMap<>();

    private static final ClassLoader CLASSLOADER = JdbcDataSourceExecTool.class.getClassLoader();

    private static final String DATASOURCE_TYPE_NOT_SET = "请设置数据源类型";

    /** copy from DataSourceBuilder */
    private static final String[] DATA_SOURCE_TYPE_NAMES = new String[]{
            "com.zaxxer.hikari.HikariDataSource",
            "org.apache.tomcat.jdbc.pool.DataSource",
            "org.apache.commons.dbcp2.BasicDataSource"};

    @Autowired(required = false)
    private DataSourceManagerService dataSourceManagerService;


    /**
     * 注册数据源（可以运行时注册）
     *
     * @param id             数据源ID
     * @param datasourceName 数据源名称
     * @param dataSource dataSource
     * @return plus.easydo.jdbc.configuration.JdbcDynamicDataSource.DataSourceNode
     * @author laoyu
     */
    public static DataSourceNode put(String id, String datasourceName, DataSource dataSource) {
        logger.info("注册数据源：{}", CharSequenceUtil.isNotBlank(id) ? id + datasourceName : DEFAULT);
        DataSourceNode node = new DataSourceNode(id, datasourceName, dataSource);
        DATA_SOURCE_MAP.put(id,node);
        return node;
    }

    /**
     * 获取全部数据源
     *
     * @return java.util.List
     * @author laoyu
     */
    public static List<String> dataSources() {
        return new ArrayList<>(DATA_SOURCE_MAP.keySet());
    }

    public boolean isEmpty() {
        return DATA_SOURCE_MAP.isEmpty();
    }

    /**
     * 获取全部数据源
     *
     * @return java.util.Collection
     * @author laoyu
     */
    public static Collection<DataSourceNode> datasourceNodes() {
        return DATA_SOURCE_MAP.values();
    }

    /**
     * 删除数据源
     *
     * @param id 数据源id
     * @return boolean
     * @author laoyu
     */
    public static boolean delete(String id) {
        boolean result = false;
        // 检查参数是否合法
        if (id != null && !id.isEmpty()) {
            DataSourceNode node = DATA_SOURCE_MAP.remove(id);
            result = node != null;
            if (result) {
                node.close();
            }
        }
        logger.info("删除数据源：{}:{}", id, result ? "成功" : "失败");
        return result;
    }


    /**
     * 获取数据源
     *
     * @param id 数据源id
     * @return plus.easydo.jdbc.configuration.JdbcDynamicDataSource.DataSourceNode
     * @author laoyu
     */
    public DataSourceNode getDataSource(String id) {
        if (id == null) {
            id = DEFAULT;
        }
        DataSourceNode dataSourceNode = DATA_SOURCE_MAP.get(id);
        if (!id.equals(DEFAULT) && Objects.isNull(dataSourceNode)){
            if(Objects.isNull(dataSourceManagerService)){
                throw new BizException("500","You need to implement : "+ DataSourceManagerService.class.getName());
            }
            DataSourceVo dataSourceManager = dataSourceManagerService.selectById(id);
            if(Objects.nonNull(dataSourceManager)){
                logger.info("缓存中未找到数据源,尝试注册：{}", dataSourceManager.getSourceName());
                DataSource dataSource = createDataSource(BeanUtil.beanToMap(dataSourceManager));
                testDataSource(dataSource, false);
                dataSourceNode = put(dataSourceManager.getId(),dataSourceManager.getSourceName(),dataSource);
            }
        }
        if(Objects.isNull(dataSourceNode)){
            throw new BizException("500","找不到数据源:"+ id);
        }
        return dataSourceNode;
    }

    /**
     * 设置默认数据源
     *
     * @param dataSource dataSource
     * @author laoyu
     */
    public void setDefault(DataSource dataSource) {
        put(DEFAULT, DEFAULT, dataSource);
    }

    /**
     * 获得默认数据源
     *
     * @return plus.easydo.jdbc.config.DynamicDataSource.DataSourceNode
     * @author laoyu
     */
    public DataSourceNode getDefault(){
        return DATA_SOURCE_MAP.get(DEFAULT);
    }

    /**
     * 创建数据源
     *
     * @param dataSourceInfo dataSourceInfo
     * @return javax.sql.DataSource
     * @author laoyu
     */
    public static DataSource createDataSource(Map<String,Object> dataSourceInfo) {
        /** Class<? extends DataSource> dataSourceType = getDataSourceType((String) dataSourceInfo.get("source_type")); */
        Class<? extends DataSource> dataSourceType = getDataSourceType("com.zaxxer.hikari.HikariDataSource");
        String url = (String) dataSourceInfo.get("url");
        String driverClass = DatabaseDriver.fromJdbcUrl(url).getDriverClassName();
        dataSourceInfo.put("driverClassName", driverClass);
        DataSource dataSource = BeanUtils.instantiateClass(dataSourceType);
        ConfigurationPropertySource source = new MapConfigurationPropertySource(dataSourceInfo);
        ConfigurationPropertyNameAliases aliases = new ConfigurationPropertyNameAliases();
        aliases.addAliases("url", "jdbc-url");
        aliases.addAliases("username", "user");
        Binder binder = new Binder(source.withAliases(aliases));
        binder.bind(ConfigurationPropertyName.EMPTY, Bindable.ofInstance(dataSource));
        return dataSource;
    }

    /**
     * 测试数据源
     *
     * @param dataSource dataSource
     * @param isClose 测试完成后是否关闭连接
     * @return java.lang.Boolean
     * @author laoyu
     */
    public static Boolean testDataSource(DataSource dataSource,Boolean isClose){
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            throw new BizException("500","数据源测试失败："+ e.getMessage());
        }finally {
            if(Objects.nonNull(connection) && isClose){
                try {
                    connection.close();
                } catch (SQLException e) {
                    logger.error("测试数据源完成后关闭连接失败！");
                }
            }
        }
        return true;
    }

    /**
     * 获得数据源类型
     *
     * @param datasourceType datasourceType
     * @return java.lang.Class<? extends javax.sql.DataSource>
     * @author laoyu
     */
    private static Class<? extends DataSource> getDataSourceType(String datasourceType) {
        if (CharSequenceUtil.isNotBlank(datasourceType)) {
            try {
                return (Class<? extends DataSource>) ClassUtils.forName(datasourceType, CLASSLOADER);
            } catch (Exception e) {
                throw new BizException("500",datasourceType + "not found");
            }
        }
        for (String name : DATA_SOURCE_TYPE_NAMES) {
            try {
                return (Class<? extends DataSource>) ClassUtils.forName(name, CLASSLOADER);
            } catch (Exception ignored) {
                // ignored
            }
        }
        throw new BizException("500",DATASOURCE_TYPE_NOT_SET);
    }



    public static class DataSourceNode {

        private static Logger logger = LoggerFactory.getLogger(DataSourceNode.class);

        private final String id;

        private final String name;

        /** 事务管理器 */
        private final DataSourceTransactionManager dataSourceTransactionManager;

        private final JdbcTemplate jdbcTemplate;

        private final DataSource dataSource;

        DataSourceNode(String id, String name, DataSource dataSource) {
            this.id = id;
            this.name = name;
            this.dataSource = dataSource;
            this.dataSourceTransactionManager = new DataSourceTransactionManager(this.dataSource);
            this.jdbcTemplate = new JdbcTemplate(dataSource);
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public JdbcTemplate getJdbcTemplate() {
            return this.jdbcTemplate;
        }

        public DataSourceTransactionManager getDataSourceTransactionManager() {
            return dataSourceTransactionManager;
        }


        public DataSource getDataSource() {
            return dataSource;
        }

        public void close() {
            closeDataSource(this.dataSource);
        }

        public static void closeDataSource(DataSource dataSource) {
            if (dataSource != null) {
                if (dataSource instanceof Closeable) {
                    try {
                        ((Closeable) dataSource).close();
                    } catch (Exception e) {
                        logger.warn("Close DataSource error", e);
                    }
                } else {
                    logger.warn("DataSource can not close");
                }
            }
        }
    }


}
