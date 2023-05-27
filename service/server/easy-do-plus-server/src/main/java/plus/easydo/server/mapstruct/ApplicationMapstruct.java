package plus.easydo.server.mapstruct;

import plus.easydo.server.dto.ApplicationDto;
import plus.easydo.server.vo.server.ApplicationVo;
import plus.easydo.server.entity.Application;
import plus.easydo.mapstruct.BaseMapstruct;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


/**
 * 应用信息转换类
 * @author yuzhanfeng
 * @date 2023-03-18 00:48:08
 */
@Mapper
public interface ApplicationMapstruct extends BaseMapstruct<Application, ApplicationVo, ApplicationDto> {

    ApplicationMapstruct INSTANCE = Mappers.getMapper(ApplicationMapstruct.class);

}
