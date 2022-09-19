package game.server.manager.uc.mapstruct;

import game.server.manager.uc.entity.SysResource;
import game.server.manager.uc.vo.SysResourceVo;
import game.server.manager.uc.dto.SysResourceDto;
import game.server.manager.mapstruct.BaseMapstruct;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


import java.io.Serializable;
import java.util.List;



/**
 * 系统资源转换类
 * @author yuzhanfeng
 * @date 2022-09-19 22:43:39
 */
@Mapper
public interface SysResourceMapstruct extends BaseMapstruct<SysResource, SysResourceVo, SysResourceDto> {

    SysResourceMapstruct INSTANCE = Mappers.getMapper(SysResourceMapstruct.class);

}