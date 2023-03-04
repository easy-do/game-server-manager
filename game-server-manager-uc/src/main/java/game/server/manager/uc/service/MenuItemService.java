package game.server.manager.uc.service;

import game.server.manager.uc.dto.MenuAddDto;
import game.server.manager.uc.dto.MenuModifyDto;
import game.server.manager.uc.entity.MenuItem;
import com.baomidou.mybatisplus.extension.service.IService;
import game.server.manager.uc.qo.MenuTreeQo;
import game.server.manager.uc.vo.MenuResultVO;
import game.server.manager.uc.vo.MenuTreeVO;

import java.util.List;

/**
* @author yuzhanfeng
* @description 针对表【menu_item】的数据库操作Service
* @createDate 2023-03-04 20:59:05
*/
public interface MenuItemService extends IService<MenuItem> {

    /**
     * 树列表
     *
     * @param menuTreeQo menuTreeQo
     * @return java.util.List<game.server.manager.uc.vo.MenuTreeVO>
     * @author laoyu
     * @date 2023/3/4
     */
    List<MenuTreeVO> treeList(MenuTreeQo menuTreeQo);

    /**
     * 详情
     *
     * @param id id
     * @return game.server.manager.uc.vo.MenuResultVO
     * @author laoyu
     * @date 2023/3/4
     */
    MenuResultVO info(String id);

    /**
     * 添加
     *
     * @param dto dto
     * @return String
     * @author laoyu
     * @date 2023/3/4
     */
    String insert(MenuAddDto dto);

    /**
     * 修改
     *
     * @param dto dto
     * @return boolean
     * @author laoyu
     * @date 2023/3/4
     */
    boolean update(MenuModifyDto dto);

    /**
     * 删除
     *
     * @param id id
     * @return java.lang.Boolean
     * @author laoyu
     * @date 2023/3/4
     */
    Boolean delete(String id);

}
