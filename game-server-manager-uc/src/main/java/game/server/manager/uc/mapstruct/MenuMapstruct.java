package game.server.manager.uc.mapstruct;

import game.server.manager.uc.dto.MenuAddDto;
import game.server.manager.uc.dto.MenuModifyDto;
import game.server.manager.uc.entity.MenuItem;
import game.server.manager.uc.vo.MenuResultVO;
import game.server.manager.uc.vo.MenuTreeVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author laoyu
 * @version 1.0
 * @description 菜单转换
 * @date 2023/3/4
 */
@Mapper
public interface MenuMapstruct {

    MenuMapstruct INSTANCE = Mappers.getMapper(MenuMapstruct.class);

    List<MenuTreeVO> entityListToTreeVoiList(List<MenuItem> entityList);

    MenuResultVO entityToVo(MenuItem menu);

    MenuItem addDtoToEntity(MenuAddDto dto);

    MenuItem updateDtoToEntity(MenuModifyDto dto);
}
