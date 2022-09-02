package game.server.manager.uc.mapstruct;

import game.server.manager.common.dto.UserMessageDto;
import game.server.manager.common.vo.UserMessageVo;
import game.server.manager.mapstruct.BaseMapstruct;
import game.server.manager.uc.entity.UserMessage;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


/**
 * @author yuzhanfeng
 */
@Mapper
public interface UserMessageMapstruct extends BaseMapstruct<UserMessage, UserMessageVo, UserMessageDto> {

    UserMessageMapstruct INSTANCE = Mappers.getMapper(UserMessageMapstruct.class);

}
