package plus.easydo.uc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import plus.easydo.uc.entity.UserPointsLog;
import org.apache.ibatis.annotations.Mapper;

/**
* @author yuzhanfeng
* @description 针对表【user_points_log(用户积分记录)】的数据库操作Mapper
* @createDate 2022-07-09 23:12:06
* @Entity entity.plus.easydo.uc.UserPointsLog
*/
@Mapper
public interface UserPointsLogMapper extends BaseMapper<UserPointsLog> {

}




