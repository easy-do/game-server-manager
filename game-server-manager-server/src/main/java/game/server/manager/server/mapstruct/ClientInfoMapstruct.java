package game.server.manager.server.mapstruct;

import game.server.manager.common.vo.ClientInfoVo;
import game.server.manager.mapstruct.BaseMapstruct;
import game.server.manager.server.dto.ClientInfoDto;
import game.server.manager.server.entity.ClientInfo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


/**
 * @author yuzhanfeng
 */
@Mapper
public interface ClientInfoMapstruct extends BaseMapstruct<ClientInfo, ClientInfoVo, ClientInfoDto> {

    ClientInfoMapstruct INSTANCE = Mappers.getMapper(ClientInfoMapstruct.class);

}
