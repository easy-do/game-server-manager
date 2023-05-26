package plus.easydo.uc.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import plus.easydo.common.dto.ChangeStatusDto;
import plus.easydo.common.vo.SysRoleVo;
import plus.easydo.uc.dto.AuthRoleDto;
import plus.easydo.uc.dto.SysRoleDto;
import plus.easydo.uc.entity.SysRole;
import plus.easydo.uc.entity.SysUserRole;
import plus.easydo.uc.entity.UserInfo;
import plus.easydo.uc.mapper.SysRoleMapper;
import plus.easydo.uc.service.SysRoleService;
import plus.easydo.uc.service.SysUserRoleService;
import plus.easydo.uc.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * @author yuzhanfeng
 * @description 针对表【sys_role(角色信息表)】的数据库操作Service实现
 * @createDate 2022-07-17 15:31:58
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole>
        implements SysRoleService {

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Autowired
    private UserInfoService userInfoService;

    @Override
    public List<SysRole> selectRoleAll() {
        LambdaQueryWrapper<SysRole> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysRole::getStatus, 0);
        return list(wrapper);
    }

    @Override
    public List<SysRoleVo> selectRolesByUserId(Long userId) {
        List<Long> roleIds = sysUserRoleService.getRoleIdListByUserId(userId);
        if(roleIds.isEmpty()){
            return ListUtil.empty();
        }
        LambdaQueryWrapper<SysRole> roleWrapper = Wrappers.lambdaQuery();
        roleWrapper.select(SysRole::getRoleId,SysRole::getRoleName,SysRole::getRoleKey);
        roleWrapper.in(SysRole::getRoleId,roleIds);
        return BeanUtil.copyToList(list(roleWrapper), SysRoleVo.class);
    }

    @Override
    public boolean insertUserAuth(AuthRoleDto authRoleDto) {
        Long userId = authRoleDto.getUserId();
        List<Long> roleIds = authRoleDto.getRoleIds();
        LambdaQueryWrapper<SysUserRole> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysUserRole::getUserId, userId);
        sysUserRoleService.remove(wrapper);
        Collection<SysUserRole> entityList = new ArrayList<>();
        for (Long roleId : roleIds) {
            entityList.add(SysUserRole.builder().userId(userId).roleId(roleId).build());
        }
        return sysUserRoleService.saveBatch(entityList);
    }

    @Override
    public boolean checkRoleNameUnique(SysRoleDto dto) {
        LambdaQueryWrapper<SysRole> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysRole::getRoleName, dto.getRoleName());
        wrapper.eq(SysRole::getRoleKey, dto.getRoleKey());
        if (Objects.nonNull(dto.getRoleId())) {
            wrapper.ne(SysRole::getRoleId, dto.getRoleId());
        }
        return baseMapper.exists(wrapper);
    }

    @Override
    public boolean updateRoleStatus(ChangeStatusDto changeStatusDto) {
        Long roleId = changeStatusDto.getId();
        Integer status = changeStatusDto.getStatus();
        Long updateUserId = changeStatusDto.getUpdateUserId();
        SysRole entity = SysRole.builder().roleId(roleId).status(status).updateBy(updateUserId).updateTime(LocalDateTime.now()).build();
        return updateById(entity);
    }

    @Override
    public void bindingDefaultRole(Long userId) {
        LambdaQueryWrapper<SysUserRole> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysUserRole::getUserId, userId);
        sysUserRoleService.remove(wrapper);
        List<Long> roleIds = getDefaultRoleId();
        Collection<SysUserRole> entityList = new ArrayList<>();
        for (Long roleId : roleIds) {
            entityList.add(SysUserRole.builder().userId(userId).roleId(roleId).build());
        }
        sysUserRoleService.saveBatch(entityList);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean syncAllUserRoleDefault() {
        sysUserRoleService.remove(null);
        List<Long> roleIds = getDefaultRoleId();
        List<UserInfo> userList = userInfoService.list();
        Collection<SysUserRole> entityList = new ArrayList<>();
        for (Long roleId : roleIds) {
            userList.forEach(userInfo -> entityList.add(SysUserRole.builder().userId(userInfo.getId()).roleId(roleId).build()));
        }
        return sysUserRoleService.saveBatch(entityList);
    }

    public List<Long> getDefaultRoleId(){
        LambdaQueryWrapper<SysRole> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysRole::getIsDefault,1);
        List<SysRole> list = list(wrapper);
        return list.stream().map(SysRole::getRoleId).toList();
    }

}




