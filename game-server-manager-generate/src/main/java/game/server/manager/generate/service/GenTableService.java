package game.server.manager.generate.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import game.server.manager.generate.entity.GenTable;

import java.util.List;

/**
 * 业务 服务层
 *
 * @author laoyu
 */
public interface GenTableService {
    /**
     * 查询业务列表
     *
     * @param genTable 业务信息
     * @return 业务集合
     */
    List<GenTable> selectGenTableList(GenTable genTable);


    /**
     * 查询所有表信息
     *
     * @return 表信息集合
     */
    List<GenTable> selectGenTableAll();

    /**
     * 查询业务信息
     *
     * @param id 业务ID
     * @return 业务信息
     */
    GenTable selectGenTableById(Long id);

    /**
     * 修改业务
     *
     * @param genTable 业务信息
     */
    void updateGenTable(GenTable genTable);

    /**
     * 删除业务信息
     *
     * @param tableIds 需要删除的表数据ID
     */
    void deleteGenTableByIds(Long[] tableIds);

    /**
     * 导入表结构
     *
     * @param dataSourceId 数据源
     * @param tableList    导入表列表
     */
    void importGenTable(String dataSourceId, List<GenTable> tableList);


    /**
     * 同步数据库
     *
     * @param tableName 表名称
     */
    void syncDb(String tableName);


    /**
     * 修改保存参数校验
     *
     * @param genTable 业务信息
     */
    void validateEdit(GenTable genTable);


    /**
     * 非分页查询业务列表
     *
     * @param genTable genTable
     * @return com.baomidou.mybatisplus.core.metadata.IPage
     * @author laoyu
     */
    IPage<GenTable> pageGenTableList(GenTable genTable);

    /**
     * 根据表名查询
     *
     * @param subTableName subTableName
     * @return plus.easydo.starter.plugins.gen.entity.GenTable
     * @author laoyu
     */
    GenTable selectGenTableByName(String subTableName);
}
