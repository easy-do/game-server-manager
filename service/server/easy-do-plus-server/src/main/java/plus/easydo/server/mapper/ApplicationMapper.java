package plus.easydo.server.mapper;


import plus.easydo.server.entity.Application;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author yuzhanfeng
 * @description 应用信息的数据库操作Mapper
 * @Entity entity.server.plus.easydo.ServerApplication
 */
@Mapper
public interface ApplicationMapper extends BaseMapper<Application> {

    void addHeat(Long applicationId);
}
