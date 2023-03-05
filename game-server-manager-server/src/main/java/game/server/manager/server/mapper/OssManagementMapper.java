package game.server.manager.server.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import game.server.manager.server.entity.OssManagement;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author yuzhanfeng
 * @description 存储管理的数据库操作Mapper
 * @Entity game.server.manager.oss.entity.OssManagement
 */
@Mapper
public interface OssManagementMapper extends BaseMapper<OssManagement> {
    
}
