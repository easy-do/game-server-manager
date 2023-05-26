package plus.easydo.server.mapstruct;

import plus.easydo.common.vo.ServerInfoVo;
import plus.easydo.mapstruct.BaseMapstruct;
import plus.easydo.server.dto.ServerInfoDto;
import plus.easydo.server.entity.ServerInfo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


/**
 * @author yuzhanfeng
 */
@Mapper
public interface ServerInfoMapstruct extends BaseMapstruct<ServerInfo, ServerInfoVo, ServerInfoDto> {

    ServerInfoMapstruct INSTANCE = Mappers.getMapper(ServerInfoMapstruct.class);

}
