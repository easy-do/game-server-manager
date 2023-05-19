package game.server.manager.generate.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import game.server.manager.generate.dynamic.core.DataSource;
import org.apache.ibatis.annotations.Mapper;


/**
 * 数据源管理Mapper接口
 * 
 * @author gebilaoyu
 */
@Mapper
public interface DataSourceManagerMapper extends BaseMapper<DataSource> {

}
