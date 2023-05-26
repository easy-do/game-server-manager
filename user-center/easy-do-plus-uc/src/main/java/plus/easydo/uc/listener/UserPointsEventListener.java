package plus.easydo.uc.listener;


import plus.easydo.auth.AuthorizationUtil;
import plus.easydo.common.vo.UserInfoVo;
import plus.easydo.event.model.UserPointsEvent;
import plus.easydo.uc.entity.UserInfo;
import plus.easydo.uc.entity.UserPointsLog;
import plus.easydo.uc.mapstruct.UserInfoMapstruct;
import plus.easydo.uc.service.UserInfoService;
import plus.easydo.uc.service.UserPointsLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author laoyu
 * @version 1.0
 * @description 事件监听器-发送用户消息
 * @date 2022/7/12
 */
@Component
public class UserPointsEventListener {


    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserPointsLogService userPointsLogService;

    @Autowired
    private AuthorizationUtil authorizationUtil;

    @Async
    @EventListener(UserPointsEvent.class)
    public void sendUserPointsEventMessage(UserPointsEvent userPointsEvent){
        Long points = userPointsEvent.getPoints();
        Long userId = userPointsEvent.getUserId();
        if (points != 0) {
            UserInfo user = userInfoService.getById(userId);
            long newPoints = (user.getPoints() + points);
            UserInfo entity = UserInfo.builder().id(userId).points(newPoints).build();
            boolean result = userInfoService.updateById(entity);
            if (result) {
                //生成积分记录、更新缓存数据
                UserPointsLog pointsLog = UserPointsLog.builder().userId(user.getId())
                        .points(user.getPoints()).currentPoints(newPoints).description(userPointsEvent.getDescription()).createTime(LocalDateTime.now()).build();
                userPointsLogService.save(pointsLog);
                UserInfoVo userInfoVo = UserInfoMapstruct.INSTANCE.entityToVo(user);
                authorizationUtil.reloadUserCache(userInfoVo);
            }
        }
    }

}
