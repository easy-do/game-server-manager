package game.server.manager.uc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import game.server.manager.uc.entity.DictData;
import org.apache.ibatis.annotations.Mapper;

/**
* @author yuzhanfeng
* @description 针对表【dict_data(字典数据)】的数据库操作Mapper
* @createDate 2022-05-05 16:15:14
* @Entity game.server.manager.uc.entity.DictData
*/
@Mapper
public interface DictDataMapper extends BaseMapper<DictData> {

}




