package game.server.manager.server.mapstruct;

import game.server.manager.common.vo.ScriptEnvDataVo;
import game.server.manager.mapstruct.BaseMapstruct;
import game.server.manager.server.dto.AppEnvInfoDto;
import game.server.manager.server.entity.ScriptEnvData;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


/**
 * @author yuzhanfeng
 */
@Mapper
public interface AppEnvInfoMapstruct extends BaseMapstruct<ScriptEnvData, ScriptEnvDataVo, AppEnvInfoDto> {

    AppEnvInfoMapstruct INSTANCE = Mappers.getMapper(AppEnvInfoMapstruct.class);

}
