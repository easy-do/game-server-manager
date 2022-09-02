package game.server.manager.server.mapper;

import game.server.manager.server.entity.Blacklist;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author yuzhanfeng
* @description 针对表【blacklist(IP黑名单)】的数据库操作Mapper
* @createDate 2022-08-24 17:21:37
* @Entity game.server.manager.server.entity.Blacklist
*/
@Mapper
public interface BlacklistMapper extends BaseMapper<Blacklist> {

}




