package plus.easydo.server.mapper;


import plus.easydo.server.entity.ApplicationVersion;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author yuzhanfeng
 * @description 应用版本信息的数据库操作Mapper
 * @Entity entity.server.plus.easydo.ApplicationVersion
 */
@Mapper
public interface ApplicationVersionMapper extends BaseMapper<ApplicationVersion> {
    
}
