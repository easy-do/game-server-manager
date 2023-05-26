package plus.easydo.server.mapstruct;

import plus.easydo.common.vo.DiscussionVo;
import plus.easydo.mapstruct.BaseMapstruct;
import plus.easydo.server.dto.DiscussionDto;
import plus.easydo.server.entity.Discussion;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


/**
 * @author yuzhanfeng
 */
@Mapper
public interface DiscussionMapstruct extends BaseMapstruct<Discussion, DiscussionVo, DiscussionDto> {

    DiscussionMapstruct INSTANCE = Mappers.getMapper(DiscussionMapstruct.class);

}
