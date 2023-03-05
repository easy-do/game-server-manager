package game.server.manager.server.service.oss;

import game.server.manager.server.dto.OssManagementDto;
import game.server.manager.server.entity.OssManagement;
import game.server.manager.server.qo.OssManagementQo;
import game.server.manager.server.vo.OssManagementVo;
import game.server.manager.web.base.BaseService;


/**
 * 存储管理Service接口
 * 
 * @author yuzhanfeng
 * @date 2022-09-26 16:35:09
 */
public interface OssManagementService extends BaseService<OssManagement, OssManagementQo, OssManagementVo, OssManagementDto> {


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
