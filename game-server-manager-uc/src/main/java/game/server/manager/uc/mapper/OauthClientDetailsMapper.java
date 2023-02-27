package game.server.manager.uc.mapper;


import game.server.manager.uc.entity.OauthClientDetails;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author yuzhanfeng
 * @description 授权客户端信息的数据库操作Mapper
 * @Entity game.server.manager.uc.entity.OauthClientDetails
 */
@Mapper
public interface OauthClientDetailsMapper extends BaseMapper<OauthClientDetails> {
    
}
