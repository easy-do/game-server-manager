package game.server.manager.server.mapper;


import game.server.manager.server.entity.ApplicationInstallLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author yuzhanfeng
 * @description 应用安装日志的数据库操作Mapper
 * @Entity game.server.manager.server.entity.ApplicationInstallLog
 */
@Mapper
public interface ApplicationInstallLogMapper extends BaseMapper<ApplicationInstallLog> {
    
}
