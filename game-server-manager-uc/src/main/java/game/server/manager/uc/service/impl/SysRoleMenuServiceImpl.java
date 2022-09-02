package game.server.manager.uc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import game.server.manager.uc.entity.SysRoleMenu;
import game.server.manager.uc.mapper.SysRoleMenuMapper;
import game.server.manager.uc.service.SysRoleMenuService;
import org.springframework.stereotype.Service;

/**
* @author yuzhanfeng
* @description 针对表【sys_role_menu(角色和菜单关联表)】的数据库操作Service实现
* @createDate 2022-07-18 19:57:46
*/
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu>
    implements SysRoleMenuService {

    @Override
    public void removeByRoleId(Long roleId) {
        LambdaQueryWrapper<SysRoleMenu> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysRoleMenu::getRoleId,roleId);
        remove(wrapper);
    }
}




