package game.server.manager.server.mapstruct;

import game.server.manager.common.vo.DiscussionVo;
import game.server.manager.mapstruct.BaseMapstruct;
import game.server.manager.server.dto.DiscussionDto;
import game.server.manager.server.entity.Discussion;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


/**
 * @author yuzhanfeng
 */
@Mapper
public interface DiscussionMapstruct extends BaseMapstruct<Discussion, DiscussionVo, DiscussionDto> {

    DiscussionMapstruct INSTANCE = Mappers.getMapper(DiscussionMapstruct.class);

}
