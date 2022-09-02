package game.server.manager.server.mapper;

import game.server.manager.server.entity.SysJobLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author yuzhanfeng
* @description 针对表【sys_job_log(定时任务调度日志表)】的数据库操作Mapper
* @createDate 2022-06-08 17:27:36
* @Entity game.server.manager.server.entity.SysJobLog
*/
@Mapper
public interface SysJobLogMapper extends BaseMapper<SysJobLog> {

}




