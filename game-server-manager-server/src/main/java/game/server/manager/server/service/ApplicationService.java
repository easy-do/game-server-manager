package game.server.manager.server.service;

import game.server.manager.web.base.BaseService;
import game.server.manager.server.dto.ApplicationDto;
import game.server.manager.server.qo.ApplicationQo;
import game.server.manager.server.vo.ApplicationVo;
import game.server.manager.server.entity.Application;


/**
 * 应用信息Service接口
 * 
 * @author yuzhanfeng
 * @date 2023-03-18 00:48:08
 */
public interface ApplicationService extends BaseService<Application, ApplicationQo, ApplicationVo, ApplicationDto>{


}
