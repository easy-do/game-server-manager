package game.server.manager.server.mapstruct;

import game.server.manager.server.entity.ApplicationVersion;
import game.server.manager.server.vo.server.ApplicationVersionVo;
import game.server.manager.server.dto.ApplicationVersionDto;
import game.server.manager.mapstruct.BaseMapstruct;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


/**
 * 应用版本信息转换类
 * @author yuzhanfeng
 * @date 2023-03-18 14:56:21
 */
@Mapper
public interface ApplicationVersionMapstruct extends BaseMapstruct<ApplicationVersion, ApplicationVersionVo, ApplicationVersionDto> {

    ApplicationVersionMapstruct INSTANCE = Mappers.getMapper(ApplicationVersionMapstruct.class);

}
