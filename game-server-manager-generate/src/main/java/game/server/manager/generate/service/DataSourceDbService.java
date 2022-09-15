package game.server.manager.generate.service;


import game.server.manager.generate.entity.GenTable;
import game.server.manager.generate.entity.GenTableColumn;
import game.server.manager.generate.entity.GenTableIndex;
import game.server.manager.generate.qo.DbListQo;
import game.server.manager.generate.vo.DbListVo;

import java.util.List;

/**
 * @author laoyu
 * @version 1.0
 */
public interface DataSourceDbService {


    /**
     * 查询据库列表
     *
     * @param dbListQo dbListQo
     * @return 数据库表集合
     */
    List<DbListVo> selectDbTableList(DbListQo dbListQo);

    /**
     * 查询据库表总数
     *
     * @param dbListQo dbListQo
     * @return 数据库表集合
     */
    long countDbTableList(DbListQo dbListQo);

    /**
     * 数据库列表查询语句的动态构建
     *
     * @param dbListQo dbListQo
     * @param sql sql
     * @param isPage isPage
     * @return java.lang.String
     * @author laoyu
     */
    String dbListDynamicSqlBuild(DbListQo dbListQo, String sql, boolean isPage);

    /**
     * 查询据库列表
     *
     * @param dataSourceId 数据源
     * @param tableNames   表名称组
     * @return 数据库表集合
     */
    List<DbListVo> selectDbTableListByNames(String dataSourceId, String[] tableNames);


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
