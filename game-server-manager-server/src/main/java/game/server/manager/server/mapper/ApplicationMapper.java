package game.server.manager.server.mapper;


import game.server.manager.server.entity.Application;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author yuzhanfeng
 * @description 应用信息的数据库操作Mapper
 * @Entity game.server.manager.server.entity.Application
 */
@Mapper
public interface ApplicationMapper extends BaseMapper<Application> {

    void addHeat(Long applicationId);
}
