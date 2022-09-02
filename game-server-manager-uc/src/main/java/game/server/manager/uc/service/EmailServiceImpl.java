package game.server.manager.uc.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.RandomUtil;
import game.server.manager.common.constant.SystemConstant;
import game.server.manager.redis.config.RedisUtils;
import game.server.manager.uc.entity.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import game.server.manager.common.exception.BizException;

import java.util.Objects;
import java.util.concurrent.TimeUnit;


/**
 * @author laoyu
 * @version 1.0
 * @description 邮箱服务实现
 * @date 2022/6/12
 */
@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

    @Value("${spring.mail.send-mailer}")
    private String sendMailer;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private RedisUtils<String> redisUtils;

    @Autowired
    private UserInfoService userInfoService;

    private String sendMail(String email, String title, String text){
        SimpleMailMessage message = new SimpleMailMessage();
        //邮件发件人
        message.setFrom(sendMailer);
        //邮件收件人 1或多个
        message.setTo(email);
        //邮件主题
        message.setSubject(title);
        //邮件内容
        message.setText(text);
        //邮件发送时间
        message.setSentDate(DateUtil.date());
        javaMailSender.send(message);
        return "ok";
    }

    @Override
    public Object sendMailCode(String email) {
        String cacheCode = getCode(email);
        if(Objects.nonNull(cacheCode)){
            throw new BizException("500","请勿重复发送，稍等一分钟");
        }
        String code = RandomUtil.randomInt(10000, 99999) + "";
        String result = sendMail(email, "简单推送,邮箱验证码", CODE_HTML.replace("#emailCode", code + ""));
        log.info("发送邮件验证码:-->{}",email);
        saveCode(email,code);
        return result;
    }

    @Override
    public boolean checkCode(String email, String code) {
        String cacheCode = getCode(email);
        if(Objects.isNull(cacheCode)){
            throw new BizException("500","验证码不存在");
        }
        return CharSequenceUtil.equals(cacheCode,code);
    }

    @Override
    public String sendHtmlMail(String email, String title, String content) {
        log.info("发送html邮件:-->{}",email);
        return sendMail(email,title,MESSAGE_HTML.replace("#title", title ).replace("#content",content));
    }

    @Override
    public String sendHtmlMailByUserId(Long userId, String title, String content) {
        UserInfo user = userInfoService.getById(userId);
        if(Objects.nonNull(user) && CharSequenceUtil.isNotEmpty(user.getEmail())){
            String email = user.getEmail();
            return sendMail(email,title,content);
        }
        log.info("用户"+userId+"不存在或未绑定邮箱");
        return "用户不存在或未绑定邮箱";
    }

    private String getCode(String email){
        return redisUtils.get(SystemConstant.PREFIX + SystemConstant.MAIL_CODE + email + ":");
    }

    private void saveCode(String email,String code){
        redisUtils.set(SystemConstant.PREFIX+SystemConstant.MAIL_CODE+email+":",code,1, TimeUnit.MINUTES);
    }


    private static final String CODE_HTML = """
            <!DOCTYPE html>
            <html lang="en" xmlns:th="http://www.thymeleaf.org">
            <head>
                <meta charset="UTF-8">
                <title>邮箱验证码</title>
                <style>
                    table {
                        width: 700px;
                        margin: 0 auto;
                    }
                
                    #top {
                        width: 700px;
                        border-bottom: 1px solid #ccc;
                        margin: 0 auto 30px;
                    }
                
                    #top table {
                        font: 12px Tahoma, Arial, 宋体;
                        height: 40px;
                    }
                
                    #content {
                        width: 680px;
                        padding: 0 10px;
                        margin: 0 auto;
                    }
                
                    #content_top {
                        line-height: 1.5;
                        font-size: 14px;
                        margin-bottom: 25px;
                        color: #4d4d4d;
                    }
                
                    #content_top strong {
                        display: block;
                        margin-bottom: 15px;
                    }
                
                    #content_top strong span {
                        color: #f60;
                        font-size: 16px;
                    }
                
                    #verificationCode {
                        color: #f60;
                        font-size: 24px;
                    }
                
                    #content_bottom {
                        margin-bottom: 30px;
                    }
                
                    #content_bottom small {
                        display: block;
                        margin-bottom: 20px;
                        font-size: 12px;
                        color: #747474;
                    }
                
                    #bottom {
                        width: 700px;
                        margin: 0 auto;
                    }
                
                    #bottom div {
                        padding: 10px 10px 0;
                        border-top: 1px solid #ccc;
                        color: #747474;
                        margin-bottom: 20px;
                        line-height: 1.3em;
                        font-size: 12px;
                    }
                
                    #content_top strong span {
                        font-size: 18px;
                        color: #FE4F70;
                    }
                
                    #sign {
                        text-align: right;
                        font-size: 18px;
                        color: #FE4F70;
                        font-weight: bold;
                    }
                
                    #verificationCode {
                        height: 100px;
                        width: 680px;
                        text-align: center;
                        margin: 30px 0;
                    }
                
                    #verificationCode div {
                        height: 100px;
                        width: 680px;
                
                    }
                
                    .button {
                        color: #FE4F70;
                        margin-left: 10px;
                        height: 80px;
                        width: 80px;
                        resize: none;
                        font-size: 42px;
                        border: none;
                        outline: none;
                        padding: 10px 15px;
                        background: #ededed;
                        text-align: center;
                        border-radius: 17px;
                        box-shadow: 6px 6px 12px #cccccc,
                        -6px -6px 12px #ffffff;
                    }
                
                    .button:hover {
                        box-shadow: inset 6px 6px 4px #d1d1d1,
                        inset -6px -6px 4px #ffffff;
                    }
                
                </style>
            </head>
            <body>
            <table>
                <tbody>
                <tr>
                    <td>
                        <div id="top">
                            <table>
                                <tbody><tr><td></td></tr></tbody>
                            </table>
                        </div>
                
                        <div id="content">
                            <div id="content_top">
                                <strong>你的<span>验证码</span>为:</strong>
                                <div id="verificationCode">
                                    <span>#emailCode</span>
                                </div>
                            </div>
                            <div id="content_bottom">
                                <small>
                                    注意：此操作可能会修改您的密码、登录邮箱或绑定手机。如非本人操作，请及时登录并修改密码以保证帐户安全
                                    <br>（工作人员不会向你索取此验证码，请勿泄漏！)
                                </small>
                            </div>
                        </div>
                        <div id="bottom">
                            <div>
                                <p>此为系统邮件，请勿回复<br>
                                    请保管好您的邮箱，避免账号被他人盗用
                                </p>
                                <p id="sign">——简单推送</p>
                            </div>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
            </body>
                    """;

    private static final String MESSAGE_HTML = """
            <!DOCTYPE html>
            <html lang="en" xmlns:th="http://www.thymeleaf.org">
            <head>
                <meta charset="UTF-8">
                <title>#title</title>
                <style>
                    table {
                        width: 700px;
                        margin: 0 auto;
                    }
                
                    #top {
                        width: 700px;
                        border-bottom: 1px solid #ccc;
                        margin: 0 auto 30px;
                    }
                
                    #top table {
                        font: 12px Tahoma, Arial, 宋体;
                        height: 40px;
                    }
                
                    #content {
                        width: 680px;
                        padding: 0 10px;
                        margin: 0 auto;
                    }
                
                    #content_top {
                        line-height: 1.5;
                        font-size: 14px;
                        margin-bottom: 25px;
                        color: #4d4d4d;
                    }
                
                    #content_top strong {
                        display: block;
                        margin-bottom: 15px;
                    }
                
                    #content_top strong span {
                        color: #f60;
                        font-size: 16px;
                    }
                
                    #verificationCode {
                        color: #f60;
                        font-size: 24px;
                    }
                
                    #content_bottom {
                        margin-bottom: 30px;
                    }
                
                    #content_bottom small {
                        display: block;
                        margin-bottom: 20px;
                        font-size: 12px;
                        color: #747474;
                    }
                
                    #bottom {
                        width: 700px;
                        margin: 0 auto;
                    }
                
                    #bottom div {
                        padding: 10px 10px 0;
                        border-top: 1px solid #ccc;
                        color: #747474;
                        margin-bottom: 20px;
                        line-height: 1.3em;
                        font-size: 12px;
                    }
                
                    #content_top strong span {
                        font-size: 18px;
                        color: #FE4F70;
                    }
                
                    #sign {
                        text-align: right;
                        font-size: 18px;
                        color: #FE4F70;
                        font-weight: bold;
                    }
                
                    #verificationCode {
                        height: 100px;
                        width: 680px;
                        text-align: center;
                        margin: 30px 0;
                    }
                
                    #verificationCode div {
                        height: 100px;
                        width: 680px;
                
                    }
                
                    .button {
                        color: #FE4F70;
                        margin-left: 10px;
                        height: 80px;
                        width: 80px;
                        resize: none;
                        font-size: 42px;
                        border: none;
                        outline: none;
                        padding: 10px 15px;
                        background: #ededed;
                        text-align: center;
                        border-radius: 17px;
                        box-shadow: 6px 6px 12px #cccccc,
                        -6px -6px 12px #ffffff;
                    }
                
                    .button:hover {
                        box-shadow: inset 6px 6px 4px #d1d1d1,
                        inset -6px -6px 4px #ffffff;
                    }
                
                </style>
            </head>
            <body>
            <table>
                <tbody>
                <tr>
                    <td>
                        <div id="top">
                            <table>
                                <tbody><tr><td></td></tr></tbody>
                            </table>
                        </div>
                
                        <div id="content">
                            <div id="content_top">
                                <div id="verificationCode">
                                    <span>#content</span>
                                </div>
                            </div>
                        </div>
                        <div id="bottom">
                            <div>
                                <p>此为系统邮件，请勿回复<br>
                                    请保管好您的邮箱，避免账号被他人盗用
                                </p>
                                <p id="sign">——简单推送</p>
                            </div>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
            </body>
                    """;



}
