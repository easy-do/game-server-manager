package game.server.manager.server.mapper;

import game.server.manager.server.entity.Discussion;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author yuzhanfeng
* @description 针对表【common_problem(常见问题)】的数据库操作Mapper
* @createDate 2022-07-03 20:00:31
* @Entity game.server.manager.server.entity.DiscussionDto
*/
@Mapper
public interface CommonProblemMapper extends BaseMapper<Discussion> {

}




