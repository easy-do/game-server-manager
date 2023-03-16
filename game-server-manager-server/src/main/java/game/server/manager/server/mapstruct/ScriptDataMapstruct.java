package game.server.manager.server.mapstruct;

import game.server.manager.common.vo.ScriptDataVo;
import game.server.manager.mapstruct.BaseMapstruct;
import game.server.manager.server.dto.ScriptDataDto;
import game.server.manager.server.entity.ScriptData;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


/**
 * @author yuzhanfeng
 */
@Mapper
public interface ScriptDataMapstruct extends BaseMapstruct<ScriptData, ScriptDataVo, ScriptDataDto> {

    ScriptDataMapstruct INSTANCE = Mappers.getMapper(ScriptDataMapstruct.class);

}
