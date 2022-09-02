package game.server.manager.server.mapper;

import game.server.manager.server.entity.AppEnvInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author yuzhanfeng
 * @description 针对表【app_env_info(应用变量)】的数据库操作Mapper
 * @createDate 2022-05-22 17:28:24
 * @Entity game.server.manager.server.entity.AppEnvInfo
 */
@Mapper
public interface AppEnvInfoMapper extends BaseMapper<AppEnvInfo> {

}



