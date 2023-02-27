package game.server.manager.uc.service;

import game.server.manager.web.base.BaseService;
import game.server.manager.uc.dto.OauthClientDetailsDto;
import game.server.manager.uc.qo.OauthClientDetailsQo;
import game.server.manager.uc.vo.OauthClientDetailsVo;
import game.server.manager.uc.entity.OauthClientDetails;


/**
 * 授权客户端信息Service接口
 * 
 * @author yuzhanfeng
 * @date 2023-02-27 16:01:25
 */
public interface OauthClientDetailsService extends BaseService<OauthClientDetails, OauthClientDetailsQo, OauthClientDetailsVo, OauthClientDetailsDto>{


}
