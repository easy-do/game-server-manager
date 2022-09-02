package game.server.manager.generate.service.impl;

import cn.hutool.core.text.CharSequenceUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import game.server.manager.common.exception.BizException;
import game.server.manager.generate.constant.GenConstants;
import game.server.manager.generate.entity.GenTable;
import game.server.manager.generate.entity.GenTableColumn;
import game.server.manager.generate.mapper.GenTableColumnMapper;
import game.server.manager.generate.mapper.GenTableMapper;
import game.server.manager.generate.service.DataSourceDbService;
import game.server.manager.generate.service.GenTableService;
import game.server.manager.generate.util.GenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 业务 服务层实现
 *
 * @author laoyu
 */
@Service
public class GenTableServiceImpl extends ServiceImpl<GenTableMapper, GenTable> implements GenTableService {

    private static final Logger log = LoggerFactory.getLogger(GenTableServiceImpl.class);

    @Autowired
    private DataSourceDbService dataSourceDbService;

    @Autowired
    private GenTableColumnMapper genTableColumnMapper;

    /**
     * 分页查询
     *
     * @param genTable genTable
     * @return com.baomidou.mybatisplus.core.metadata.IPage
     * @author laoyu
     */
    @Override
    public IPage<GenTable> pageGenTableList(GenTable genTable) {
        IPage<GenTable> page = new Page<>(genTable.getPageCurrent(), genTable.getPageSize());
        Wrapper<GenTable> wrapper = buildQueryWrapper(genTable);
        return baseMapper.selectPage(page, wrapper);
    }

    @Override
    public GenTable selectGenTableByName(String subTableName) {
        LambdaQueryWrapper<GenTable> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(GenTable::getTableName, subTableName);
        GenTable genTable = baseMapper.selectOne(wrapper);
        LambdaQueryWrapper<GenTableColumn> columnWrapper = Wrappers.lambdaQuery();
        columnWrapper.eq(GenTableColumn::getTableId, genTable.getTableId());
        columnWrapper.orderByDesc(GenTableColumn::getSort);
        List<GenTableColumn> genTableColumns = genTableColumnMapper.selectList(columnWrapper);
        genTable.setColumns(genTableColumns);
        setTableFromOptions(genTable);
       return genTable;
    }

    /**
     * 查询业务列表
     *
     * @param genTable 业务信息
     * @return 业务集合
     */
    @Override
    public List<GenTable> selectGenTableList(GenTable genTable) {
        Wrapper<GenTable> wrapper = buildQueryWrapper(genTable);
        return baseMapper.selectList(wrapper);
    }

    /**
     * 构建查询Wrapper
     *
     * @param genTable genTable
     * @return 查询wrapper
     * @author laoyu
     */
    private Wrapper<GenTable> buildQueryWrapper(GenTable genTable) {
        LambdaQueryWrapper<GenTable> wrapper = Wrappers.lambdaQuery();
        if (CharSequenceUtil.isNotBlank(genTable.getTableName())) {
            wrapper.like(GenTable::getTableName, genTable.getTableName());
        }
        if (CharSequenceUtil.isNotBlank(genTable.getTableComment())) {
            wrapper.like(GenTable::getTableComment, genTable.getTableComment());
        }
        return wrapper;
    }


    /**
     * 查询所有表信息
     *
     * @return 表信息集合
     */
    @Override
    public List<GenTable> selectGenTableAll() {
        List<GenTable> tableList = baseMapper.selectList(null);
        return tableList.stream().peek(table -> {
            LambdaQueryWrapper<GenTableColumn> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(GenTableColumn::getTableId, table.getTableId());
            List<GenTableColumn> genTableColumns = genTableColumnMapper.selectList(wrapper);
            table.setColumns(genTableColumns);
        }).collect(Collectors.toList());
    }

    /**
     * 查询业务信息
     *
     * @param id 业务ID
     * @return 业务信息
     */
    @Override
    public GenTable selectGenTableById(Long id) {
        GenTable genTable = selectGenTableByIdJoin(id);
        setTableFromOptions(genTable);
        return genTable;
    }

    /**
     * 连表查询
     *
     * @param id id
     * @return plus.easydo.starter.plugins.gen.entity.GenTable
     * @author laoyu
     */
    public GenTable selectGenTableByIdJoin(Long id) {
        GenTable genTable = baseMapper.selectById(id);
        LambdaQueryWrapper<GenTableColumn> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(GenTableColumn::getTableId, id);
        wrapper.orderByDesc(GenTableColumn::getSort);
        List<GenTableColumn> genTableColumns = genTableColumnMapper.selectList(wrapper);
        genTable.setColumns(genTableColumns);
        setTableFromOptions(genTable);
        return genTable;
    }

