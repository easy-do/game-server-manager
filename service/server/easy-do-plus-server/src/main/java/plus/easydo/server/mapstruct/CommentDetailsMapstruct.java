package plus.easydo.server.mapstruct;

import plus.easydo.common.vo.CommentDetailsVo;
import plus.easydo.mapstruct.BaseMapstruct;
import plus.easydo.server.dto.CommentDetailsDto;
import plus.easydo.server.entity.CommentDetails;
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
