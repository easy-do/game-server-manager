package plus.easydo.server.service;


import plus.easydo.server.dto.ApplicationVersionDto;
import plus.easydo.web.base.BaseService;
import plus.easydo.server.qo.server.ApplicationVersionQo;
import plus.easydo.server.vo.server.ApplicationVersionVo;
import plus.easydo.server.entity.ApplicationVersion;

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
     * @return java.util.List<server.vo.server.plus.easydo.ApplicationVersionVo>
     * @author laoyu
     * @date 2023/3/19
     */
    List<ApplicationVersionVo> versionList(Long applicationId);

    /**
     * 根据应用id和版本获取信息
     *
     * @param applicationId
     * @param version       version
     * @return entity.server.plus.easydo.ApplicationVersion
     * @author laoyu
     * @date 2023/3/19
     */
    ApplicationVersion getByApplicationIdAndVersion(Long applicationId, String version);
}
