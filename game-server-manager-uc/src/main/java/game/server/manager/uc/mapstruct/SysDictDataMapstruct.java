package game.server.manager.uc.mapstruct;

import game.server.manager.common.vo.SysDictDataVo;
import game.server.manager.mapstruct.BaseMapstruct;
import game.server.manager.uc.dto.SysDictDataDto;
import game.server.manager.uc.entity.SysDictData;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


/**
 * @author yuzhanfeng
 */
@Mapper
public interface SysDictDataMapstruct extends BaseMapstruct<SysDictData, SysDictDataVo, SysDictDataDto> {

    SysDictDataMapstruct INSTANCE = Mappers.getMapper(SysDictDataMapstruct.class);

}
