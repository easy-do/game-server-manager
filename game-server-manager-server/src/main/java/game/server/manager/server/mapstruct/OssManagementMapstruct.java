package game.server.manager.server.mapstruct;

import game.server.manager.mapstruct.BaseMapstruct;
import game.server.manager.server.dto.OssManagementDto;
import game.server.manager.server.entity.OssManagement;
import game.server.manager.server.vo.oss.OssManagementVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


/**
 * 存储管理转换类
 * @author yuzhanfeng
 * @date 2022-09-26 16:35:09
 */
@Mapper
public interface OssManagementMapstruct extends BaseMapstruct<OssManagement, OssManagementVo, OssManagementDto> {

    OssManagementMapstruct INSTANCE = Mappers.getMapper(OssManagementMapstruct.class);

}
