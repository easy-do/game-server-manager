package plus.easydo.event;

import org.springframework.beans.factory.annotation.Autowired;
import plus.easydo.common.dto.AuthorizationConfigDto;
import plus.easydo.event.model.*;
import lombok.Data;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;


/**
 * @author laoyu
 * @version 1.0
 * @description 基础事件发布
 * @date 2022/7/12
 */
@Data
@Component
public class BasePublishEventServer {

    @Autowired
    private ApplicationContext applicationContext;

    public void publishResetPasswordEvent(Long userId,String password){
        applicationContext.publishEvent(new ResetPasswordEvent(userId,password));
    }

    public void publishResetSecretEvent(Long userId){
        applicationContext.publishEvent(new ResetSecretEvent(userId));
    }

    public void publishBindingEmailEvent(Long userId){
        applicationContext.publishEvent(new BindingEmailEvent(userId));
    }

    public void publishRegisterEvent(Long userId){
        applicationContext.publishEvent(new RegisterEvent(userId));
    }

    public void publishCommentEvent(Long toUserId, String toUserName, String discussionTitle){
        applicationContext.publishEvent(new CommentEvent(toUserId,toUserName,discussionTitle));
    }

    public void publishAuditEvent(Long userId, String title, String name, String status, String description){
        applicationContext.publishEvent(new AuditEvent(userId,title,name,status,description));
    }
    public void publishUserPointsEvent(Long userId, Long points,String description){
        applicationContext.publishEvent(new UserPointsEvent(userId,points,description));
    }

    public void publishAuthorizationCodeEvent(Long userId, AuthorizationConfigDto authorizationConfigDto){
        applicationContext.publishEvent(new AuthorizationCodeEvent(userId,authorizationConfigDto));
    }

    public void publishAwaitAuditEvent(String title, String name, String description){
        applicationContext.publishEvent(new AwaitAuditEvent(title,name,description));
    }

    public void publishScriptStartEvent(Long userId, Long appScriptId){
        applicationContext.publishEvent(new ScriptStartEvent(userId, appScriptId));
    }

    public void publishScriptEndEvent(Long userId, String scriptName){
        applicationContext.publishEvent(new ScriptEndEvent(userId, scriptName));
    }

    public void publishClientUninstallEvent(String clientId) {
        applicationContext.publishEvent(new ClientUninstallEvent(clientId));
    }
}
