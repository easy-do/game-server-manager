package game.server.manager.server.service;

import game.server.manager.common.application.DeployParam;
import game.server.manager.mybatis.plus.qo.MpBaseQo;
import game.server.manager.web.base.BaseService;
import game.server.manager.server.dto.ApplicationInfoDto;
import game.server.manager.server.entity.ApplicationInfo;
import game.server.manager.common.vo.ApplicationInfoVo;

import java.io.Serializable;

/**
* @author yuzhanfeng
* @description 针对表【application_info(应用信息)】的数据库操作Service
* @createDate 2022-05-20 10:29:54
*/
public interface ApplicationInfoService extends BaseService<ApplicationInfo, MpBaseQo<ApplicationInfo>, ApplicationInfoVo, ApplicationInfoDto> {

    /**
     * 统计用户应用数量
     *
     * @param userId userId
     * @return long
     * @author laoyu
     * @date 2022/5/21
     */
    long countByUserId(Long userId);

    /**
     * 拉黑用户的所有应用
     *
     * @param id id
     * @author laoyu
     * @date 2022/5/21
     */
    void setAllBlackByUserId(Long id);

    /**
     * 应用是否存在
     *
     * @param id id
     * @param userId userId
     * @return boolean
     * @author laoyu
     * @date 2022/5/21
     */
    boolean exist(String id, Long userId);

    /**
     * 部署应用
     *
     * @param deployParam deployParam
     * @return boolean
     * @author laoyu
     * @date 2022/5/21
     */
    boolean deployment(DeployParam deployParam);

    /**
     * 服务下应用数量
     *
     * @param id id
     * @return long
     * @author laoyu
     * @date 2022/5/22
     */
    long countByDeviceId(String id);

    /**
     * 绑定了APP的应用数量
     *
     * @param id id
     * @return long
     * @author laoyu
     * @date 2022/5/22
     */
    long countByAppId(Serializable id);
}
