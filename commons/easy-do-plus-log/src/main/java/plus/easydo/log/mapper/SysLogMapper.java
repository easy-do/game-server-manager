package plus.easydo.log.mapper;

import plus.easydo.log.entity.SysLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author yuzhanfeng
* @description 针对表【sys_log(系统日志)】的数据库操作Mapper
* @createDate 2022-06-18 20:15:08
* @Entity plus.easydo.push.server.entity.SysLog
*/
@Mapper
public interface SysLogMapper extends BaseMapper<SysLog> {

}




