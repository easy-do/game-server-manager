package plus.easydo.uc.mapstruct;

import plus.easydo.common.vo.SysDictDataVo;
import plus.easydo.mapstruct.BaseMapstruct;
import plus.easydo.uc.dto.SysDictDataDto;
import plus.easydo.uc.entity.SysDictData;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


/**
 * @author yuzhanfeng
 */
@Mapper
public interface SysDictDataMapstruct extends BaseMapstruct<SysDictData, SysDictDataVo, SysDictDataDto> {

    SysDictDataMapstruct INSTANCE = Mappers.getMapper(SysDictDataMapstruct.class);

}
