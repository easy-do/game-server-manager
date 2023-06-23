package plus.easydo.uc.listener;


import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.text.CharSequenceUtil;
import plus.easydo.common.dto.AuthorizationConfigDto;
import plus.easydo.event.BasePublishEventServer;
import plus.easydo.event.model.*;
import plus.easydo.uc.entity.UserMessage;
import plus.easydo.uc.service.EmailService;
import plus.easydo.uc.service.UserMessageService;
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
public class SendUserMessageEventListener {


    @Autowired
    private EmailService emailService;

    @Autowired
    private UserMessageService userMessageService;

    @Autowired
    private BasePublishEventServer basePublishEventServer;


    @Async
    @EventListener(ResetSecretEvent.class)
    public void sendResetSecretMessage(ResetSecretEvent resetSecretEvent){
        Long userId = resetSecretEvent.getUserId();
        String title = "重置密钥通知";
        String content = "账户密钥已重置，请注意保存防止泄露。";
        UserMessage userMessage = UserMessage.builder().userId(userId)
                .title(title).createTime(LocalDateTime.now())
                .content(content).build();
        userMessageService.save(userMessage);
        emailService.sendHtmlMailByUserId(userId, title, content);
    }


    @Async
    @EventListener(ResetPasswordEvent.class)
    public void sendResetPasswordMessage(ResetPasswordEvent resetPasswordEvent) {
        String title = "密码修改通知";
        String content = "账户密码修改成功，新的密码为：" + resetPasswordEvent.getPassword();
        UserMessage userMessage = UserMessage.builder().userId(resetPasswordEvent.getUserId())
                .title(title).createTime(LocalDateTime.now())
                .content(content).build();
        userMessageService.save(userMessage);
        emailService.sendHtmlMailByUserId(resetPasswordEvent.getUserId(), title, content);
    }

    @Async
    @EventListener(BindingEmailEvent.class)
    public void sendBindingEmailMessage(BindingEmailEvent bindingEmailEvent) {
        String title = "邮箱绑定通知";
        String content = "您的账号已成功绑定邮箱。";
        UserMessage userMessage = UserMessage.builder().userId(bindingEmailEvent.getUserId())
                .title(title).createTime(LocalDateTime.now())
                .content(content).build();
        userMessageService.save(userMessage);
        emailService.sendHtmlMailByUserId(bindingEmailEvent.getUserId(), title, content);
        basePublishEventServer.publishUserPointsEvent(bindingEmailEvent.getUserId(),100L,"绑定邮箱。");
    }

    @Async
    @EventListener(RegisterEvent.class)
    public void sendRegMessage(RegisterEvent registerEvent) {
        String title = "注册成功通知";
        String content = "账号注册成功。";
        UserMessage userMessage = UserMessage.builder().userId(registerEvent.getUserId())
                .title(title).createTime(LocalDateTime.now())
                .content(content).build();
        userMessageService.save(userMessage);
        emailService.sendHtmlMailByUserId(registerEvent.getUserId(), title, content);
    }

    @Async
    @EventListener(AuthorizationCodeEvent.class)
    public void sendAuthorizationCodeEventMessage(AuthorizationCodeEvent authorizationCodeEvent) {
        AuthorizationConfigDto dto = authorizationCodeEvent.getAuthorizationConfigDto();
        String title = "授权通知";
        String content = CharSequenceUtil.format("您已成功授权,服务授权{},应用授权授权{},应用创作{},脚本创作{},到期时间{}",
                dto.getServerNum(),dto.getApplicationNum(),dto.getAppCreationNum(),dto.getAppCreationNum(),
                DateUtil.format(dto.getExpires(), DatePattern.NORM_DATETIME_PATTERN));
        UserMessage userMessage = UserMessage.builder().userId(authorizationCodeEvent.getUserId())
                .title(title).createTime(LocalDateTime.now())
                .content(content).build();
        userMessageService.save(userMessage);
        //发送邮件通知
        emailService.sendHtmlMailByUserId(authorizationCodeEvent.getUserId(),title,content);
    }

    @Async
    @EventListener(UserPointsEvent.class)
    public void sendUserPointsEventMessage(UserPointsEvent userPointsEvent) {
        String title = "积分变动通知";
        String content = "积分变动 "+ userPointsEvent.getPoints() +",备注：" + userPointsEvent.getDescription();
        UserMessage userMessage = UserMessage.builder().userId(userPointsEvent.getUserId())
                .title(title).createTime(LocalDateTime.now())
                .content(content).build();
        userMessageService.save(userMessage);
    }

}
