package plus.easydo.server.service.impl;

import com.alicp.jetcache.anno.CacheInvalidate;
import plus.easydo.common.enums.AuditStatusEnum;
import plus.easydo.common.exception.ExceptionFactory;
import plus.easydo.event.BasePublishEventServer;
import plus.easydo.server.dto.AuditDto;
import plus.easydo.server.entity.Application;
import plus.easydo.server.entity.ApplicationVersion;
import plus.easydo.server.entity.Discussion;
import plus.easydo.server.service.ApplicationService;
import plus.easydo.server.service.ApplicationVersionService;
import plus.easydo.server.service.AuditService;
import plus.easydo.server.service.DiscussionService;
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

    @Autowired
    private ApplicationVersionService applicationVersionService;

    @Override
    @CacheInvalidate(name = "AppInfoService.storePage")
    @CacheInvalidate(name = "DiscussionService.page")
    public boolean audit(AuditDto dto) {
        switch (dto.getAuditType()){
            case 1:
                return auditDiscussion(dto);
            case 2:
                return auditApplication(dto);
            case 3:
                return auditApplicationVersion(dto);
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
            case 3:
                return commitApplicationVersionAudit(auditDto);
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

    private boolean commitApplicationVersionAudit(AuditDto auditDto) {
        ApplicationVersion applicationVersion = applicationVersionService.getById(auditDto.getId());
        if(Objects.isNull(applicationVersion)){
            throw ExceptionFactory.bizException("应用版本不存在");
        }
        if(!AuditStatusEnum.canCommitAudit(applicationVersion.getStatus())){
            throw ExceptionFactory.bizException("当前状态无法在提交审核.");
        }
        ApplicationVersion entity = ApplicationVersion.builder().id(auditDto.getId()).status(AuditStatusEnum.AUDIT_ING.getState()).build();
        boolean result = applicationVersionService.updateById(entity);
        //发送待审核事件
        basePublishEventServer.publishAwaitAuditEvent("应用版本待审核通知",applicationVersion.getApplicationName(),"");
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

    private boolean auditApplicationVersion(AuditDto dto){
        ApplicationVersion applicationVersion = applicationVersionService.getById(dto.getId());
        if(Objects.isNull(applicationVersion)){
            throw ExceptionFactory.bizException("应用版本不存在");
        }
        int status = dto.getStatus();
        ApplicationVersion entity = ApplicationVersion.builder().id(dto.getId()).status(status).build();
        boolean result = applicationVersionService.updateById(entity);
        if(result){
            String title = "应用审核";
            //发布计算积分事件
            basePublishEventServer.publishUserPointsEvent(applicationVersion.getCreateBy(),dto.getPoints(),title);
            //发布用户消息事件
            basePublishEventServer.publishAuditEvent(applicationVersion.getCreateBy(),title, applicationVersion.getApplicationName(), AuditStatusEnum.getDesc(status),dto.getDescription());
        }
        return result;
    }
}
