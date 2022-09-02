package game.server.manager.uc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import game.server.manager.event.BasePublishEventServer;
import game.server.manager.uc.dto.UserPointsOperationDto;
import game.server.manager.uc.entity.UserPointsLog;
import game.server.manager.uc.mapper.UserPointsLogMapper;
import game.server.manager.uc.service.UserPointsLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author yuzhanfeng
* @description 针对表【user_points_log(用户积分记录)】的数据库操作Service实现
* @createDate 2022-07-09 23:12:06
*/
@Service
public class UserPointsLogServiceImpl extends ServiceImpl<UserPointsLogMapper, UserPointsLog>
    implements UserPointsLogService {

    @Autowired
    private BasePublishEventServer basePublishEventServer;

    @Override
    public boolean userPointsOperation(UserPointsOperationDto userPointsOperationDto) {
        List<Long> userIdList = userPointsOperationDto.getUserId();
        Long points = userPointsOperationDto.getPoints();
        String description = userPointsOperationDto.getDescription();
        userIdList.forEach(userid->{
            basePublishEventServer.publishUserPointsEvent(userid, points, description);
        });
        return true;
    }
}




