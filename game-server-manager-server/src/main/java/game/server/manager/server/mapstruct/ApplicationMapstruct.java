package game.server.manager.server.mapstruct;

import game.server.manager.server.entity.Application;
import game.server.manager.server.vo.ApplicationVo;
import game.server.manager.server.dto.ApplicationDto;
import game.server.manager.mapstruct.BaseMapstruct;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


import java.io.Serializable;
import java.util.List;



/**
 * 应用信息转换类
 * @author yuzhanfeng
 * @date 2023-03-18 00:48:08
 */
@Mapper
public interface ApplicationMapstruct extends BaseMapstruct<Application, ApplicationVo, ApplicationDto> {

    ApplicationMapstruct INSTANCE = Mappers.getMapper(ApplicationMapstruct.class);

}