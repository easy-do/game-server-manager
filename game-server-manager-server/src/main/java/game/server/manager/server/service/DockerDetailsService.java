package game.server.manager.server.service;

import game.server.manager.server.entity.DockerDetails;
import game.server.manager.web.base.BaseService;
import game.server.manager.server.dto.DockerDetailsDto;
import game.server.manager.server.qo.DockerDetailsQo;
import game.server.manager.server.vo.DockerDetailsVo;


/**
 * docker配置信息Service接口
 * 
 * @author yuzhanfeng
 * @date 2022-11-13 12:10:30
 */
public interface DockerDetailsService extends BaseService<DockerDetails, DockerDetailsQo, DockerDetailsVo, DockerDetailsDto>{


}
