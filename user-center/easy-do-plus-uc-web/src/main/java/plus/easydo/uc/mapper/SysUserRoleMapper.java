package plus.easydo.uc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import plus.easydo.uc.entity.SysUserRole;
import org.apache.ibatis.annotations.Mapper;

/**
* @author yuzhanfeng
* @description 针对表【sys_user_role(用户和角色关联表)】的数据库操作Mapper
* @createDate 2022-07-17 16:11:13
* @Entity entity.plus.easydo.uc.SysUserRole
*/
@Mapper
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

}




