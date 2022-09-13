package game.server.manager.generate.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import game.server.manager.generate.dynamic.core.DataSource;
import game.server.manager.generate.dynamic.core.qo.DataSourceQo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 数据源管理Mapper接口
 * 
 * @author gebilaoyu
 */
@Mapper
public interface DataSourceManagerMapper extends BaseMapper<DataSource> {
    /**
     * 查询数据源管理
     *
     * @param id 数据源管理ID
     * @return 数据源管理
     */
    DataSource selectDataSourceManagerById(String id);

    /**
     * 查询所有数据源列表
     *
     * @param qo 查询条件封装
     * @return 数据源管理集合
     */
    List<DataSource> selectDataSourceManagerList(@Param("qo") DataSourceQo qo);

}
