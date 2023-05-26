package plus.easydo.uc.mapstruct;

import plus.easydo.auth.vo.SimpleUserInfoVo;
import plus.easydo.common.vo.UserInfoVo;
import plus.easydo.mapstruct.BaseMapstruct;
import plus.easydo.uc.dto.UserInfoDto;
import plus.easydo.uc.entity.UserInfo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


/**
 * @author yuzhanfeng
 */
@Mapper
public interface UserInfoMapstruct extends BaseMapstruct<UserInfo, UserInfoVo, UserInfoDto> {

    UserInfoMapstruct INSTANCE = Mappers.getMapper(UserInfoMapstruct.class);

    SimpleUserInfoVo voToSimpleVo(UserInfoVo userInfoVo);

}
