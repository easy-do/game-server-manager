package game.server.manager.server.mapstruct;

import game.server.manager.common.vo.ApplicationInfoVo;
import game.server.manager.mapstruct.BaseMapstruct;
import game.server.manager.server.dto.ApplicationInfoDto;
import game.server.manager.server.entity.ApplicationInfo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


/**
 * @author yuzhanfeng
 */
@Mapper
public interface ApplicationInfoMapstruct extends BaseMapstruct<ApplicationInfo, ApplicationInfoVo, ApplicationInfoDto> {

    ApplicationInfoMapstruct INSTANCE = Mappers.getMapper(ApplicationInfoMapstruct.class);

}
