package game.server.manager.uc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import game.server.manager.uc.entity.SysRoleMenu;
import org.apache.ibatis.annotations.Mapper;

/**
* @author yuzhanfeng
* @description 针对表【sys_role_menu(角色和菜单关联表)】的数据库操作Mapper
* @createDate 2022-07-18 19:57:46
* @Entity game.server.manager.uc.entity.SysRoleMenu
*/
@Mapper
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {

}




