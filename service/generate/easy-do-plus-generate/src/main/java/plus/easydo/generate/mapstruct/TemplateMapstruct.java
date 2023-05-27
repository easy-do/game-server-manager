package plus.easydo.generate.mapstruct;


import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import plus.easydo.generate.dto.TemplateManagementDto;
import plus.easydo.generate.entity.TemplateManagement;
import plus.easydo.generate.vo.TemplateManagementVo;
import plus.easydo.mapstruct.BaseMapstruct;

/**
 * @author laoyu
 * @version 1.0
 * @date 2022/9/4
 */
@Mapper(componentModel = "spring")
public interface TemplateMapstruct extends BaseMapstruct<TemplateManagement, TemplateManagementVo, TemplateManagementDto> {


    TemplateMapstruct INSTANCE = Mappers.getMapper(TemplateMapstruct.class);
}
