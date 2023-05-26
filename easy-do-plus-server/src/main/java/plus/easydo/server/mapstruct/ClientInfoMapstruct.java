package plus.easydo.server.mapstruct;

import plus.easydo.common.vo.ClientInfoVo;
import plus.easydo.mapstruct.BaseMapstruct;
import plus.easydo.server.dto.ClientInfoDto;
import plus.easydo.server.entity.ClientInfo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


/**
 * @author yuzhanfeng
 */
@Mapper
public interface ClientInfoMapstruct extends BaseMapstruct<ClientInfo, ClientInfoVo, ClientInfoDto> {

    ClientInfoMapstruct INSTANCE = Mappers.getMapper(ClientInfoMapstruct.class);

}
