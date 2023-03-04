package game.server.manager.uc.service.impl;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import game.server.manager.common.exception.BizException;
import game.server.manager.uc.dto.MenuAddDto;
import game.server.manager.uc.dto.MenuModifyDto;
import game.server.manager.uc.entity.MenuItem;
import game.server.manager.uc.mapstruct.MenuMapstruct;
import game.server.manager.uc.qo.MenuTreeQo;
import game.server.manager.uc.service.MenuItemService;
import game.server.manager.uc.mapper.MenuItemMapper;
import game.server.manager.uc.vo.MenuResultVO;
import game.server.manager.uc.vo.MenuTreeVO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author yuzhanfeng
 * @description 针对表【menu_item】的数据库操作Service实现
 * @createDate 2023-03-04 20:59:05
 */
@Service
public class MenuItemServiceImpl extends ServiceImpl<MenuItemMapper, MenuItem>
        implements MenuItemService {

    @Override
    public List<MenuTreeVO> treeList(MenuTreeQo menuTreeQo) {
        LambdaQueryWrapper<MenuItem> wrapper = Wrappers.lambdaQuery();
        String pid = menuTreeQo.getPid();
        if (CharSequenceUtil.isNotEmpty(pid)) {
            MenuItem parent = getById(pid);
            if (parent == null) {
                throw new BizException("菜单不存在");
            }
            wrapper.likeRight(MenuItem::getMenuPath, parent.getMenuPath());
        }
        if (CharSequenceUtil.isNotEmpty(menuTreeQo.getMenuCode())) {
            wrapper.eq(MenuItem::getMenuCode, menuTreeQo.getMenuCode());
        }
        if (CharSequenceUtil.isNotEmpty(menuTreeQo.getMenuName())) {
            wrapper.eq(MenuItem::getMenuName, menuTreeQo.getMenuName());
        }
        if (Objects.nonNull(menuTreeQo.getAuthFlag())) {
            wrapper.eq(MenuItem::getAuthFlag, menuTreeQo.getAuthFlag());
        }
        if (Objects.nonNull(menuTreeQo.getEmbeddedType())) {
            wrapper.eq(MenuItem::getEmbeddedType, menuTreeQo.getEmbeddedType());
        }
        List<MenuItem> entityList = list(wrapper);
        if (entityList.isEmpty()) {
            return Collections.emptyList();
        }
        List<MenuTreeVO> voList = MenuMapstruct.INSTANCE.entityListToTreeVoiList(entityList);
        voList.forEach(vo -> vo.setChildren(new ArrayList<>()));
        Map<String, MenuTreeVO> menuMap = voList.stream().collect(Collectors.toMap(MenuResultVO::getId, vo -> vo));
        voList.forEach(vo -> {
            if (menuMap.containsKey(vo.getPid())) {
                MenuTreeVO menuTreeVO = menuMap.get(vo.getPid());
                menuTreeVO.getChildren().add(vo);
            }
        });
        if (CharSequenceUtil.isEmpty(pid)) {
            return voList.stream().filter(vo -> CharSequenceUtil.isEmpty(vo.getPid())).collect(Collectors.toList());
        }
        return voList.stream().filter(vo -> Objects.equals(pid, vo.getPid())).collect(Collectors.toList());

    }

    @Override
    public MenuResultVO info(String id) {
        return MenuMapstruct.INSTANCE.entityToVo(getById(id));

    }

    @Override
    public String insert(MenuAddDto dto) {
        String menuPath;
        if (CharSequenceUtil.isNotEmpty(dto.getPid())) {
            MenuItem parent = getById(dto.getPid());
            if (parent == null) {
                throw new BizException("父菜单不存在");
            }
            menuPath = parent.getMenuPath();
        } else {
            menuPath = "/";
        }
        long count = countByCode(dto.getMenuCode());
        if (count > 0) {
            throw new BizException("菜单编号" + "[" + dto.getMenuCode() + "]已存在。");
        }
        String id = IdUtil.getSnowflakeNextIdStr();
        MenuItem entity = MenuMapstruct.INSTANCE.addDtoToEntity(dto);
        entity.setMenuPath(menuPath.concat(id).concat("/"));
        save(entity);
        return entity.getId();

    }

    @Override
    public boolean update(MenuModifyDto dto) {
        MenuItem oldEntity = getById(dto.getId());
        if (Objects.isNull(oldEntity)) {
            throw new BizException("要修改的菜单不存在");
        }
        long count = countByCodeAndIdNot(dto.getMenuCode(), oldEntity.getId());
        if (count > 0) {
            throw new BizException("菜单编号" + "[" + dto.getMenuCode() + "]已存在。");
        }
        MenuItem entity = MenuMapstruct.INSTANCE.updateDtoToEntity(dto);
        boolean result = updateById(entity);
        if (Objects.equals(dto.getDisabledFlag(), 1)) {
            disableByMenuPathPrefix(oldEntity.getMenuPath() + "%");
        }
        return result;

    }

    private void disableByMenuPathPrefix(String prefix) {
        LambdaUpdateWrapper<MenuItem> wrapper = Wrappers.lambdaUpdate();
        wrapper.likeRight(MenuItem::getPid,prefix);
        update(MenuItem.builder().disabledFlag(1).build(), wrapper);
    }

    @Override
    public Boolean delete(String id) {
        MenuItem oldEntity = getById(id);
        if (Objects.isNull(oldEntity)) {
            throw new BizException("要删除的菜单不存在");
        }
        List<MenuItem> children = getByPid(id);
        if (!children.isEmpty()) {
            throw new BizException("存在子菜单不能删除");
        }
        return removeById(id);
    }

    private List<MenuItem> getByPid(String pid) {
        LambdaQueryWrapper<MenuItem> wrapper = Wrappers.lambdaQuery();
        wrapper.likeRight(MenuItem::getPid, pid);
        return list(wrapper);
    }

    private long countByCode(String menuCode) {
        LambdaQueryWrapper<MenuItem> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(MenuItem::getMenuCode, menuCode);
        return count(wrapper);
    }

    private long countByCodeAndIdNot(String menuCode, String id) {
        LambdaQueryWrapper<MenuItem> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(MenuItem::getMenuCode, menuCode);
        wrapper.ne(MenuItem::getId, id);
        return count(wrapper);
    }
}




