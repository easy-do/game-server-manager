package plus.easydo.server.mapstruct;

import plus.easydo.common.vo.ScriptEnvDataVo;
import plus.easydo.mapstruct.BaseMapstruct;
import plus.easydo.server.dto.AppEnvInfoDto;
import plus.easydo.server.entity.ScriptEnvData;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


/**
 * @author yuzhanfeng
 */
@Mapper
public interface AppEnvInfoMapstruct extends BaseMapstruct<ScriptEnvData, ScriptEnvDataVo, AppEnvInfoDto> {

    AppEnvInfoMapstruct INSTANCE = Mappers.getMapper(AppEnvInfoMapstruct.class);

}
