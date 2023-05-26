package plus.easydo.server.mapstruct;

import plus.easydo.server.entity.ApplicationInstallLog;
import plus.easydo.server.vo.server.ApplicationInstallLogVo;
import plus.easydo.server.dto.ApplicationInstallLogDto;
import plus.easydo.mapstruct.BaseMapstruct;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


/**
 * 应用安装日志转换类
 * @author yuzhanfeng
 * @date 2023-04-08 18:35:18
 */
@Mapper
public interface ApplicationInstallLogMapstruct extends BaseMapstruct<ApplicationInstallLog, ApplicationInstallLogVo, ApplicationInstallLogDto> {

    ApplicationInstallLogMapstruct INSTANCE = Mappers.getMapper(ApplicationInstallLogMapstruct.class);

}
