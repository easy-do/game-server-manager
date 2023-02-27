package game.server.manager.uc.mapstruct;

import game.server.manager.uc.entity.OauthClientDetails;
import game.server.manager.uc.vo.OauthClientDetailsVo;
import game.server.manager.uc.dto.OauthClientDetailsDto;
import game.server.manager.mapstruct.BaseMapstruct;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


import java.io.Serializable;
import java.util.List;



/**
 * 授权客户端信息转换类
 * @author yuzhanfeng
 * @date 2023-02-27 16:01:25
 */
@Mapper
public interface OauthClientDetailsMapstruct extends BaseMapstruct<OauthClientDetails, OauthClientDetailsVo, OauthClientDetailsDto> {

    OauthClientDetailsMapstruct INSTANCE = Mappers.getMapper(OauthClientDetailsMapstruct.class);

}