    /**
     * 设置代码生成其他选项值
     *
     * @param genTable 设置后的生成对象
     */
    public void setTableFromOptions(GenTable genTable) {
        JSONObject paramsObj = JSONObject.parseObject(genTable.getOptions());
        if (Objects.nonNull(paramsObj)) {
            String treeCode = paramsObj.getString(GenConstants.TREE_CODE);
            String treeParentCode = paramsObj.getString(GenConstants.TREE_PARENT_CODE);
            String treeName = paramsObj.getString(GenConstants.TREE_NAME);
            String parentMenuId = paramsObj.getString(GenConstants.PARENT_MENU_ID);
            String parentMenuName = paramsObj.getString(GenConstants.PARENT_MENU_NAME);
            genTable.setTreeCode(treeCode);
            genTable.setTreeParentCode(treeParentCode);
            genTable.setTreeName(treeName);
            genTable.setParentMenuId(parentMenuId);
            genTable.setParentMenuName(parentMenuName);
        }
    }

    /**
     * 修改业务
     *
     * @param genTable 业务信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateGenTable(GenTable genTable) {
        String options = JSON.toJSONString(genTable.getParams());
        genTable.setOptions(options);
        int row = baseMapper.updateById(genTable);
        if (row > 0) {
            for (GenTableColumn cenTableColumn : genTable.getColumns()) {
                genTableColumnMapper.updateById(cenTableColumn);
            }
        }
    }

    /**
     * 删除业务对象
     *
     * @param tableIds 需要删除的数据ID
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void deleteGenTableByIds(Long[] tableIds) {
        baseMapper.deleteBatchIds(Arrays.asList(tableIds));
        LambdaQueryWrapper<GenTableColumn> wrapper = Wrappers.lambdaQuery();
        wrapper.in(GenTableColumn::getTableId, tableIds);
        genTableColumnMapper.delete(wrapper);
    }

    /**
     * 导入表结构
     *
     * @param dataSourceId 数据源
     * @param tableList    导入表列表
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void importGenTable(String dataSourceId, List<GenTable> tableList) {
        String operationName = "easy-do";
        try {
            for (GenTable table : tableList) {
                String tableName = table.getTableName();
                GenUtils.initTable(table, operationName);
                table.setDataSourceId(dataSourceId);
                int row = baseMapper.insert(table);
                if (row > 0) {
                    // 保存列信息
                    List<GenTableColumn> genTableColumns = dataSourceDbService.selectDbTableColumnsByName(dataSourceId, tableName);
                    for (GenTableColumn column : genTableColumns) {
                        GenUtils.initColumnField(column, table);
                        genTableColumnMapper.insert(column);
                    }
                }
            }
        } catch (Exception e) {
            throw new BizException("500","导入失败：" + e.getMessage());
        }
    }

    /**
     * 同步数据库
     *
     * @param tableName 表名称
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void syncDb(String tableName) {
        GenTable table = selectGenTableByName(tableName);
        List<GenTableColumn> tableColumns = table.getColumns();
        List<String> tableColumnNames = tableColumns.stream().map(GenTableColumn::getColumnName).collect(Collectors.toList());

        List<GenTableColumn> dbTableColumns = dataSourceDbService.selectDbTableColumnsByName(table.getDataSourceId(),tableName);

        if (Objects.isNull(dbTableColumns)) {
            throw new BizException("500","同步数据失败，原表结构不存在");
        }
        List<String> dbTableColumnNames = dbTableColumns.stream().map(GenTableColumn::getColumnName).collect(Collectors.toList());

        dbTableColumns.forEach(column -> {
            if (!tableColumnNames.contains(column.getColumnName())) {
                GenUtils.initColumnField(column, table);
                genTableColumnMapper.insert(column);
            }
        });

        List<GenTableColumn> delColumns = tableColumns.stream().filter(column -> !dbTableColumnNames.contains(column.getColumnName())).collect(Collectors.toList());
        if (!delColumns.isEmpty()) {
            List<Long> ids = delColumns.stream().map(GenTableColumn::getColumnId).collect(Collectors.toList());
            genTableColumnMapper.deleteBatchIds(ids);
        }
    }


    /**
     * 修改保存参数校验
     *
     * @param genTable 业务信息
     */
    @Override
    public void validateEdit(GenTable genTable) {
        if (GenConstants.TPL_TREE.equals(genTable.getTplCategory())) {
            String options = JSON.toJSONString(genTable.getParams());
            JSONObject paramsObj = JSONObject.parseObject(options);
            if (CharSequenceUtil.isEmpty(paramsObj.getString(GenConstants.TREE_CODE))) {
                throw new BizException("500","树编码字段不能为空");
            } else if (CharSequenceUtil.isEmpty(paramsObj.getString(GenConstants.TREE_PARENT_CODE))) {
                throw new BizException("500","树父编码字段不能为空");
            } else if (CharSequenceUtil.isEmpty(paramsObj.getString(GenConstants.TREE_NAME))) {
                throw new BizException("500","树名称字段不能为空");
            } else if (GenConstants.TPL_SUB.equals(genTable.getTplCategory())) {
                if (CharSequenceUtil.isEmpty(genTable.getSubTableName())) {
                    throw new BizException("500","关联子表的表名不能为空");
                } else if (CharSequenceUtil.isEmpty(genTable.getSubTableFkName())) {
                    throw new BizException("500","子表关联的外键名不能为空");
                }
            }
        }
    }


}
