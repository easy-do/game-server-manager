package plus.easydo.uc.service;

import plus.easydo.common.dto.ChangeStatusDto;
import plus.easydo.uc.dto.AuthRoleDto;
import plus.easydo.uc.dto.SysRoleDto;
import plus.easydo.uc.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;
import plus.easydo.common.vo.SysRoleVo;

import java.util.List;

/**
* @author yuzhanfeng
* @description 针对表【sys_role(角色信息表)】的数据库操作Service
* @createDate 2022-07-17 15:31:58
*/
public interface SysRoleService extends IService<SysRole> {

    /**
     * 获取所有角色
     *
     * @return java.util.List<plus.easydo.push.server.entity.SysRole>
     * @author laoyu
     * @date 2022/7/17
     */
    List<SysRole> selectRoleAll();

    /**
     * 获取用户所有角色信息
     *
     * @param userId userId
     * @return java.util.List<plus.easydo.push.server.entity.SysRole>
     * @author laoyu
     * @date 2022/7/17
     */
    List<SysRoleVo> selectRolesByUserId(Long userId);

    /**
     * 生成用户角色关联关系
     *
     * @param authRoleDto authRoleDto
     * @return boolean
     * @author laoyu
     * @date 2022/7/20
     */
    boolean insertUserAuth(AuthRoleDto authRoleDto);

    /**
     * 校验角色唯一性
     *
     * @param dto dto
     * @return boolean
     * @author laoyu
     * @date 2022/7/18
     */
    boolean checkRoleNameUnique(SysRoleDto dto);

    /**
     * 更该角色状态
     *
     * @param changeStatusDto changeStatusDto
     * @return boolean
     * @author laoyu
     * @date 2022/7/18
     */
    boolean updateRoleStatus(ChangeStatusDto changeStatusDto);


    /**
     * 绑定默认角色
     *
     * @param userId userId
     * @author laoyu
     * @date 2022/7/21
     */
    void bindingDefaultRole(Long userId);

    /**
     * 设置所有用户的角色为默认角色
     *
     * @return boolean
     * @author laoyu
     * @date 2022/7/21
     */
    boolean syncAllUserRoleDefault();
}
