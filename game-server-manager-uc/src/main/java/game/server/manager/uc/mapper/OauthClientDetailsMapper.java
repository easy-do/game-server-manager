package game.server.manager.uc.mapper;

import game.server.manager.uc.entity.OauthClientDetails;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author yuzhanfeng
* @description 针对表【oauth_client_details(授权客户端信息)】的数据库操作Mapper
* @createDate 2023-02-27 09:29:32
* @Entity game.server.manager.uc.entity.OauthClientDetails
*/
@Mapper
public interface OauthClientDetailsMapper extends BaseMapper<OauthClientDetails> {

}




