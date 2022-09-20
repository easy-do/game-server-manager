package game.server.manager.uc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import game.server.manager.uc.entity.SysRoleResource;
import game.server.manager.uc.service.SysRoleResourceService;
import game.server.manager.uc.mapper.SysRoleResourceMapper;
import org.springframework.stereotype.Service;

/**
* @author yuzhanfeng
* @description 针对表【sys_role_resource(角色和资源关联表)】的数据库操作Service实现
* @createDate 2022-09-20 09:31:33
*/
@Service
public class SysRoleResourceServiceImpl extends ServiceImpl<SysRoleResourceMapper, SysRoleResource>
    implements SysRoleResourceService{

    @Override
    public boolean removeByRoleId(Long roleId) {
        LambdaQueryWrapper<SysRoleResource> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysRoleResource::getRoleId,roleId);
        return remove(wrapper);
    }
}




