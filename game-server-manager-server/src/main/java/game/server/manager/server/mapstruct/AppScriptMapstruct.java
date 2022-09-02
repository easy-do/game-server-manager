package game.server.manager.server.mapstruct;

import game.server.manager.common.vo.AppScriptVo;
import game.server.manager.mapstruct.BaseMapstruct;
import game.server.manager.server.dto.AppScriptDto;
import game.server.manager.server.entity.AppScript;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


/**
 * @author yuzhanfeng
 */
@Mapper
public interface AppScriptMapstruct extends BaseMapstruct<AppScript, AppScriptVo, AppScriptDto> {

    AppScriptMapstruct INSTANCE = Mappers.getMapper(AppScriptMapstruct.class);

}
