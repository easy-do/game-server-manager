package game.server.manager.uc.service;

import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.extension.service.IService;
import game.server.manager.common.dto.ChangeStatusDto;
import game.server.manager.mybatis.plus.qo.MpBaseQo;
import game.server.manager.uc.dto.AuthRoleMenuDto;
import game.server.manager.uc.entity.SysMenu;

import java.util.List;

/**
* @author yuzhanfeng
* @description 针对表【sys_menu(菜单权限表)】的数据库操作Service
* @createDate 2022-07-18 19:57:36
*/
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 构建所有菜单下拉树形结构
     *
     * @return java.util.List<cn.hutool.core.lang.tree.Tree<java.lang.Long>>
     * @author laoyu
     * @date 2022/7/19
     */
    List<Tree<Long>> treeSelect();

    /**
     * 获取角色的所有菜单下拉框树
     *
     * @param roleId roleId
     * @return java.lang.Object
     * @author laoyu
     * @date 2022/7/19
     */
    List<Tree<Long>> roleTreeSelect(Long roleId);


    /**
     * 获取用户的所有菜单下拉树
     *
     * @param userId userId
     * @return java.util.List<cn.hutool.core.lang.tree.Tree<java.lang.Long>>
     * @author laoyu
     * @date 2022/7/19
     */
    List<Tree<Long>> userMenu(Long userId);

    /**
     * 获取用户的所有权限列表
     *
     * @param userId userId
     * @return java.util.List<java.lang.String>
     * @author laoyu
     * @date 2022/9/1
     */
    List<String> userPermissionList(Long userId);

    /**
     * 获取所有菜单下数列表(带详情)
     *
     * @param mpBaseQo mpBaseQo
     * @return java.util.List<cn.hutool.core.lang.tree.Tree<java.lang.Long>>
     * @author laoyu
     * @date 2022/7/19
     */
    List<Tree<Long>> treeInfoList(MpBaseQo mpBaseQo);

    /**
     * 授权角色菜单
     *
     * @param authRoleMenuDto authRoleMenuDto
     * @return boolean
     * @author laoyu
     * @date 2022/7/20
     */
    boolean authRoleMenu(AuthRoleMenuDto authRoleMenuDto);

    /**
     * 获取角色所有菜单id
     *
     * @param roleId roleId
     * @return java.util.List<java.lang.Long>
     * @author laoyu
     * @date 2022/7/20
     */
    List<Long> roleMenuIds(Long roleId);

    /**
     * 更改菜单状态
     *
     * @param changeStatusDto changeStatusDto
     * @return boolean
     * @author laoyu
     * @date 2022/7/21
     */
    boolean changeStatus(ChangeStatusDto changeStatusDto);
}
