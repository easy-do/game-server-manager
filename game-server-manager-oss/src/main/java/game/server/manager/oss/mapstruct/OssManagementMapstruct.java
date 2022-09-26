package game.server.manager.oss.mapstruct;

import game.server.manager.oss.entity.OssManagement;
import game.server.manager.oss.vo.OssManagementVo;
import game.server.manager.oss.dto.OssManagementDto;
import game.server.manager.mapstruct.BaseMapstruct;
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