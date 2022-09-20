package game.server.manager.uc.service.impl;


import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.lang.tree.parser.NodeParser;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Maps;
import game.server.manager.auth.AuthorizationUtil;
import game.server.manager.common.dto.ChangeStatusDto;
import game.server.manager.common.enums.ResourceTypeEnum;
import game.server.manager.common.enums.StatusEnum;
import game.server.manager.uc.dto.AuthRoleMenuDto;
import game.server.manager.uc.entity.SysMenu;
import game.server.manager.uc.entity.SysRoleResource;
import game.server.manager.uc.service.SysRoleResourceService;
import game.server.manager.uc.service.SysUserRoleService;
import game.server.manager.web.base.BaseServiceImpl;
import game.server.manager.uc.dto.SysResourceDto;
import game.server.manager.uc.entity.SysResource;
import game.server.manager.uc.qo.SysResourceQo;
import game.server.manager.uc.vo.SysResourceVo;
import game.server.manager.uc.mapstruct.SysResourceMapstruct;
import game.server.manager.uc.mapper.SysResourceMapper;
import game.server.manager.uc.service.SysResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * 系统资源Service层
 *
 * @author yuzhanfeng
 * @date 2022-09-19 22:43:39
 */
@Service
public class SysResourceServiceImpl extends BaseServiceImpl<SysResource, SysResourceQo, SysResourceVo, SysResourceDto, SysResourceMapper> implements SysResourceService {


    private static final Long ANONYMOUS_ID = 3L;

    @Autowired
    private SysRoleResourceService sysRoleResourceService;

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Override
    public void listSelect(LambdaQueryWrapper<SysResource> wrapper) {

    }

    @Override
    public void pageSelect(LambdaQueryWrapper<SysResource> wrapper) {

    }


    /**
     * 获取所有系统资源列表
     *
     * @return 系统资源
     */
    @Override
    public List<SysResourceVo> voList() {
        LambdaQueryWrapper<SysResource> wrapper = Wrappers.lambdaQuery();
        wrapper.orderByDesc(SysResource::getCreateTime);
        listSelect(wrapper);
        return SysResourceMapstruct.INSTANCE.entityToVo(list(wrapper));
    }


    /**
     * 分页条件查询系统资源列表
     *
     * @param mpBaseQo 查询条件封装
     * @return 系统资源
     */
    @Override
    public IPage<SysResourceVo> page(SysResourceQo mpBaseQo) {
        LambdaQueryWrapper<SysResource> wrapper = getWrapper();
        wrapper.orderByDesc(SysResource::getCreateTime);
        pageSelect(wrapper);
        return page(mpBaseQo.getPage(), mpBaseQo.getWrapper()).convert(SysResourceMapstruct.INSTANCE::entityToVo);
    }


    /**
     * 查询系统资源
     *
     * @param id 系统资源ID
     * @return 系统资源
     */
    @Override
    public SysResourceVo info(Serializable id) {
        return SysResourceMapstruct.INSTANCE.entityToVo(getById(id));
    }


    /**
     * 新增系统资源
     *
     * @param sysResourceDto 数据传输对象
     * @return 结果
     */
    @Override
    public boolean add(SysResourceDto sysResourceDto) {
        SysResource entity = SysResourceMapstruct.INSTANCE.dtoToEntity(sysResourceDto);
        entity.setCreateBy(getUserId());
        return save(entity);
    }

    /**
     * 修改系统资源
     *
     * @param sysResourceDto 数据传输对象
     * @return 结果
     */
    @Override
    public boolean edit(SysResourceDto sysResourceDto) {
        SysResource entity = SysResourceMapstruct.INSTANCE.dtoToEntity(sysResourceDto);
        return updateById(entity);
    }

    /**
     * 批量删除系统资源
     *
     * @param id 需要删除的系统资源ID
     * @return 结果
     */
    @Override
    public boolean delete(Serializable id) {
        return removeById(id);
    }


