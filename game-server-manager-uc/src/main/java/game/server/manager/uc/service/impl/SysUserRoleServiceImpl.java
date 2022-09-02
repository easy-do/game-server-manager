package game.server.manager.uc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import game.server.manager.uc.entity.SysUserRole;
import game.server.manager.uc.mapper.SysUserRoleMapper;
import game.server.manager.uc.service.SysUserRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author yuzhanfeng
* @description 针对表【sys_user_role(用户和角色关联表)】的数据库操作Service实现
* @createDate 2022-07-17 16:11:13
*/
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole>
    implements SysUserRoleService {

    @Override
    public List<SysUserRole> listByUserId(Long userId) {
        LambdaQueryWrapper<SysUserRole> userRoleWrapper = Wrappers.lambdaQuery();
        userRoleWrapper.eq(SysUserRole::getUserId, userId);
        return list(userRoleWrapper);
    }

    @Override
    public List<Long> getRoleIdListByUserId(Long userId) {
        List<SysUserRole> userRoles = listByUserId(userId);
        return userRoles.stream().map(SysUserRole::getRoleId).toList();
    }
}




