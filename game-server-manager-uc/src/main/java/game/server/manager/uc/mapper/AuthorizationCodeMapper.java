package game.server.manager.uc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import game.server.manager.uc.entity.AuthorizationCode;
import org.apache.ibatis.annotations.Mapper;

/**
* @author yuzhanfeng
* @description 针对表【authorization_code(授权码)】的数据库操作Mapper
* @createDate 2022-05-19 13:13:29
* @Entity game.server.manager.server.entity.AuthorizationCode
*/
@Mapper
public interface AuthorizationCodeMapper extends BaseMapper<AuthorizationCode> {

}




