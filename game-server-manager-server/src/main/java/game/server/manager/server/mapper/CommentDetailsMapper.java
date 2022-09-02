package game.server.manager.server.mapper;

import game.server.manager.server.entity.CommentDetails;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author yuzhanfeng
* @description 针对表【comment_details(评论信息)】的数据库操作Mapper
* @createDate 2022-07-03 20:00:35
* @Entity game.server.manager.server.entity.CommentDetailsDto
*/
@Mapper
public interface CommentDetailsMapper extends BaseMapper<CommentDetails> {

}




