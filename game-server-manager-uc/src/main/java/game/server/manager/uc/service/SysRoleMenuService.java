package game.server.manager.uc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import game.server.manager.uc.entity.SysRoleMenu;

/**
* @author yuzhanfeng
* @description 针对表【sys_role_menu(角色和菜单关联表)】的数据库操作Service
* @createDate 2022-07-18 19:57:46
*/
public interface SysRoleMenuService extends IService<SysRoleMenu> {

    /**
     * 删除角色关联关系
     *
     * @param roleId roleId
     * @author laoyu
     * @date 2022/7/20
     */
    void removeByRoleId(Long roleId);
}