    /**
     * 变更状态
     *
     * @param changeStatusDto changeStatusDto
     * @return boolean
     * @author laoyu
     * @date 2022/9/20
     */
    @Override
    public boolean changeStatus(ChangeStatusDto changeStatusDto) {
        Long id = changeStatusDto.getId();
        Integer status = changeStatusDto.getStatus();
        Long updateUserId = changeStatusDto.getUpdateUserId();
        SysResource entity = SysResource.builder().id(id).status(status).updateBy(updateUserId).build();
        return updateById(entity);
    }

    /**
     * 获取所有资源下拉树(带详情)
     *
     * @return java.util.List<cn.hutool.core.lang.tree.Tree<java.lang.Long>>
     * @author laoyu
     * @date 2022/9/20
     */
    @Override
    public List<Tree<Long>> resourceInfoTree() {
        LambdaQueryWrapper<SysResource> wrapper = Wrappers.lambdaQuery();
        wrapper.select(SysResource::getId, SysResource::getResourceName, SysResource::getParentId, SysResource::getStatus);
        wrapper.eq(SysResource::getStatus, StatusEnum.ENABLE);
        return buildResourceTree(list(wrapper));
    }

    /**
     * 获取所有资源下拉树选择
     *
     * @return java.util.List<cn.hutool.core.lang.tree.Tree<java.lang.Long>>
     * @author laoyu
     * @date 2022/9/20
     */
    @Override
    public List<Tree<Long>> resourceTreeSelect() {
        LambdaQueryWrapper<SysResource> wrapper = Wrappers.lambdaQuery();
        wrapper.select(SysResource::getId, SysResource::getResourceName, SysResource::getParentId, SysResource::getStatus);
        wrapper.eq(SysResource::getStatus, StatusEnum.ENABLE);
        List<SysResource> allList = list(wrapper);
        return buildResourceTree(allList);
    }

