package game.server.manager.generate.service;


import game.server.manager.generate.entity.GenTable;
import game.server.manager.generate.entity.GenTableColumn;
import game.server.manager.generate.entity.GenTableIndex;

import java.util.List;

/**
 * @author laoyu
 * @version 1.0
 */
public interface DataSourceDbService {


    /**
     * 查询据库列表
     *
     * @param genTable 业务信息
     * @return 数据库表集合
     */
    List<GenTable> selectDbTableList(GenTable genTable);

    /**
     * 查询据库表总数
     *
     * @param genTable 业务信息
     * @return 数据库表集合
     */
    long countDbTableList(GenTable genTable);

    /**
     * 数据库列表查询语句的动态构建
     *
     * @param genTable genTable
     * @param sql sql
     * @param isPage isPage
     * @return java.lang.String
     * @author laoyu
     */
    String dbListDynamicSQLBuild(GenTable genTable, String sql, boolean isPage);

    /**
     * 查询据库列表
     *
     * @param dataSourceId 数据源
     * @param tableNames   表名称组
     * @return 数据库表集合
     */
    List<GenTable> selectDbTableListByNames(String dataSourceId, String[] tableNames);


    /**
     * 根据数据源id和表名动态获取表的所有字段列信息
     *
     * @param dataSourceId dataSourceId
     * @param tableName    tableName
     * @return java.util.List
     * @author laoyu
     */
    List<GenTableColumn> selectDbTableColumnsByName(String dataSourceId, String tableName);

    /**
     * 根据表名称查询表所有列信息
     *
     * @param tableName tableName
     * @return java.util.List
     * @author laoyu
     */
    List<GenTableColumn> selectDbTableColumnsByName(String tableName);


    /**
     * 根据数据源id和表名动态获取表的所有索引信息
     *
     * @param dataSourceId dataSourceId
     * @param tableName    tableName
     * @return java.util.List
     * @author laoyu
     */
    List<GenTableIndex> selectIndexByTableName(String dataSourceId, String tableName);
}
