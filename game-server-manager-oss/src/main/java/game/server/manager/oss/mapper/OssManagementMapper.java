package game.server.manager.oss.mapper;


import game.server.manager.oss.entity.OssManagement;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author yuzhanfeng
 * @description 存储管理的数据库操作Mapper
 * @Entity game.server.manager.oss.entity.OssManagement
 */
@Mapper
public interface OssManagementMapper extends BaseMapper<OssManagement> {
    
}
