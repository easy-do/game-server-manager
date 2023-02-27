package game.server.manager.uc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import game.server.manager.uc.entity.OauthClientDetails;
import game.server.manager.uc.service.OauthClientDetailsService;
import game.server.manager.uc.mapper.OauthClientDetailsMapper;
import org.springframework.stereotype.Service;

/**
* @author yuzhanfeng
* @description 针对表【oauth_client_details(授权客户端信息)】的数据库操作Service实现
* @createDate 2023-02-27 09:29:32
*/
@Service
public class OauthClientDetailsServiceImpl extends ServiceImpl<OauthClientDetailsMapper, OauthClientDetails>
    implements OauthClientDetailsService{

}