    /**
     * 根据类型获取资源列表(带详情)
     *
     * @param type type
     * @return java.util.List<cn.hutool.core.lang.tree.Tree < java.lang.Long>>
     * @author laoyu
     * @date 2022/9/20
     */
    @Override
    public List<Tree<Long>> resourceInfoTreeByType(String type) {
        LambdaQueryWrapper<SysResource> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysResource::getDelFlag, 0);
        wrapper.eq(SysResource::getStatus, StatusEnum.ENABLE);
        wrapper.eq(SysResource::getResourceType, type);
        List<SysResource> allList = list(wrapper);
        return buildResourceTree(allList);
    }

    /**
     * 根据类型获取资源列表
     *
     * @return java.util.List<cn.hutool.core.lang.tree.Tree < java.lang.Long>>
     * @author laoyu
     * @date 2022/9/20
     */
    @Override
    public List<Tree<Long>> resourceTreeByType(String type) {
        LambdaQueryWrapper<SysResource> wrapper = Wrappers.lambdaQuery();
        wrapper.select(SysResource::getId, SysResource::getResourceName, SysResource::getParentId, SysResource::getStatus);
        wrapper.eq(SysResource::getStatus, StatusEnum.ENABLE);
        wrapper.eq(SysResource::getResourceType, type);
        List<SysResource> allList = list(wrapper);
        return buildResourceTree(allList);
    }


    /**
     * 授权角色资源
     *
     * @param authRoleMenuDto authRoleMenuDto
     * @return boolean
     * @author laoyu
     * @date 2022/9/20
     */
    @Override
    public boolean authRoleResource(AuthRoleMenuDto authRoleMenuDto) {
        ArrayList<SysRoleResource> entityList = new ArrayList<>();
        Long roleId = authRoleMenuDto.getRoleId();
        List<Long> menuIds = authRoleMenuDto.getMenuIds();
        sysRoleResourceService.removeByRoleId(roleId);
        menuIds.forEach(menuId -> entityList.add(SysRoleResource.builder().roleId(roleId).resourceId(menuId).build()));
        return sysRoleResourceService.saveBatch(entityList);
    }


    /**
     * 加载对应角色资源列表
     *
     * @param roleId roleId
     * @return java.util.List<cn.hutool.core.lang.tree.Tree < java.lang.Long>>
     * @author laoyu
     * @date 2022/9/20
     */
    @Override
    public List<Tree<Long>> roleResource(Long roleId) {
        List<SysResource> menuList = getRoleResourceList(List.of(roleId));
        return buildResourceTree(menuList);
    }

    /**
     * 加载对应角色资源列表
     *
     * @param roleIds roleIds
     * @return java.util.List<cn.hutool.core.lang.tree.Tree < java.lang.Long>>
     * @author laoyu
     * @date 2022/9/20
     */
    public List<Tree<Long>> roleResource(List<Long> roleIds) {
        List<SysResource> menuList = getRoleResourceList(roleIds);
        return buildResourceTree(menuList);
    }

    /**
     * 加载对应角色资源id集合
     *
     * @param roleId roleId
     * @return java.util.List<java.lang.Long>
     * @author laoyu
     * @date 2022/9/20
     */
    @Override
    public List<Long> roleResourceIds(Long roleId) {
        return getRoleResourceList(List.of(roleId)).stream().map(SysResource::getId).toList();
    }


    /**
     * 加载 用户/游客 资源列表
     *
     * @return java.util.List<cn.hutool.core.lang.tree.Tree < java.lang.Long>>
     * @author laoyu
     * @date 2022/9/20
     */
    @Override
    public List<Tree<Long>> userResource() {
        if (StpUtil.isLogin()) {
            List<Long> roleIds = sysUserRoleService.getRoleIdListByUserId(AuthorizationUtil.getUserId());
            if (roleIds.isEmpty()) {
                return ListUtil.empty();
            }
            return roleResource(roleIds);
        } else {

            return roleResource(List.of(ANONYMOUS_ID));
        }
    }

    @Override
    @Cacheable(value = "userPermission", key = "#userId")
    public List<String> userPermissionList(Long userId) {
        List<Long> roleIds = sysUserRoleService.getRoleIdListByUserId(userId);
        if(roleIds.isEmpty()){
            return ListUtil.empty();
        }
        List<SysResource> menuList = getRoleResourceList(roleIds);
        return menuList.stream().map(SysResource::getResourceCode).toList();
    }

    @Override
    public Map<String, List<String>> userResourceAction(Long userId) {
        List<Long> roleIds = sysUserRoleService.getRoleIdListByUserId(userId);
        if(roleIds.isEmpty()){
            return Maps.newHashMap();
        }
        List<SysResource> roleResourceList = getRoleResourceList(roleIds);
        if(roleResourceList.isEmpty()){
            return Maps.newHashMap();
        }
        return buildResourceAction(roleResourceList);
    }

    private Map<String,List<String>> buildResourceAction(List<SysResource> resourceList){
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        NodeParser<SysResource, Long> nodeParser = (sysResource, treeNode) -> {
            treeNode.setId(sysResource.getId());
            treeNode.setParentId(sysResource.getParentId());
            treeNode.setName(sysResource.getResourceName());
            treeNode.setWeight(sysResource.getOrderNumber());
            treeNode.putExtra("type",sysResource.getResourceType());
            treeNode.putExtra("perms",sysResource.getResourceCode());
        };
        Long min = resourceList.stream().min((a, b) -> (int) (a.getParentId() - b.getParentId())).get().getParentId();
        List<Tree<Long>> treeList = TreeUtil.build(resourceList, min, treeNodeConfig, nodeParser);
        Map<String,List<String>> actionMap = Maps.newHashMap();
        buildResourceAction(actionMap,treeList);
        return actionMap;
    }

    private Map<String,List<String>> buildResourceAction(Map<String,List<String>> actionMap,List<Tree<Long>> treeList){
        treeList.forEach(tree -> {
            String type = (String) tree.get("type");
            if(type.equals(ResourceTypeEnum.MENU.getType()) && isChildrenMenu(tree)){
                //是菜单 并且子节点存在菜单 继续向下遍历
                buildResourceAction(actionMap,tree.getChildren());
            }else if(type.equals(ResourceTypeEnum.MENU.getType()) && !isChildrenMenu(tree)){
                //是菜单 但子节点已经不存在菜单 直接组装
                String perms = (String) tree.get("perms");
                List<Tree<Long>> children = tree.getChildren();
                if(Objects.nonNull(children)){
                    List<String> actions = children.stream().map(tree1 -> (String)tree1.get("perms")).toList();
                    actionMap.put(perms,actions);
                }else {
                    actionMap.put(perms, List.of("*"));
                }
            }
        });
        return actionMap;
    }

    private boolean isChildrenMenu(Tree<Long> tree){
        List<Tree<Long>> childrenList = tree.getChildren();
        if(Objects.isNull(childrenList)){
            return false;
        }
        for (Tree<Long> children: childrenList) {
            if (children.get("type").equals(ResourceTypeEnum.MENU.getType())) {
                return true;
            }
        }
        return false;
    }


    /**
     * 获取角色关联的所有资源信息
     *
     * @param roleIds roleIds
     * @return java.util.List<game.server.manager.uc.entity.SysResource>
     * @author laoyu
     * @date 2022/9/20
     */
    private List<SysResource> getRoleResourceList(List<Long> roleIds) {
        LambdaQueryWrapper<SysRoleResource> roleMenuWrapper = Wrappers.lambdaQuery();
        roleMenuWrapper.in(SysRoleResource::getRoleId, roleIds);
        List<SysRoleResource> roleMenuList = sysRoleResourceService.list(roleMenuWrapper);
        if (roleMenuList.isEmpty()) {
            return ListUtil.empty();
        }
        List<Long> resourceIds = roleMenuList.stream().map(SysRoleResource::getResourceId).toList();
        LambdaQueryWrapper<SysResource> menuWrapper = Wrappers.lambdaQuery();
        menuWrapper.in(SysResource::getId, resourceIds);
        menuWrapper.in(SysResource::getStatus, StatusEnum.ENABLE);
        return list(menuWrapper);
    }


    /**
     * 构建资源树形列表
     *
     * @param sysResourceList sysResourceList
     * @return java.util.List<cn.hutool.core.lang.tree.Tree < java.lang.Long>>
     * @author laoyu
     * @date 2022/9/20
     */
    List<Tree<Long>> buildResourceTree(List<SysResource> sysResourceList) {
        if (sysResourceList.isEmpty()) {
            return ListUtil.empty();
        }
        List<SysResourceVo> voList = SysResourceMapstruct.INSTANCE.entityToVo(sysResourceList);
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        treeNodeConfig.setIdKey("value");
        treeNodeConfig.setNameKey("label");
        treeNodeConfig.setChildrenKey("children");
        NodeParser<SysResourceVo, Long> nodeParser = (sysResourceVo, treeNode) -> {
            treeNode.setId(sysResourceVo.getId());
            treeNode.setParentId(sysResourceVo.getParentId());
            treeNode.setName(sysResourceVo.getResourceName());
            treeNode.setWeight(sysResourceVo.getOrderNumber());
            treeNode.putExtra("details", sysResourceVo);
            treeNode.putExtra("key", sysResourceVo.getId());
            treeNode.putExtra("visible", sysResourceVo.getVisible());
        };
        Long min = voList.stream().min((a, b) -> (int) (a.getParentId() - b.getParentId())).get().getParentId();
        return TreeUtil.build(voList, min, treeNodeConfig, nodeParser);
    }
}
