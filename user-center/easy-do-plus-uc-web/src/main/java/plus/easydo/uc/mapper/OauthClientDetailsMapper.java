package plus.easydo.uc.mapper;


import plus.easydo.uc.entity.OauthClientDetails;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author yuzhanfeng
 * @description 授权客户端信息的数据库操作Mapper
 * @Entity entity.plus.easydo.uc.OauthClientDetails
 */
@Mapper
public interface OauthClientDetailsMapper extends BaseMapper<OauthClientDetails> {
    
}
