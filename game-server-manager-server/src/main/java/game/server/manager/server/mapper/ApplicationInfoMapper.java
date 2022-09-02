package game.server.manager.server.mapper;

import game.server.manager.server.entity.ApplicationInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author yuzhanfeng
* @description 针对表【application_info(应用信息)】的数据库操作Mapper
* @createDate 2022-05-20 10:29:54
* @Entity game.server.manager.server.entity.ApplicationInfo
*/
@Mapper
public interface ApplicationInfoMapper extends BaseMapper<ApplicationInfo> {

}




