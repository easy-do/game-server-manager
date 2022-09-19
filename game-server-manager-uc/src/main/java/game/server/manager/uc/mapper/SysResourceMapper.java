package game.server.manager.uc.mapper;


import game.server.manager.uc.entity.SysResource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author yuzhanfeng
 * @description 系统资源的数据库操作Mapper
 * @Entity game.server.manager.uc.entity.SysResource
 */
@Mapper
public interface SysResourceMapper extends BaseMapper<SysResource> {
    
}
