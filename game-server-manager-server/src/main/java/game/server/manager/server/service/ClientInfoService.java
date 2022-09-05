package game.server.manager.server.service;

import game.server.manager.common.vo.ClientInfoVo;
import game.server.manager.mybatis.plus.qo.MpBaseQo;
import game.server.manager.web.base.BaseService;
import game.server.manager.server.dto.ClientInfoDto;
import game.server.manager.server.entity.ClientInfo;

/**
* @author yuzhanfeng
* @description 针对表【client_info(客户端信息)】的数据库操作Service
* @createDate 2022-08-04 19:22:22
*/
public interface ClientInfoService extends BaseService<ClientInfo, MpBaseQo, ClientInfoVo, ClientInfoDto> {

    /**
     * 在线安装
     *
     * @param id id
     * @return boolean
     * @author laoyu
     * @date 2022/8/7
     */
    boolean onlineInstall(String id);


    /**
     * 获得手动安装命令
     *
     * @param id id
     * @return java.lang.String
     * @author laoyu
     * @date 2022/8/7
     */
    String getInstallCmd(String id);

    /**
     * 是否存在
     *
     * @param clientId clientId
     * @return boolean
     * @author laoyu
     * @date 2022/8/7
     */
    boolean exists(String clientId);
}
