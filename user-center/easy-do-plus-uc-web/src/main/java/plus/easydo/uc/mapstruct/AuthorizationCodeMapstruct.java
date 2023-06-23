package plus.easydo.uc.mapstruct;

import plus.easydo.uc.dto.AuthorizationCodeDto;
import plus.easydo.uc.vo.AuthorizationCodeVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import plus.easydo.mapstruct.BaseMapstruct;
import plus.easydo.uc.entity.AuthorizationCode;


/**
 * @author yuzhanfeng
 */
@Mapper
public interface AuthorizationCodeMapstruct extends BaseMapstruct<AuthorizationCode, AuthorizationCodeVo, AuthorizationCodeDto> {

    AuthorizationCodeMapstruct INSTANCE = Mappers.getMapper(AuthorizationCodeMapstruct.class);

}
