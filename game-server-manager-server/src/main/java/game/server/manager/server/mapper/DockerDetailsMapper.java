package game.server.manager.server.mapper;


import game.server.manager.server.entity.DockerDetails;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author yuzhanfeng
 * @description docker配置信息的数据库操作Mapper
 * @Entity game.server.manager.server.entity.DockerDetails
 */
@Mapper
public interface DockerDetailsMapper extends BaseMapper<DockerDetails> {
    
}
