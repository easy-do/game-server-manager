package plus.easydo.uc.service.impl;


import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.lang.tree.parser.NodeParser;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.IdUtil;
import com.alicp.jetcache.anno.CacheInvalidate;
import com.alicp.jetcache.anno.CacheRefresh;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Maps;
import plus.easydo.auth.AuthorizationUtil;
import plus.easydo.common.dto.ChangeStatusDto;
import plus.easydo.common.enums.ResourceTypeEnum;
import plus.easydo.common.enums.StatusEnum;
import plus.easydo.common.exception.BizException;
import plus.easydo.uc.dto.AuthRoleMenuDto;
import plus.easydo.uc.entity.SysRoleResource;
import plus.easydo.uc.qo.SysResourceQo;
import plus.easydo.uc.service.SysRoleResourceService;
import plus.easydo.uc.service.SysUserRoleService;
import plus.easydo.uc.vo.FunctionAuthVo;
import plus.easydo.uc.vo.UserResourceVo;
import plus.easydo.web.base.BaseServiceImpl;
import plus.easydo.uc.dto.SysResourceDto;
import plus.easydo.uc.entity.SysResource;
import plus.easydo.uc.vo.SysResourceVo;
import plus.easydo.uc.mapstruct.SysResourceMapstruct;
import plus.easydo.uc.mapper.SysResourceMapper;
import plus.easydo.uc.service.SysResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
        mpBaseQo.initInstance(SysResource.class);
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
    @CacheInvalidate(name = "SysResourceService.roleResource")
    public boolean add(SysResourceDto sysResourceDto) {
        checkResourceCode(sysResourceDto.getResourceCode());
        SysResource entity = SysResourceMapstruct.INSTANCE.dtoToEntity(sysResourceDto);
        String id = IdUtil.getSnowflakeNextIdStr();
        String resourcePath;
        if (Objects.nonNull(sysResourceDto.getParentId()) && !Objects.equals(sysResourceDto.getParentId(),0L)) {
            SysResource parent = getById(sysResourceDto.getParentId());
            if (Objects.isNull(parent)) {
                throw new BizException("父资源不存在");
            }
            resourcePath = parent.getResourcePath();
        } else {
            resourcePath = "/" + id +"/";
        }
        entity.setResourcePath(resourcePath);
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
    @CacheInvalidate(name = "SysResourceService.roleResource")
    public boolean edit(SysResourceDto sysResourceDto) {
        if(Objects.equals(sysResourceDto.getId(),sysResourceDto.getParentId())){
            throw new BizException("不能选择自己作为父节点");
        }
        SysResource oldResource = checkResource(sysResourceDto.getId());
        if(!CharSequenceUtil.equals(sysResourceDto.getResourceCode(),oldResource.getResourceCode())){
            checkResourceCode(sysResourceDto.getId(), sysResourceDto.getResourceCode());
        }
        SysResource entity = SysResourceMapstruct.INSTANCE.dtoToEntity(sysResourceDto);
        boolean isUpdateParent = !Objects.equals(oldResource.getParentId(), sysResourceDto.getParentId());
        if (isUpdateParent) {
            if(Objects.equals(sysResourceDto.getParentId(),0L)){
                entity.setResourcePath("/" + oldResource.getId() +"/");
            }else {
                SysResource parent = getById(sysResourceDto.getParentId());
                if (Objects.isNull(parent)) {
                    throw new BizException("选择的资源节点不存在");
                }
                entity.setResourcePath(parent.getResourcePath() + entity.getId() + "/");
            }
        }
        boolean result = updateById(entity);
        if (result && isUpdateParent) {
            //递归更新所有子节点的path
            updateResourcePathByParent(entity);
        }
        return result;
    }

    /**
     * 批量删除系统资源
     *
     * @param id 需要删除的系统资源ID
     * @return 结果
     */
    @Override
    @CacheInvalidate(name = "SysResourceService.roleResource")
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
    @CacheInvalidate(name = "SysResourceService.roleResource")
    public boolean changeStatus(ChangeStatusDto changeStatusDto) {
        Long id = changeStatusDto.getId();
        Integer status = changeStatusDto.getStatus();
        Long updateUserId = changeStatusDto.getUpdateUserId();
        SysResource entity = SysResource.builder().id(id).status(status).updateBy(updateUserId).build();
        return updateById(entity);
    }

    @Override
    public List<SysResourceVo> treeList(SysResourceQo sysResourceQo) {
        LambdaQueryWrapper<SysResource> wrapper = sysResourceQo.buildSearchWrapper();
        Map<String, Object> param = sysResourceQo.getSearchParam();
        if(Objects.nonNull(param) && Objects.nonNull(param.get("parentId"))){
            wrapper.likeRight(SysResource::getResourcePath,"/"+ param.get("parentId"));
        }
        List<SysResource> resourceList = list(wrapper);
        if(resourceList.isEmpty()){
            return Collections.emptyList();
        }
        List<SysResourceVo> voList = SysResourceMapstruct.INSTANCE.entityToVo(resourceList);
        voList.stream().forEach(vo -> vo.setChildren(new ArrayList()));
        Map<Long, SysResourceVo> resourceMap = voList.parallelStream().collect(Collectors.toMap(SysResourceVo::getId, sysResourceVo -> sysResourceVo));
        voList.forEach(vo -> {
            if (resourceMap.containsKey(vo.getParentId()))
                resourceMap.get(vo.getParentId()).getChildren().add(vo);
        });
        Long min = voList.stream().min((a, b) -> (int) (a.getParentId() - b.getParentId())).get().getParentId();
        return voList.stream().filter(vo -> Objects.equals(min,vo.getParentId())).collect(Collectors.toList());
    }

    /**
     * 获取所有资源下拉树(带详情)
     *
     * @return java.util.List<cn.hutool.core.lang.tree.Tree < java.lang.Long>>
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
     * @return java.util.List<cn.hutool.core.lang.tree.Tree < java.lang.Long>>
     * @author laoyu
     * @date 2022/9/20
     */
    @Override
    public List<Tree<Long>> resourceTree() {
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
    @CacheInvalidate(name = "SysResourceService.roleResource")
    public boolean authRoleResource(AuthRoleMenuDto authRoleMenuDto) {
        ArrayList<SysRoleResource> entityList = new ArrayList<>();
        Long roleId = authRoleMenuDto.getRoleId();
        List<Long> resourceIds = authRoleMenuDto.getResourceIds();
        sysRoleResourceService.removeByRoleId(roleId);
        resourceIds.forEach(menuId -> entityList.add(SysRoleResource.builder().roleId(roleId).resourceId(menuId).build()));
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
    @Cached(name = "SysResourceService.roleResource", expire = 3200, cacheType = CacheType.BOTH)
    @CacheRefresh(refresh = 60)
    public List<UserResourceVo> roleResource(List<Long> roleIds) {
        List<SysResource> resourceList = getRoleResourceList(roleIds);
        return buildUserResource(resourceList);
    }

    private List<UserResourceVo> buildUserResource(List<SysResource> resourceList) {
        if(resourceList.isEmpty()){
            return Collections.emptyList();
        }
        List<UserResourceVo> rootList = new ArrayList<>();
        //拿到所有跟节点菜单
        resourceList.parallelStream().forEach(resource->{
            if(resource.getParentId().equals(0L)){
                UserResourceVo userResourcevo = resourceToUserResourceVo(resource);
                findChildren(userResourcevo,resource.getId(),resourceList);
                rootList.add(userResourcevo);
            }
        });
        return rootList.parallelStream().sorted(Comparator.comparing(UserResourceVo::getOrderNumber)).toList();
    }

    private void findChildren(UserResourceVo resourcevo, Long parentId, List<SysResource> resourceList) {
        List<UserResourceVo> childrenList = new ArrayList<>();
        List<FunctionAuthVo> functionAuthList = new ArrayList<>();
        resourceList.parallelStream().forEach(resource->{
            if(resource.getParentId().equals(parentId)){
                if(resource.getResourceType().equals(ResourceTypeEnum.MENU.getType())){
                    UserResourceVo userResourcevo = resourceToUserResourceVo(resource);
                    findChildren(userResourcevo,resource.getId(),resourceList);
                    childrenList.add(userResourcevo);
                }else {
                    functionAuthList.add(FunctionAuthVo.builder()
                                    .name(resource.getResourceName())
                                    .key(resource.getResourceCode())
                                    .orderNumber(resource.getOrderNumber())
                            .build());
                }
            }
        });
        resourcevo.setChildren(childrenList.parallelStream().sorted(Comparator.comparing(UserResourceVo::getOrderNumber)).toList());
        resourcevo.setFunctionAuths(functionAuthList.parallelStream().sorted(Comparator.comparing(FunctionAuthVo::getOrderNumber)).toList());
    }

    private static UserResourceVo resourceToUserResourceVo(SysResource resource) {
        UserResourceVo userResourcevo = BeanUtil.copyProperties(resource, UserResourceVo.class);
        userResourcevo.setName(resource.getResourceName());
        userResourcevo.setKey(resource.getResourceCode());
        return userResourcevo;
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
    public List<UserResourceVo> userResource() {
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
    public List<UserResourceVo> userResource(Long userId) {
        List<Long> roleIds = sysUserRoleService.getRoleIdListByUserId(userId);
        if (roleIds.isEmpty()) {
            return ListUtil.empty();
        }
        return roleResource(roleIds);
    }

    @Override
    public Map<String, List<String>> userResourceAction(Long userId) {
        List<Long> roleIds = sysUserRoleService.getRoleIdListByUserId(userId);
        if (roleIds.isEmpty()) {
            return Maps.newHashMap();
        }
        List<SysResource> roleResourceList = getRoleResourceList(roleIds);
        if (roleResourceList.isEmpty()) {
            return Maps.newHashMap();
        }
        return buildResourceAction(roleResourceList);
    }

    private Map<String, List<String>> buildResourceAction(List<SysResource> resourceList) {
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        NodeParser<SysResource, Long> nodeParser = (sysResource, treeNode) -> {
            treeNode.setId(sysResource.getId());
            treeNode.setParentId(sysResource.getParentId());
            treeNode.setName(sysResource.getResourceName());
            treeNode.setWeight(sysResource.getOrderNumber());
            treeNode.putExtra("type", sysResource.getResourceType());
            treeNode.putExtra("resourceCode", sysResource.getResourceCode());
        };
        Long min = resourceList.stream().min((a, b) -> (int) (a.getParentId() - b.getParentId())).get().getParentId();
        List<Tree<Long>> treeList = TreeUtil.build(resourceList, min, treeNodeConfig, nodeParser);
        Map<String, List<String>> actionMap = Maps.newHashMap();
        buildResourceAction(actionMap, treeList);
        return actionMap;
    }

    private Map<String, List<String>> buildResourceAction(Map<String, List<String>> actionMap, List<Tree<Long>> treeList) {
        treeList.forEach(tree -> {
            String type = (String) tree.get("type");
            if (type.equals(ResourceTypeEnum.MENU.getType()) && isChildrenMenu(tree)) {
                //是菜单 并且子节点存在菜单 继续向下遍历
                buildResourceAction(actionMap, tree.getChildren());
            } else if (type.equals(ResourceTypeEnum.MENU.getType()) && !isChildrenMenu(tree)) {
                //是菜单 但子节点已经不存在菜单 直接组装
                String perms = (String) tree.get("resourceCode");
                List<Tree<Long>> children = tree.getChildren();
                if (Objects.nonNull(children)) {
//                    List<Tree<Long>> actionsResource = children.stream().filter(longTree -> ResourceTypeEnum.ACTION.getType().equals(longTree.get("type").toString())).toList();
//                    if (!actionsResource.isEmpty()) {
//                        List<String> actions = actionsResource.stream().map(tree1 -> tree1.get("resourceCode").toString()).toList();
//                        actionMap.put(perms, actions);
//                    }
                }
            }
        });
        return actionMap;
    }

    private boolean isChildrenMenu(Tree<Long> tree) {
        List<Tree<Long>> childrenList = tree.getChildren();
        if (Objects.isNull(childrenList)) {
            return false;
        }
        for (Tree<Long> children : childrenList) {
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
     * @return java.util.List<entity.plus.easydo.uc.SysResource>
     * @author laoyu
     * @date 2022/9/20
     */
    private List<SysResource> getRoleResourceList(List<Long> roleIds) {
        LambdaQueryWrapper<SysRoleResource> roleMenuWrapper = Wrappers.lambdaQuery();
        roleMenuWrapper.in(SysRoleResource::getRoleId, roleIds);
        List<SysRoleResource> roleResources = sysRoleResourceService.list(roleMenuWrapper);
        if (roleResources.isEmpty()) {
            return ListUtil.empty();
        }
        List<Long> resourceIds = roleResources.stream().map(SysRoleResource::getResourceId).toList();
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
        NodeParser<SysResourceVo, Long> nodeParser = (sysResourceVo, treeNode) -> {
            treeNode.setId(sysResourceVo.getId());
            treeNode.setParentId(sysResourceVo.getParentId());
            treeNode.setName(sysResourceVo.getResourceName());
            treeNode.setWeight(sysResourceVo.getOrderNumber());
            treeNode.putExtra("details", sysResourceVo);
            treeNode.putExtra("key", sysResourceVo.getUrl());
            treeNode.putExtra("type", sysResourceVo.getResourceType());
            treeNode.putExtra("resourceCode", sysResourceVo.getResourceCode());
            treeNode.putExtra("visible", StatusEnum.DISABLE.getCode().equals(sysResourceVo.getStatus()));
        };
        Long min = voList.stream().min((a, b) -> (int) (a.getParentId() - b.getParentId())).get().getParentId();
        return TreeUtil.build(voList, min, treeNodeConfig, nodeParser);
    }

    private void checkResourceCode(String resourceCode) {
        LambdaQueryWrapper<SysResource> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysResource::getResourceCode, resourceCode);
        if (!list(wrapper).isEmpty()) {
            throw new BizException("资源编码[" + resourceCode + "]已存在");
        }
    }

    private void checkResourceCode(Long resourceId, String resourceCode) {
        LambdaQueryWrapper<SysResource> wrapper = Wrappers.lambdaQuery();
        wrapper.ne(SysResource::getId, resourceId);
        wrapper.eq(SysResource::getResourceCode, resourceCode);
        if (!list(wrapper).isEmpty()) {
            throw new BizException("资源编码[" + resourceCode + "]已存在");
        }
    }

    private SysResource checkResource(Long id) {
        SysResource entity = getById(id);
        if (Objects.isNull(entity)) {
            throw new BizException("资源不存在");
        }
        return entity;
    }


    private void updateResourcePathByParent(SysResource parent) {
        Long parentId = parent.getId();
        List<SysResource> children = getByParentId(parentId);
        List<SysResource> entityList = new ArrayList<>();
        if (!children.isEmpty()) {
            String parentResourcePath = parent.getResourcePath();
            children.parallelStream().forEach(sysResource -> {
                sysResource.setResourcePath(parentResourcePath + sysResource.getId() + "/");
                entityList.add(sysResource);
                updateResourcePathByParent(sysResource);
            });
        }
        updateBatchById(entityList);
    }


    private List<SysResource> getByParentId(Long parentId) {
        LambdaQueryWrapper<SysResource> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysResource::getParentId, parentId);
        return list(wrapper);
    }


}
