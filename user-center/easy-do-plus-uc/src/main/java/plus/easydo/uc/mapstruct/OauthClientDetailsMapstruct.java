package plus.easydo.uc.mapstruct;

import plus.easydo.uc.entity.OauthClientDetails;
import plus.easydo.uc.vo.OauthClientDetailsVo;
import plus.easydo.uc.dto.OauthClientDetailsDto;
import plus.easydo.mapstruct.BaseMapstruct;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


/**
 * 授权客户端信息转换类
 * @author yuzhanfeng
 * @date 2023-02-27 16:01:25
 */
@Mapper
public interface OauthClientDetailsMapstruct extends BaseMapstruct<OauthClientDetails, OauthClientDetailsVo, OauthClientDetailsDto> {

    OauthClientDetailsMapstruct INSTANCE = Mappers.getMapper(OauthClientDetailsMapstruct.class);

}
