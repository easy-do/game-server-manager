package plus.easydo.server.service;

import plus.easydo.dao.qo.MpBaseQo;
import plus.easydo.server.dto.ServerInfoDto;
import plus.easydo.web.base.BaseService;
import plus.easydo.server.entity.ServerInfo;
import plus.easydo.common.vo.ServerInfoVo;

/**
* @author yuzhanfeng
* @description 针对表【server_info(服务器信息)】的数据库操作Service
* @createDate 2022-05-19 19:29:55
*/
public interface ServerInfoService extends BaseService<ServerInfo, MpBaseQo<ServerInfo>, ServerInfoVo, ServerInfoDto> {

    /**
     * 统计用户服务数量
     *
     * @param userId userId
     * @return long
     * @author laoyu
     * @date 2022/5/21
     */
    long countByUserId(Long userId);

    /**
     * 是否存在
     *
     * @param serverId serverId
     * @return boolean
     * @author laoyu
     * @date 2022/7/2
     */
    boolean exists(Long serverId);


    /**
     * 测试服务器连通性
     *
     * @param serverInfoDto serverInfoDto
     * @return java.lang.String
     * @author laoyu
     * @date 2022/7/29
     */
    boolean test(ServerInfoDto serverInfoDto);
}
