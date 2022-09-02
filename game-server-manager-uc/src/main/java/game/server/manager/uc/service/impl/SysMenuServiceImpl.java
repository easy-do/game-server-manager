package game.server.manager.uc.service.impl;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.lang.tree.parser.NodeParser;
import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import game.server.manager.common.dto.ChangeStatusDto;
import game.server.manager.common.enums.StatusEnum;
import game.server.manager.common.vo.SysMenuVo;
import game.server.manager.mybatis.plus.qo.MpBaseQo;
import game.server.manager.uc.dto.AuthRoleMenuDto;
import game.server.manager.uc.entity.SysMenu;
import game.server.manager.uc.entity.SysRoleMenu;
import game.server.manager.uc.mapper.SysMenuMapper;
import game.server.manager.uc.mapstruct.SysMenuMapstruct;
import game.server.manager.uc.service.SysMenuService;
import game.server.manager.uc.service.SysRoleMenuService;
import game.server.manager.uc.service.SysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author yuzhanfeng
* @description 针对表【sys_menu(菜单权限表)】的数据库操作Service实现
* @createDate 2022-07-18 19:57:36
*/
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu>
    implements SysMenuService {

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Autowired
    private SysRoleMenuService roleMenuService;

    @Override
    @Cacheable(value = "sysMenu",key = "'treeSelect'")
    public List<Tree<Long>> treeSelect() {
        MpBaseQo mpBaseQo = MpBaseQo.builder().columns(Arrays.asList("menuId","menuName","parentId","status")).build();
        List<SysMenu> allList = list(mpBaseQo.buildSearchWrapper());
        return buildSelectMenuTree(allList);
    }

    @Override
    public List<Tree<Long>> roleTreeSelect(Long roleId) {
        return getRoleMenuSelectTree(List.of(roleId));
    }


    @Override
    @Cacheable(value = "userMenu", key = "#userId")
    public List<Tree<Long>> userMenu(Long userId) {
        List<Long> roleIds = sysUserRoleService.getRoleIdListByUserId(userId);
        if(roleIds.isEmpty()){
            return ListUtil.empty();
        }
        return buildUserMenu(roleIds);
    }

    @Override
    @Cacheable(value = "userPermission", key = "#userId")
    public List<String> userPermissionList(Long userId) {
        List<Long> roleIds = sysUserRoleService.getRoleIdListByUserId(userId);
        if(roleIds.isEmpty()){
            return ListUtil.empty();
        }
        List<SysMenu> menuList = getRoleMenuList(roleIds);
        return menuList.stream().map(SysMenu::getPerms).collect(Collectors.toList());
    }

    @Override
    public List<Tree<Long>> treeInfoList(MpBaseQo mpBaseQo) {
        QueryWrapper<SysMenu> wrapper = mpBaseQo.buildSearchWrapper();
        List<SysMenu> allList = list(wrapper);
        return buildSelectMenuTree(allList);
    }


    private List<SysMenu> getRoleMenuList(List<Long> roleIds){
        LambdaQueryWrapper<SysRoleMenu> roleMenuWrapper = Wrappers.lambdaQuery();
        roleMenuWrapper.in(SysRoleMenu::getRoleId,roleIds);
        List<SysRoleMenu> roleMenuList = roleMenuService.list(roleMenuWrapper);
        if(roleMenuList.isEmpty()){
            return ListUtil.empty();
        }
        List<Long> menuIds = roleMenuList.stream().map(SysRoleMenu::getMenuId).toList();
        LambdaQueryWrapper<SysMenu> menuWrapper = Wrappers.lambdaQuery();
        menuWrapper.select(SysMenu::getMenuId,SysMenu::getMenuName,SysMenu::getParentId,SysMenu::getIcon,SysMenu::getPath,SysMenu::getQuery,SysMenu::getMenuType,SysMenu::getStatus,SysMenu::getPerms);
        menuWrapper.in(SysMenu::getMenuId,menuIds);
        menuWrapper.in(SysMenu::getStatus,0);
        return list(menuWrapper);
    }
    /**
     * 获取角色菜单选择树
     *
     * @param roleIds roleIds
     * @return java.util.List<cn.hutool.core.lang.tree.Tree<java.lang.Long>>
     * @author laoyu
     * @date 2022/7/19
     */
    private List<Tree<Long>> getRoleMenuSelectTree(List<Long> roleIds) {
        List<SysMenu> menuList = getRoleMenuList(roleIds);
        return buildSelectMenuTree(menuList);
    }



    /**
     * 获取角色菜单选择树
     *
     * @param roleIds roleIds
     * @return java.util.List<cn.hutool.core.lang.tree.Tree<java.lang.Long>>
     * @author laoyu
     * @date 2022/7/19
     */
    private List<Tree<Long>> buildUserMenu(List<Long> roleIds) {
        List<SysMenu> menuList = getRoleMenuList(roleIds);
        return buildUserMenuTree(menuList);
    }

    /**
     * 构建下拉菜单树形列表
     *
     * @param sysMenuList sysMenuList
     * @return java.util.List<cn.hutool.core.lang.tree.Tree<java.lang.Long>>
     * @author laoyu
     * @date 2022/7/19
     */
    List<Tree<Long>> buildUserMenuTree(List<SysMenu> sysMenuList){
        if(sysMenuList.isEmpty()){
            return ListUtil.empty();
        }
        List<SysMenuVo> voList = SysMenuMapstruct.INSTANCE.entityListToVoList(sysMenuList);
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        treeNodeConfig.setIdKey("itemKey");
        treeNodeConfig.setNameKey("text");
        treeNodeConfig.setChildrenKey("items");
        NodeParser<SysMenuVo, Long> nodeParser = (sysMenuVo, treeNode) -> {
            treeNode.setId(sysMenuVo.getMenuId());
            treeNode.setParentId(sysMenuVo.getParentId());
            treeNode.setName(sysMenuVo.getMenuName());
            treeNode.setWeight(sysMenuVo.getOrderNum());
            treeNode.putExtra("icon",sysMenuVo.getIcon());
            String query = sysMenuVo.getQuery();
            treeNode.putExtra("link",sysMenuVo.getPath() + (CharSequenceUtil.isNotEmpty(query)?query:""));
            treeNode.put("menuType",sysMenuVo.getMenuType());
            treeNode.putExtra("disabled", sysMenuVo.getStatus().equals(StatusEnum.DISABLE.getCode()));
        };
        Long min = voList.stream().min((a, b) -> (int) (a.getParentId() - b.getParentId())).get().getParentId();
        List<Tree<Long>> treeList = TreeUtil.build(voList, min, treeNodeConfig, nodeParser);
        return treeList;
    }


    /**
     * 构建下拉菜单树形列表
     *
     * @param sysMenuList sysMenuList
     * @return java.util.List<cn.hutool.core.lang.tree.Tree<java.lang.Long>>
     * @author laoyu
     * @date 2022/7/19
     */
    List<Tree<Long>> buildSelectMenuTree(List<SysMenu> sysMenuList){
        if(sysMenuList.isEmpty()){
            return ListUtil.empty();
        }
        List<SysMenuVo> voList = SysMenuMapstruct.INSTANCE.entityListToVoList(sysMenuList);
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        treeNodeConfig.setIdKey("value");
        treeNodeConfig.setNameKey("label");
        treeNodeConfig.setChildrenKey("children");
        NodeParser<SysMenuVo, Long> nodeParser = (sysMenuVo, treeNode) -> {
            treeNode.setId(sysMenuVo.getMenuId());
            treeNode.setParentId(sysMenuVo.getParentId());
            treeNode.setName(sysMenuVo.getMenuName());
            treeNode.setWeight(sysMenuVo.getOrderNum());
            treeNode.putExtra("details",sysMenuVo);
            treeNode.putExtra("key",sysMenuVo.getMenuId());
            treeNode.putExtra("disabled", sysMenuVo.getStatus().equals(StatusEnum.DISABLE.getCode()));
        };
        Long min = voList.stream().min((a, b) -> (int) (a.getParentId() - b.getParentId())).get().getParentId();
        List<Tree<Long>> treeList = TreeUtil.build(voList, min, treeNodeConfig, nodeParser);
        return treeList;
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "userMenu",allEntries = true),
            @CacheEvict(value = "userPermission",allEntries = true)
    })
    public boolean authRoleMenu(@RequestBody @Validated AuthRoleMenuDto authRoleMenuDto) {
        ArrayList<SysRoleMenu> entityList = new ArrayList<>();
        Long roleId = authRoleMenuDto.getRoleId();
        List<Long> menuIds = authRoleMenuDto.getMenuIds();
        roleMenuService.removeByRoleId(roleId);
        menuIds.forEach(menuId-> entityList.add(SysRoleMenu.builder().roleId(roleId).menuId(menuId).build()));
        return roleMenuService.saveBatch(entityList);
    }

    @Override
    public List<Long> roleMenuIds(Long roleId) {
        LambdaQueryWrapper<SysRoleMenu> roleMenuWrapper = Wrappers.lambdaQuery();
        roleMenuWrapper.eq(SysRoleMenu::getRoleId,roleId);
        List<SysRoleMenu> roleMenuList = roleMenuService.list(roleMenuWrapper);
        if(roleMenuList.isEmpty()){
            return ListUtil.empty();
        }
        return roleMenuList.stream().map(SysRoleMenu::getMenuId).toList();
    }

    @Override
    @CacheEvict(value = "sysMenu",allEntries = true)
    public boolean changeStatus(ChangeStatusDto changeStatusDto) {
        Long menuId = changeStatusDto.getId();
        Integer status = changeStatusDto.getStatus();
        Long updateUserId = changeStatusDto.getUpdateUserId();
        SysMenu entity = SysMenu.builder().menuId(menuId).status(status).updateBy(updateUserId).build();
        return updateById(entity);
    }

}




