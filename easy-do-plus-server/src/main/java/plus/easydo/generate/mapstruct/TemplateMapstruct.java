package plus.easydo.generate.mapstruct;

import plus.easydo.generate.dto.TemplateManagementDto;
import plus.easydo.generate.entity.TemplateManagement;
import plus.easydo.generate.vo.TemplateManagementVo;
import plus.easydo.mapstruct.BaseMapstruct;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author laoyu
 * @version 1.0
 * @date 2022/9/4
 */
@Mapper
public interface TemplateMapstruct extends BaseMapstruct<TemplateManagement, TemplateManagementVo, TemplateManagementDto> {


    TemplateMapstruct INSTANCE = Mappers.getMapper(TemplateMapstruct.class);
}