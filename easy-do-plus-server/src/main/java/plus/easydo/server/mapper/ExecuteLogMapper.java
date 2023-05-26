package plus.easydo.server.mapper;

import plus.easydo.server.entity.ExecuteLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author yuzhanfeng
* @description 针对表【execute_log(执行日志)】的数据库操作Mapper
* @createDate 2022-07-02 02:12:05
* @Entity entity.server.plus.easydo.ExecuteLog
*/
@Mapper
public interface ExecuteLogMapper extends BaseMapper<ExecuteLog> {

}




