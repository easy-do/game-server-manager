package game.server.manager.uc.mapstruct;

import game.server.manager.common.vo.UserInfoVo;
import game.server.manager.mapstruct.BaseMapstruct;
import game.server.manager.uc.dto.UserInfoDto;
import game.server.manager.uc.entity.UserInfo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


/**
 * @author yuzhanfeng
 */
@Mapper
public interface UserInfoMapstruct extends BaseMapstruct<UserInfo, UserInfoVo, UserInfoDto> {

    UserInfoMapstruct INSTANCE = Mappers.getMapper(UserInfoMapstruct.class);

}
