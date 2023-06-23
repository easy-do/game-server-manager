package plus.easydo.uc.service;

import plus.easydo.uc.entity.SysRoleResource;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author yuzhanfeng
* @description 针对表【sys_role_resource(角色和资源关联表)】的数据库操作Service
* @createDate 2022-09-20 09:31:33
*/
public interface SysRoleResourceService extends IService<SysRoleResource> {

    /**
     * 根据角色id删除
     *
     * @param roleId roleId
     * @return boolean
     * @author laoyu
     * @date 2022/9/20
     */
    boolean removeByRoleId(Long roleId);
}
