package game.server.manager.uc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import game.server.manager.uc.entity.SysMenu;
import org.apache.ibatis.annotations.Mapper;

/**
* @author yuzhanfeng
* @description 针对表【sys_menu(菜单权限表)】的数据库操作Mapper
* @createDate 2022-07-18 19:57:36
* @Entity game.server.manager.uc.entity.SysMenu
*/
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

}




