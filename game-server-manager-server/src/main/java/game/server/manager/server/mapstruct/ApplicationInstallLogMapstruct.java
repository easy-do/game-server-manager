package game.server.manager.server.mapstruct;

import game.server.manager.server.entity.ApplicationInstallLog;
import game.server.manager.server.vo.ApplicationInstallLogVo;
import game.server.manager.server.dto.ApplicationInstallLogDto;
import game.server.manager.mapstruct.BaseMapstruct;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


import java.io.Serializable;
import java.util.List;



/**
 * 应用安装日志转换类
 * @author yuzhanfeng
 * @date 2023-04-08 18:35:18
 */
@Mapper
public interface ApplicationInstallLogMapstruct extends BaseMapstruct<ApplicationInstallLog, ApplicationInstallLogVo, ApplicationInstallLogDto> {

    ApplicationInstallLogMapstruct INSTANCE = Mappers.getMapper(ApplicationInstallLogMapstruct.class);

}