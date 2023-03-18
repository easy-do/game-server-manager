package game.server.manager.server.service;


import game.server.manager.web.base.BaseService;
import game.server.manager.server.dto.ApplicationVersionDto;
import game.server.manager.server.qo.ApplicationVersionQo;
import game.server.manager.server.vo.ApplicationVersionVo;
import game.server.manager.server.entity.ApplicationVersion;

import java.util.List;


/**
 * 应用版本信息Service接口
 * 
 * @author yuzhanfeng
 * @date 2023-03-18 14:56:21
 */
public interface ApplicationVersionService extends BaseService<ApplicationVersion, ApplicationVersionQo, ApplicationVersionVo, ApplicationVersionDto>{

    /**
     * 获取应用版本列表
     *
     * @param applicationId applicationId
     * @return java.util.List<game.server.manager.server.vo.ApplicationVersionVo>
     * @author laoyu
     * @date 2023/3/19
     */
    List<ApplicationVersionVo> versionList(Long applicationId);

    /**
     * 根据应用id和版本获取信息
     *
     * @param applicationId
     * @param version       version
     * @return game.server.manager.server.entity.ApplicationVersion
     * @author laoyu
     * @date 2023/3/19
     */
    ApplicationVersion getByApplicationIdAndVersion(Long applicationId, String version);
}
