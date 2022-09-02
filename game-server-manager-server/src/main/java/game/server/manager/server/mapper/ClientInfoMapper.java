package game.server.manager.server.mapper;

import game.server.manager.server.entity.ClientInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author yuzhanfeng
* @description 针对表【client_info(客户端信息)】的数据库操作Mapper
* @createDate 2022-08-04 19:22:22
* @Entity game.server.manager.server.entity.ClientInfo
*/
@Mapper
public interface ClientInfoMapper extends BaseMapper<ClientInfo> {

}




