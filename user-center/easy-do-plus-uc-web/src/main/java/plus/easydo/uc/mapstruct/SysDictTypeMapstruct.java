package plus.easydo.uc.mapstruct;

import plus.easydo.common.vo.SysDictTypeVo;
import plus.easydo.mapstruct.BaseMapstruct;
import plus.easydo.uc.dto.SysDictTypeDto;
import plus.easydo.uc.entity.SysDictType;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


/**
 * @author yuzhanfeng
 */
@Mapper
public interface SysDictTypeMapstruct extends BaseMapstruct<SysDictType, SysDictTypeVo, SysDictTypeDto> {

    SysDictTypeMapstruct INSTANCE = Mappers.getMapper(SysDictTypeMapstruct.class);

}
