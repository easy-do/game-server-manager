package game.server.manager.server.service.impl;

import com.alicp.jetcache.anno.CacheInvalidate;
import game.server.manager.common.enums.AuditStatusEnum;
import game.server.manager.common.exception.ExceptionFactory;
import game.server.manager.event.BasePublishEventServer;
import game.server.manager.server.dto.AuditDto;
import game.server.manager.server.entity.AppInfo;
import game.server.manager.server.entity.Discussion;
import game.server.manager.server.service.AppInfoService;
import game.server.manager.server.service.AuditService;
import game.server.manager.server.service.DiscussionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author laoyu
 * @version 1.0
 * @date 2022/7/23
 */
@Service
public class AuditServiceImpl implements AuditService {


    @Autowired
    private BasePublishEventServer basePublishEventServer;

    @Autowired
    private DiscussionService discussionService;

    @Autowired
    private AppInfoService appInfoService;

    @Override
    @CacheInvalidate(name = "AppInfoService.storePage")
    @CacheInvalidate(name = "DiscussionService.page")
    public boolean audit(AuditDto dto) {
        switch (dto.getAuditType()){
            case 1:
                return auditDiscussion(dto);
            case 2:
                return auditApp(dto);
            default:
                return false;
        }
    }

    @Override
    @CacheInvalidate(name = "AppInfoService.storePage")
    @CacheInvalidate(name = "DiscussionService.page")
    public boolean commitAudit(AuditDto auditDto) {
        switch (auditDto.getAuditType()){
            case 1:
                return commitDiscussionAudit(auditDto);
            case 2:
                return commitAppAudit(auditDto);
            default:
                return false;
        }
    }

    private boolean commitAppAudit(AuditDto auditDto) {
        AppInfo appInfo = appInfoService.getById(auditDto.getId());
        if(Objects.isNull(appInfo)){
            throw ExceptionFactory.bizException("APP不存在");
        }
        if(!AuditStatusEnum.canCommitAudit(appInfo.getIsAudit())){
            throw ExceptionFactory.bizException("当前状态无法在提交审核.");
        }
        AppInfo entity = AppInfo.builder().id(auditDto.getId()).isAudit(AuditStatusEnum.AUDIT_ING.getState()).build();
        boolean result = appInfoService.updateById(entity);
        //发送待审核事件
        basePublishEventServer.publishAwaitAuditEvent("APP待审核通知",appInfo.getAppName(),"");
        return result;
    }

    private boolean commitDiscussionAudit(AuditDto auditDto) {
        Discussion discussion = discussionService.getById(auditDto.getId());
        if(Objects.isNull(discussion)){
            throw ExceptionFactory.bizException("话题不存在");
        }
        if(!AuditStatusEnum.canCommitAudit(discussion.getStatus())){
            throw ExceptionFactory.bizException("当前状态无法在提交审核.");
        }
        Discussion entity = Discussion.builder().id(auditDto.getId()).status(AuditStatusEnum.AUDIT_ING.getState()).build();
        boolean result = discussionService.updateById(entity);
        //发送待审核事件
        basePublishEventServer.publishAwaitAuditEvent("话题待审核通知",discussion.getTitle(),"");
        return result;
    }

    private boolean auditDiscussion(AuditDto dto) {
        Discussion discussion = discussionService.getById(dto.getId());
        if(Objects.isNull(discussion)){
            throw ExceptionFactory.bizException("话题不存在");
        }
        int status = dto.getStatus();
        Discussion entity = Discussion.builder().id(dto.getId()).status(status).build();
        boolean result = discussionService.updateById(entity);
        if(result){
            String title = "话题审核";
            //发布计算积分事件
            basePublishEventServer.publishUserPointsEvent(discussion.getCreateBy(),dto.getPoints(),title);
            //发布用户消息事件
            basePublishEventServer.publishAuditEvent(discussion.getCreateBy(),title, discussion.getTitle(), AuditStatusEnum.getDesc(status),dto.getDescription());
        }
        return result;
    }


    private boolean auditApp(AuditDto dto){
        AppInfo appInfo = appInfoService.getById(dto.getId());
        if(Objects.isNull(appInfo)){
            throw ExceptionFactory.bizException("APP不存在");
        }
        int status = dto.getStatus();
        AppInfo entity = AppInfo.builder().id(dto.getId()).isAudit(status).build();
        boolean result = appInfoService.updateById(entity);
        if(result){
            String title = "APP审核";
            //发布计算积分事件
            basePublishEventServer.publishUserPointsEvent(appInfo.getCreateBy(),dto.getPoints(),title);
            //发布用户消息事件
            basePublishEventServer.publishAuditEvent(appInfo.getCreateBy(),title, appInfo.getAppName(), AuditStatusEnum.getDesc(status),dto.getDescription());
        }
        return result;
    }
}
