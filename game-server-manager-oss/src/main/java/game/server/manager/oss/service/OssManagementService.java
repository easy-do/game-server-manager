package game.server.manager.oss.service;

import game.server.manager.web.base.BaseService;
import game.server.manager.oss.dto.OssManagementDto;
import game.server.manager.oss.qo.OssManagementQo;
import game.server.manager.oss.vo.OssManagementVo;
import game.server.manager.oss.entity.OssManagement;


/**
 * 存储管理Service接口
 * 
 * @author yuzhanfeng
 * @date 2022-09-26 16:35:09
 */
public interface OssManagementService extends BaseService<OssManagement, OssManagementQo, OssManagementVo, OssManagementDto>{


    /**
     * 条件删除
     *
     * @param groupName groupName
     * @param filePath filePath
     * @param fileName fileName
     * @author laoyu
     * @date 2022/9/26
     */
    void removeByWrapper(String groupName, String filePath, String fileName);
}
