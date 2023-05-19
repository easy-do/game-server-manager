package game.server.manager.generate.mapstruct;

import game.server.manager.generate.dto.TemplateManagementDto;
import game.server.manager.generate.entity.TemplateManagement;
import game.server.manager.generate.vo.TemplateManagementVo;
import game.server.manager.mapstruct.BaseMapstruct;
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
