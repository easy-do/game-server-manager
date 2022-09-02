package game.server.manager.server.mapper;

import game.server.manager.server.entity.ServerInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author yuzhanfeng
* @description 针对表【server_info(服务器信息)】的数据库操作Mapper
* @createDate 2022-05-19 19:29:55
* @Entity game.server.manager.server.entity.ServerInfo
*/
@Mapper
public interface ServerInfoMapper extends BaseMapper<ServerInfo> {

}




