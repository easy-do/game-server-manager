package game.server.manager.uc.listener;


import game.server.manager.auth.AuthorizationUtil;
import game.server.manager.common.vo.UserInfoVo;
import game.server.manager.event.model.UserPointsEvent;
import game.server.manager.uc.entity.UserInfo;
import game.server.manager.uc.entity.UserPointsLog;
import game.server.manager.uc.mapstruct.UserInfoMapstruct;
import game.server.manager.uc.service.UserInfoService;
import game.server.manager.uc.service.UserPointsLogService;
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
                AuthorizationUtil.reloadUserCache(userInfoVo);
            }
        }
    }

}
