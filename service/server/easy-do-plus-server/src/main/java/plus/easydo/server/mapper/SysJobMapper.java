package plus.easydo.server.mapper;

import plus.easydo.server.entity.SysJob;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author yuzhanfeng
* @description 针对表【sys_job(定时任务调度表)】的数据库操作Mapper
* @createDate 2022-06-08 17:27:36
* @Entity entity.server.plus.easydo.SysJob
*/
@Mapper
public interface SysJobMapper extends BaseMapper<SysJob> {

}




