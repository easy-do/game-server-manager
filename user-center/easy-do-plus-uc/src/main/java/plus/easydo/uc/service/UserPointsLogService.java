package plus.easydo.uc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import plus.easydo.uc.dto.UserPointsOperationDto;
import plus.easydo.uc.entity.UserPointsLog;

/**
* @author yuzhanfeng
* @description 针对表【user_points_log(用户积分记录)】的数据库操作Service
* @createDate 2022-07-09 23:12:06
*/
public interface UserPointsLogService extends IService<UserPointsLog> {

    /**
     * 对指定用户积分进行操作
     *
     * @param userPointsOperationDto userPointsOperationDto
     * @return boolean
     * @author laoyu
     * @date 2022/7/29
     */
    boolean userPointsOperation(UserPointsOperationDto userPointsOperationDto);
}
