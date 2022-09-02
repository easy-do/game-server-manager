package game.server.manager.server.mapstruct;

import game.server.manager.common.vo.ServerInfoVo;
import game.server.manager.mapstruct.BaseMapstruct;
import game.server.manager.server.dto.ServerInfoDto;
import game.server.manager.server.entity.ServerInfo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


/**
 * @author yuzhanfeng
 */
@Mapper
public interface ServerInfoMapstruct extends BaseMapstruct<ServerInfo, ServerInfoVo, ServerInfoDto> {

    ServerInfoMapstruct INSTANCE = Mappers.getMapper(ServerInfoMapstruct.class);

}
