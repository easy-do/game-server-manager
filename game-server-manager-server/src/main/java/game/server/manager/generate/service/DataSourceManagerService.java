package game.server.manager.generate.service;



import com.baomidou.mybatisplus.core.metadata.IPage;
import game.server.manager.common.result.R;
import game.server.manager.generate.dynamic.core.dto.DataSourceDto;
import game.server.manager.generate.dynamic.core.qo.DataSourceQo;
import game.server.manager.generate.dynamic.core.vo.DataSourceVo;

import java.util.List;

/**
 * 数据源管理Service接口
 * 
 * @author gebilaoyu
 */
public interface DataSourceManagerService {

    /**
     * 查询数据源管理
     * 
     * @param id 数据源管理ID
     * @return 数据源管理
     */
    DataSourceVo selectById(String id);

    /**
     * 分页条件查询数据源管理列表
     * 
     * @param qo 条件封装
     * @return 数据源管理集合
     */
    IPage<DataSourceVo> page(DataSourceQo qo);

    /**
     * 获取所有数据源管理列表
     *
     * @param qo 条件封装
     * @return 数据源管理集合
     */
    List<DataSourceVo> list(DataSourceQo qo);


    /**
     * 添加或保存
     *
     * @param dto dto
     * @return int
     * @author laoyu
     * @date 2022/4/14
     */
    boolean saveOrUpdate(DataSourceDto dto);

    /**
     * 批量删除数据源管理
     * 
     * @param ids 需要删除的数据源管理ID
     * @return 结果
     */
    Boolean deleteByIds(String[] ids);

    /**
     * 删除数据源管理信息
     * 
     * @param id 数据源管理ID
     * @return 结果
     */
    Boolean deleteById(Long id);

    /**
     * 测试数据源
     *
     * @param dto dto
     * @return java.lang.Boolean
     * @author laoyu
     */
    R<Object> test(DataSourceDto dto);
}
