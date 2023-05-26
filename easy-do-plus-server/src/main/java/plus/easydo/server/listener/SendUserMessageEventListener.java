package plus.easydo.server.listener;


import plus.easydo.api.EmailApi;
import plus.easydo.api.SysDictDataApi;
import plus.easydo.api.UserMessageApi;
import plus.easydo.common.dto.UserMessageDto;
import plus.easydo.common.vo.SysDictDataVo;
import plus.easydo.push.event.model.*;
import plus.easydo.server.entity.ScriptData;
import plus.easydo.server.entity.ClientInfo;
import plus.easydo.server.service.ScriptDataService;
import plus.easydo.server.service.ClientInfoService;
import plus.easydo.server.service.ClientMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author laoyu
 * @version 1.0
 * @description 事件监听器-发送用户消息
 * @date 2022/7/12
 */
@Component
public class SendUserMessageEventListener {


    @Autowired
    private EmailApi emailService;

    @Autowired
    private UserMessageApi userMessageService;

    @Autowired
    private SysDictDataApi sysDictDataService;

    @Autowired
    private ScriptDataService scriptDataService;

    @Autowired
    private ClientInfoService clientInfoService;

    @Autowired
    private ClientMessageService clientMessageService;

    @Async
    @EventListener(CommentEvent.class)
    public void sendCommentMessage(CommentEvent commentEvent) {
        String title = "收到回复通知";
        String content = "用户" + commentEvent.getToUserName() + "再主题" + commentEvent.getDiscussionTitle() + "中回复了你。";
        userMessageService.insert(UserMessageDto.builder().userId(commentEvent.getToUserId()).title(title).content(content).build());
        emailService.sendHtmlMailByUserId(commentEvent.getToUserId(), title, content);
    }

    @Async
    @EventListener(AuditEvent.class)
    public void sendAuditMessage(AuditEvent auditEvent) {
        String title = auditEvent.getTitle().contains("通知") ? auditEvent.getTitle() : auditEvent.getTitle()+"通知" ;
        String content =  auditEvent.getName() + " 状态变更为" + auditEvent.getStatus() + "。" + "备注：" + auditEvent.getDescription();
        UserMessageDto userMessage = UserMessageDto.builder().userId(auditEvent.getUserId())
                .title(title)
                .content(content).build();
        userMessageService.insert(userMessage);
        //发送邮件通知
        emailService.sendHtmlMailByUserId(auditEvent.getUserId(),title,content);
    }


    @Async
    @EventListener(AwaitAuditEvent.class)
    public void sendAwaitAuditEventMessage(AwaitAuditEvent awaitAuditEvent) {
        SysDictDataVo sysDictDataVo = sysDictDataService.getSingleDictData("admin_details", "admin_email").getData();
        String title = awaitAuditEvent.getTitle();
        String content = awaitAuditEvent.getName()+"待审核.";
        emailService.sendHtmlMail(sysDictDataVo.getDictValue(),title,content);
    }


    @Async
    @EventListener(ScriptStartEvent.class)
    public void sendScriptStartEventMessage(ScriptStartEvent scriptStartEvent) {
        Long userId = scriptStartEvent.getUserId();
        Long scriptId = scriptStartEvent.getAppScriptId();
        ScriptData scriptData = scriptDataService.getById(scriptId);
        if(Objects.nonNull(scriptData)){
            String title = "脚本开始执行通知";
            String content = "脚本<"+ scriptData.getScriptName()+">开始执行。";
            UserMessageDto userMessage = UserMessageDto.builder().userId(userId)
                    .title(title)
                    .content(content).build();
            userMessageService.insert(userMessage);
            emailService.sendHtmlMailByUserId(userId,title,content);
        }
    }

    @Async
    @EventListener(ScriptEndEvent.class)
    public void sendScriptEndEventMessage(ScriptEndEvent scriptEndEvent) {
        Long userId = scriptEndEvent.getUserId();
        String scriptName = scriptEndEvent.getScriptName();
        String title = "脚本执行完毕通知";
        String content = "脚本<"+scriptName+">执行完毕。";
        UserMessageDto userMessage = UserMessageDto.builder().userId(userId)
                .title(title)
                .content(content).build();
        userMessageService.insert(userMessage);
        emailService.sendHtmlMailByUserId(userId,title,content);
    }

    @Async
    @EventListener(ClientUninstallEvent.class)
    public void sendClientUninstallMessage(ClientUninstallEvent clientUninstallEvent) {
        String clientId = clientUninstallEvent.getClientId();
        ClientInfo client = clientInfoService.getById(clientId);
        if(Objects.nonNull(client)){
            //删除客户端
            clientInfoService.removeById(clientId);
            clientMessageService.removeByClientId(clientId);
            Long userId = client.getCreateBy();
            String title = "客户端卸载通知";
            String content = "客户端<"+client.getClientName()+">已卸载。";
            UserMessageDto userMessage = UserMessageDto.builder().userId(userId)
                    .title(title)
                    .content(content).build();
            userMessageService.insert(userMessage);
            emailService.sendHtmlMailByUserId(userId,title,content);
        }
    }

}
