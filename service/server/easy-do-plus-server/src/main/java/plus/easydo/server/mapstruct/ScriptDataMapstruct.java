package plus.easydo.server.mapstruct;

import plus.easydo.common.vo.ScriptDataVo;
import plus.easydo.mapstruct.BaseMapstruct;
import plus.easydo.server.dto.ScriptDataDto;
import plus.easydo.server.entity.ScriptData;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


/**
 * @author yuzhanfeng
 */
@Mapper
public interface ScriptDataMapstruct extends BaseMapstruct<ScriptData, ScriptDataVo, ScriptDataDto> {

    ScriptDataMapstruct INSTANCE = Mappers.getMapper(ScriptDataMapstruct.class);

}
