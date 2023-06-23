package plus.easydo.uc.mapstruct;

import plus.easydo.uc.entity.SysResource;
import plus.easydo.uc.vo.SysResourceVo;
import plus.easydo.uc.dto.SysResourceDto;
import plus.easydo.mapstruct.BaseMapstruct;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


/**
 * 系统资源转换类
 * @author yuzhanfeng
 * @date 2022-09-19 22:43:39
 */
@Mapper
public interface SysResourceMapstruct extends BaseMapstruct<SysResource, SysResourceVo, SysResourceDto> {

    SysResourceMapstruct INSTANCE = Mappers.getMapper(SysResourceMapstruct.class);

}
