package plus.easydo.server.service.oss;

import plus.easydo.server.dto.OssManagementDto;
import plus.easydo.server.entity.OssManagement;
import plus.easydo.server.qo.oss.OssManagementQo;
import plus.easydo.server.vo.oss.OssManagementVo;
import plus.easydo.web.base.BaseService;


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
