package game.server.manager.uc.controller;

import game.server.manager.common.result.DataResult;
import game.server.manager.common.result.R;
import game.server.manager.uc.dto.MenuAddDto;
import game.server.manager.uc.dto.MenuModifyDto;
import game.server.manager.uc.qo.MenuTreeQo;
import game.server.manager.uc.service.MenuItemService;
import game.server.manager.uc.vo.MenuResultVO;
import game.server.manager.uc.vo.MenuTreeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author laoyu
 * @version 1.0
 * @description 菜单接口
 * @date 2023/3/4
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuItemService menuService;

    /**
     * 菜单树列表
     *
     * @param menuTreeQo menuTreeQo
     * @return java.util.List<game.server.manager.uc.vo.MenuTreeVO>
     * @author laoyu
     * @date 2023/3/4
     */
    @PostMapping("/treeList")
    public R<List<MenuTreeVO>> treeList(@RequestBody @Validated MenuTreeQo menuTreeQo) {
        return DataResult.ok(menuService.treeList(menuTreeQo));
    }

    /**
     * 详情
     *
     * @param id id
     * @return game.server.manager.uc.vo.MenuResultVO
     * @author laoyu
     * @date 2023/3/4
     */
    @GetMapping("/info/{id}")
    public R<MenuResultVO> info(@PathVariable("id") @NotNull(message = "id") String id) {
        return DataResult.ok(this.menuService.info(id));
    }

    /**
     * 添加菜单
     *
     * @param dto dto
     * @return game.server.manager.uc.vo.MenuResultVO
     * @author laoyu
     * @date 2023/3/4
     */
    @PostMapping("/add}")
    public R<String> insert(@RequestBody @Validated MenuAddDto dto) {
        return DataResult.ok(menuService.insert(dto));
    }

    /**
     * 更新菜单
     *
     * @param dto dto
     * @return game.server.manager.uc.vo.MenuResultVO
     * @author laoyu
     * @date 2023/3/4
     */
    @PostMapping("/update}")
    public R<Boolean> update(@RequestBody @Validated MenuModifyDto dto) {
        return DataResult.ok(menuService.update(dto));
    }

    /**
     * 删除菜单
     *
     * @param id id
     * @return java.lang.Boolean
     * @author laoyu
     * @date 2023/3/4
     */
    @GetMapping("/delete/{id}")
    public R<Boolean> delete(@NotNull(message = "id") String id) {
        return DataResult.ok(menuService.delete(id));
    }
}
