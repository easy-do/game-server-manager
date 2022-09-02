package game.server.manager.uc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import game.server.manager.uc.entity.UserMessage;
import org.apache.ibatis.annotations.Mapper;

/**
* @author yuzhanfeng
* @description 针对表【user_message(用户消息)】的数据库操作Mapper
* @createDate 2022-07-09 23:37:47
* @Entity game.server.manager.server.entity.UserMessage
*/
@Mapper
public interface UserMessageMapper extends BaseMapper<UserMessage> {

}




