package game.server.manager.uc.mapstruct;

import game.server.manager.common.vo.SysDictTypeVo;
import game.server.manager.mapstruct.BaseMapstruct;
import game.server.manager.uc.dto.SysDictTypeDto;
import game.server.manager.uc.entity.SysDictType;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


/**
 * @author yuzhanfeng
 */
@Mapper
public interface SysDictTypeMapstruct extends BaseMapstruct<SysDictType, SysDictTypeVo, SysDictTypeDto> {

    SysDictTypeMapstruct INSTANCE = Mappers.getMapper(SysDictTypeMapstruct.class);

}
