package plus.easydo.uc.mapstruct;

import plus.easydo.common.dto.UserMessageDto;
import plus.easydo.common.vo.UserMessageVo;
import plus.easydo.mapstruct.BaseMapstruct;
import plus.easydo.uc.entity.UserMessage;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


/**
 * @author yuzhanfeng
 */
@Mapper
public interface UserMessageMapstruct extends BaseMapstruct<UserMessage, UserMessageVo, UserMessageDto> {

    UserMessageMapstruct INSTANCE = Mappers.getMapper(UserMessageMapstruct.class);

}
