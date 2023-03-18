package game.server.manager.server.mapper;


import game.server.manager.server.entity.ApplicationVersion;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author yuzhanfeng
 * @description 应用版本信息的数据库操作Mapper
 * @Entity game.server.manager.server.entity.ApplicationVersion
 */
@Mapper
public interface ApplicationVersionMapper extends BaseMapper<ApplicationVersion> {
    
}
