package game.server.manager.server.mapper;

import game.server.manager.server.entity.ScriptEnvData;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author yuzhanfeng
 * @description 针对表【script_env_data(脚本变量)】的数据库操作Mapper
 * @createDate 2022-05-22 17:28:24
 * @Entity game.server.manager.server.entity.ScriptEnvData
 */
@Mapper
public interface ScriptEnvDataMapper extends BaseMapper<ScriptEnvData> {

}



