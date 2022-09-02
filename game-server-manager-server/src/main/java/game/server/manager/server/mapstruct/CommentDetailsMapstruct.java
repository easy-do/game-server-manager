package game.server.manager.server.mapstruct;

import game.server.manager.common.vo.CommentDetailsVo;
import game.server.manager.mapstruct.BaseMapstruct;
import game.server.manager.server.dto.CommentDetailsDto;
import game.server.manager.server.entity.CommentDetails;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author yuzhanfeng
 * @Date 2022/8/30 17:49
 */
@Mapper
public interface CommentDetailsMapstruct extends BaseMapstruct<CommentDetails, CommentDetailsVo, CommentDetailsDto> {

    CommentDetailsMapstruct INSTANCE = Mappers.getMapper(CommentDetailsMapstruct.class);
}
