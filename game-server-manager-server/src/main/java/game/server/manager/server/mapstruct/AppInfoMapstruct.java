package game.server.manager.server.mapstruct;

import game.server.manager.common.vo.AppInfoVo;
import game.server.manager.mapstruct.BaseMapstruct;
import game.server.manager.server.dto.AppInfoDto;
import game.server.manager.server.entity.AppInfo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


/**
 * @author yuzhanfeng
 */
@Mapper
public interface AppInfoMapstruct extends BaseMapstruct<AppInfo, AppInfoVo, AppInfoDto> {

    AppInfoMapstruct INSTANCE = Mappers.getMapper(AppInfoMapstruct.class);

}
