package game.server.manager.uc.mapstruct;

import game.server.manager.common.vo.SysMenuVo;
import game.server.manager.mapstruct.BaseMapstruct;
import game.server.manager.uc.dto.SysMenuDto;
import game.server.manager.uc.entity.SysMenu;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


/**
 * @author yuzhanfeng
 */
@Mapper
public interface SysMenuMapstruct extends BaseMapstruct<SysMenu, SysMenuVo, SysMenuDto> {

    SysMenuMapstruct INSTANCE = Mappers.getMapper(SysMenuMapstruct.class);

}
