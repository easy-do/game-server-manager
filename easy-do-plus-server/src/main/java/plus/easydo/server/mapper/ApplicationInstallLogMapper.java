package plus.easydo.server.mapper;


import plus.easydo.server.entity.ApplicationInstallLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author yuzhanfeng
 * @description 应用安装日志的数据库操作Mapper
 * @Entity entity.server.plus.easydo.ApplicationInstallLog
 */
@Mapper
public interface ApplicationInstallLogMapper extends BaseMapper<ApplicationInstallLog> {
    
}
