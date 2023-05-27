package plus.easydo.server.mapstruct;

import plus.easydo.mapstruct.BaseMapstruct;
import plus.easydo.server.dto.OssManagementDto;
import plus.easydo.server.entity.OssManagement;
import plus.easydo.server.vo.oss.OssManagementVo;
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
