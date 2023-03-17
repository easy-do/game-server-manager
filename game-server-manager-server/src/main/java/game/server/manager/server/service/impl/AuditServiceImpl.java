package game.server.manager.server.service.impl;

import com.alicp.jetcache.anno.CacheInvalidate;
import game.server.manager.common.enums.AuditStatusEnum;
import game.server.manager.common.exception.ExceptionFactory;
import game.server.manager.event.BasePublishEventServer;
import game.server.manager.server.dto.AuditDto;
import game.server.manager.server.entity.AppInfo;
import game.server.manager.server.entity.Application;
import game.server.manager.server.entity.Discussion;
import game.server.manager.server.service.ApplicationService;
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
    private ApplicationService applicationService;

    @Override
    @CacheInvalidate(name = "AppInfoService.storePage")
    @CacheInvalidate(name = "DiscussionService.page")
    public boolean audit(AuditDto dto) {
        switch (dto.getAuditType()){
            case 1:
                return auditDiscussion(dto);
            case 2:
                return auditApplication(dto);
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
                return commitApplicationAudit(auditDto);
            default:
                return false;
        }
    }

    private boolean commitApplicationAudit(AuditDto auditDto) {
        Application application = applicationService.getById(auditDto.getId());
        if(Objects.isNull(application)){
            throw ExceptionFactory.bizException("应用不存在");
        }
        if(!AuditStatusEnum.canCommitAudit(application.getStatus())){
            throw ExceptionFactory.bizException("当前状态无法在提交审核.");
        }
        Application entity = Application.builder().id(auditDto.getId()).status(AuditStatusEnum.AUDIT_ING.getState()).build();
        boolean result = applicationService.updateById(entity);
        //发送待审核事件
        basePublishEventServer.publishAwaitAuditEvent("应用待审核通知",application.getApplicationName(),"");
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


    private boolean auditApplication(AuditDto dto){
        Application application = applicationService.getById(dto.getId());
        if(Objects.isNull(application)){
            throw ExceptionFactory.bizException("应用不存在");
        }
        int status = dto.getStatus();
        Application entity = Application.builder().id(dto.getId()).status(status).build();
        boolean result = applicationService.updateById(entity);
        if(result){
            String title = "应用审核";
            //发布计算积分事件
            basePublishEventServer.publishUserPointsEvent(application.getCreateBy(),dto.getPoints(),title);
            //发布用户消息事件
            basePublishEventServer.publishAuditEvent(application.getCreateBy(),title, application.getApplicationName(), AuditStatusEnum.getDesc(status),dto.getDescription());
        }
        return result;
    }
}
