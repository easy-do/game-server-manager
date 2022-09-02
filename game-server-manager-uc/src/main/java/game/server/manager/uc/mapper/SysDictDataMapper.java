package game.server.manager.uc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import game.server.manager.uc.entity.SysDictData;
import org.apache.ibatis.annotations.Mapper;

/**
* @author yuzhanfeng
* @description 针对表【sys_dict_data(字典数据表)】的数据库操作Mapper
* @createDate 2022-07-22 10:19:20
* @Entity game.server.manager.uc.entity.SysDictData
*/
@Mapper
public interface SysDictDataMapper extends BaseMapper<SysDictData> {

}




