package plus.easydo.server.mapper;


import plus.easydo.server.entity.DockerDetails;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author yuzhanfeng
 * @description docker配置信息的数据库操作Mapper
 * @Entity entity.server.plus.easydo.DockerDetails
 */
@Mapper
public interface DockerDetailsMapper extends BaseMapper<DockerDetails> {
    
}
