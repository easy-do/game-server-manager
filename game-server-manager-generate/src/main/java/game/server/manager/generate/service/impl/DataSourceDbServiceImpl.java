package game.server.manager.generate.service.impl;

import cn.hutool.core.text.CharSequenceUtil;
import game.server.manager.generate.dynamic.utils.JdbcDataSourceExecTool;
import game.server.manager.generate.entity.GenTable;
import game.server.manager.generate.entity.GenTableColumn;
import game.server.manager.generate.entity.GenTableIndex;
import game.server.manager.generate.rowmapper.GenTableColumnRowMapper;
import game.server.manager.generate.rowmapper.GenTableIndexRowMapper;
import game.server.manager.generate.rowmapper.GenTableRowMapper;
import game.server.manager.generate.service.DataSourceDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author laoyu
 * @version 1.0
 */
@Service
public class DataSourceDbServiceImpl implements DataSourceDbService {

    private static final RowMapperResultSetExtractor<GenTable> genTableSetExtractor = new RowMapperResultSetExtractor<>(new GenTableRowMapper());
    private static final RowMapperResultSetExtractor<GenTableColumn> genTableColumnSetExtractor = new RowMapperResultSetExtractor<>(new GenTableColumnRowMapper());
    private static final RowMapperResultSetExtractor<GenTableIndex> genTableIndexSetExtractor = new RowMapperResultSetExtractor<>(new GenTableIndexRowMapper());
    @Autowired
    private JdbcDataSourceExecTool jdbcDataSourceExecTool;

    /**
     * 查询据库列表
     *
     * @param genTable 业务信息
     * @return 数据库表集合
     */
    @Override
    public List<GenTable> selectDbTableList(GenTable genTable) {
        String sql = "select table_name, table_comment, create_time, update_time from information_schema.tables where table_schema = (select database())  AND table_name NOT LIKE 'gen_%'";
        return jdbcDataSourceExecTool.query(genTable.getDataSourceId(), dbListDynamicSQLBuild(genTable, sql, true), genTableSetExtractor);
    }

    /**
     * 查询据库表总数
     *
     * @param genTable 业务信息
     * @return 数据库表集合
     */
    @Override
    public long countDbTableList(GenTable genTable) {
        String countSql = "select count(1) AS count from information_schema.tables where table_schema = (select database()) AND table_name NOT LIKE 'gen_%'";
        return jdbcDataSourceExecTool.query(genTable.getDataSourceId(), dbListDynamicSQLBuild(genTable, countSql, false), rs -> {
            while (rs.next()) {
                return rs.getLong("count");
            }
            return 0L;
        });
    }

    /**
     * 数据库列表查询语句的动态构建
     *
     * @param genTable genTable
     * @param sql      sql
     * @return SQL
     * @author laoyu
     */
    @Override
    public String dbListDynamicSQLBuild(GenTable genTable, String sql, boolean isPage) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(sql);
        if (CharSequenceUtil.isNotBlank(genTable.getTableName())) {
            stringBuilder.append(" AND lower(table_name) like lower(concat('%', ").append("'").append(genTable.getTableName()).append("'").append(", '%')) ");
        }
        if (CharSequenceUtil.isNotBlank(genTable.getTableComment())) {
            stringBuilder.append(" AND lower(table_comment) like lower(concat('%', ").append("'").append(genTable.getTableComment()).append("'").append(", '%')) ");
        }
        if (CharSequenceUtil.isNotBlank(genTable.getTableName())) {
            stringBuilder.append("AND lower(table_name) like lower(concat('%', ").append("'").append(genTable.getTableName()).append("'").append(", '%'))");
        }
        if (isPage) {
            Long pageNum = genTable.getPageCurrent();
            Long pageSize = genTable.getPageSize();
            if (pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0) {
                stringBuilder.append(" LIMIT " + (pageNum - 1) + "," + pageSize);
            } else {
                stringBuilder.append(" LIMIT " + 0 + "," + 10);
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 查询据库列表
     *
     * @param dataSourceId 数据源
     * @param tableNames   表名称组
     * @return 数据库表集合
     */
    @Override
    public List<GenTable> selectDbTableListByNames(String dataSourceId, String[] tableNames) {
        StringBuilder startBuilder = new StringBuilder();
        StringBuilder endBuilder = new StringBuilder();
        String sql = "select table_name, table_comment, create_time, update_time from information_schema.tables where table_name NOT LIKE 'gen_%' and table_schema = (select database()) ";
        startBuilder.append(sql);
        endBuilder.append(" and table_name in (");
        for (String tableName : tableNames) {
            endBuilder.append("'").append(tableName).append("'").append(",");
        }
        String endSql = endBuilder.substring(0, endBuilder.length() - 1);
        startBuilder.append(endSql).append(")");
        return jdbcDataSourceExecTool.query(dataSourceId, startBuilder.toString(), genTableSetExtractor);
    }


    /**
     * 根据数据源id和表名动态获取表的所有字段列信息
     *
     * @param dataSourceId dataSourceId
     * @param tableName    tableName
     * @return java.util.List
     * @author laoyu
     */
    @Override
    public List<GenTableColumn> selectDbTableColumnsByName(String dataSourceId, String tableName) {
        String sql = "select column_name, (case when (is_nullable = 'no' && column_key != 'PRI') then '1' else null end) as is_required, (case when column_key = 'PRI' then '1' else '0' end) as is_pk, ordinal_position as sort, column_comment, (case when extra = 'auto_increment' then '1' else '0' end) as is_increment, column_type, column_default" +
                " from information_schema.columns where table_schema = (select database()) and table_name = '" + tableName + "' order by ordinal_position";
        return jdbcDataSourceExecTool.query(dataSourceId, sql, genTableColumnSetExtractor);
    }

    /**
     * 根据表名称查询表所有列信息
     *
     * @param tableName tableName
     * @return java.util.List
     * @author laoyu
     */
    @Override
    public List<GenTableColumn> selectDbTableColumnsByName(String tableName) {
        String sql = "select column_name, (case when (is_nullable = 'no' && column_key != 'PRI') then '1' else null end) as is_required, (case when column_key = 'PRI' then '1' else '0' end) as is_pk, ordinal_position as sort, column_comment, (case when extra = 'auto_increment' then '1' else '0' end) as is_increment, column_type, column_default" +
                " from information_schema.columns where table_schema = (select database()) and table_name = '" + tableName + "' order by ordinal_position";
        return jdbcDataSourceExecTool.query("default", sql, genTableColumnSetExtractor);
    }


    /**
     * 根据数据源id和表名动态获取表的所有索引信息
     *
     * @param dataSourceId dataSourceId
     * @param tableName    tableName
     * @return java.util.List
     * @author laoyu
     */
    @Override
    public List<GenTableIndex> selectIndexByTableName(String dataSourceId, String tableName) {
        String sql = "SHOW INDEX FROM " + tableName;
        return jdbcDataSourceExecTool.query(dataSourceId, sql, genTableIndexSetExtractor);
    }

}
