package plus.easydo.uc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import plus.easydo.uc.entity.SysUserRole;

import java.util.List;

/**
* @author yuzhanfeng
* @description 针对表【sys_user_role(用户和角色关联表)】的数据库操作Service
* @createDate 2022-07-17 16:11:13
*/
public interface SysUserRoleService extends IService<SysUserRole> {

    /**
     * 根据角色获取集合
     *
     * @param userId userId
     * @return java.util.List<plus.easydo.server.entity.SysUserRole>
     * @author laoyu
     * @date 2022/7/20
     */
    List<SysUserRole> listByUserId(Long userId);

    /**
     * 根据角色获取集合
     *
     * @param userId userId
     * @return java.util.List<plus.easydo.server.entity.SysUserRole>
     * @author laoyu
     * @date 2022/7/20
     */
    List<Long> getRoleIdListByUserId(Long userId);

}
