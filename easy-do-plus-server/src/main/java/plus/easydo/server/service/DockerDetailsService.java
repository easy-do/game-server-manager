package plus.easydo.server.service;

import plus.easydo.server.entity.DockerDetails;
import plus.easydo.server.dto.DockerDetailsDto;
import plus.easydo.web.base.BaseService;
import plus.easydo.server.qo.server.DockerDetailsQo;
import plus.easydo.server.vo.server.DockerDetailsVo;


/**
 * docker配置信息Service接口
 * 
 * @author yuzhanfeng
 * @date 2022-11-13 12:10:30
 */
public interface DockerDetailsService extends BaseService<DockerDetails, DockerDetailsQo, DockerDetailsVo, DockerDetailsDto>{


    /**
     * 根据客户端获取docker信息
     *
     * @param clientId clientId
     * @return server.vo.server.plus.easydo.DockerDetailsVo
     * @author laoyu
     * @date 2023/3/19
     */
    DockerDetailsVo getByClientId(String clientId);
}
