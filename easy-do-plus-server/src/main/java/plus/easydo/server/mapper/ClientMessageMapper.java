package plus.easydo.server.mapper;

import plus.easydo.server.entity.ClientMessage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author yuzhanfeng
* @description 针对表【client_message】的数据库操作Mapper
* @createDate 2022-08-07 21:14:10
* @Entity entity.server.plus.easydo.ClientMessage
*/
@Mapper
public interface ClientMessageMapper extends BaseMapper<ClientMessage> {

}




