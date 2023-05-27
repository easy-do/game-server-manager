package plus.easydo.server.mapstruct;

import plus.easydo.server.dto.ApplicationVersionDto;
import plus.easydo.server.vo.server.ApplicationVersionVo;
import plus.easydo.server.entity.ApplicationVersion;
import plus.easydo.mapstruct.BaseMapstruct;
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
