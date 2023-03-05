package game.server.manager.uc.service;

import cn.hutool.core.lang.tree.Tree;
import game.server.manager.common.dto.ChangeStatusDto;
import game.server.manager.uc.dto.AuthRoleMenuDto;
import game.server.manager.web.base.BaseService;
import game.server.manager.uc.dto.SysResourceDto;
import game.server.manager.uc.qo.SysResourceQo;
import game.server.manager.uc.vo.SysResourceVo;
import game.server.manager.uc.entity.SysResource;

import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * 系统资源Service接口
 * 
 * @author yuzhanfeng
 * @date 2022-09-19 22:43:39
 */
public interface SysResourceService extends BaseService<SysResource, SysResourceQo, SysResourceVo, SysResourceDto>{

    /**
     * 更改资源状态
     *
     * @param changeStatusDto changeStatusDto
     * @return boolean
     * @author laoyu
     * @date 2022/9/20
     */
    boolean changeStatus(ChangeStatusDto changeStatusDto);


    /**
     * 树列表
     *
     * @return java.util.List<cn.hutool.core.lang.tree.Tree<java.lang.Long>>
     * @author laoyu
     * @date 2022/9/20
     */
    List<SysResourceVo> treeList(SysResourceQo sysResourceQo);

    /**
     * 获取所有资源下拉树(带详情)
     *
     * @return java.util.List<cn.hutool.core.lang.tree.Tree<java.lang.Long>>
     * @author laoyu
     * @date 2022/9/20
     */
    List<Tree<Long>> resourceInfoTree();

    /**
     * 获取所有资源下拉树选择
     *
     * @return java.util.List<cn.hutool.core.lang.tree.Tree<java.lang.Long>>
     * @author laoyu
     * @date 2022/9/20
     */
    List<Tree<Long>> resourceTree();

    /**
     * 根据类型获取资源列表(带详情)
     *
     * @param type type
     * @return java.util.List<cn.hutool.core.lang.tree.Tree<java.lang.Long>>
     * @author laoyu
     * @date 2022/9/20
     */
    List<Tree<Long>> resourceInfoTreeByType(String type);

    /**
     * 根据类型获取资源列表
     *
     * @param type type
     * @return java.util.List<cn.hutool.core.lang.tree.Tree<java.lang.Long>>
     * @author laoyu
     * @date 2022/9/20
     */
    List<Tree<Long>> resourceTreeByType(String type);

    /**
     * 授权角色资源
     *
     * @param authRoleMenuDto authRoleMenuDto
     * @return boolean
     * @author laoyu
     * @date 2022/9/20
     */
    boolean authRoleResource(AuthRoleMenuDto authRoleMenuDto);

    /**
     * 加载对应角色资源列表
     *
     * @param roleId roleId
     * @return java.util.List<cn.hutool.core.lang.tree.Tree<java.lang.Long>>
     * @author laoyu
     * @date 2022/9/20
     */
    List<Tree<Long>> roleResource(Long roleId);

    /**
     * 加载对应角色资源id集合
     *
     * @param roleId roleId
     * @return java.util.List<java.lang.Long>
     * @author laoyu
     * @date 2022/9/20
     */
    List<Long> roleResourceIds(Long roleId);

    /**
     * 加载 用户/游客 资源列表
     *
     * @return java.util.List<cn.hutool.core.lang.tree.Tree<java.lang.Long>>
     * @author laoyu
     * @date 2022/9/20
     */
    List<Tree<Long>> userResource();

    /**
     * 获得用户接口权限列表
     *
     * @param userId userId
     * @return java.util.List<java.lang.String>
     * @author laoyu
     * @date 2022/9/20
     */
    Set<String> userPermissionList(Long userId);

    /**
     * 获取用户资源权限结构数据
     *
     * @param userId userId
     * @return java.util.Map<java.lang.String,java.util.List<java.lang.String>>
     * @author laoyu
     * @date 2022/9/20
     */
    Map<String, List<String>> userResourceAction(Long userId);
}
