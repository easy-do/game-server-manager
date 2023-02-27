package game.server.manager.uc.controller;


import cn.dev33.satoken.same.SaSameUtil;
import cn.dev33.satoken.stp.StpUtil;
import game.server.manager.log.SaveLog;
import game.server.manager.uc.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import game.server.manager.common.result.DataResult;
import game.server.manager.common.result.R;

/**
 * @author laoyu
 * @version 1.0
 * @description 邮箱相关
 * @date 2022/6/12
 */
@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/sendMailCode")
    @SaveLog(logType = "操作日志", moduleName = "邮件服务", description = "邮箱：?1", expressions = {"#p1"}, actionType = "发送邮件")
    public R<Object> sendMailCode(@RequestParam("email")String email){
        return DataResult.ok(emailService.sendMailCode(email));
    }


    /**
     * 向邮箱发送邮件
     *
     * @param email email
     * @param title title
     * @param content content
     * @return java.lang.String
     * @author laoyu
     * @date 2022/7/9
     */
    @PostMapping("/sendHtmlMail")
    @SaveLog(logType = "操作日志", moduleName = "邮件服务", description = "向邮箱发送邮件", expressions = {"#p1"}, actionType = "发送邮件")
    public R<String> sendHtmlMail(@RequestParam("email") String email, @RequestParam("title") String title, @RequestParam("content") String content){
        if(!StpUtil.isLogin()){
            // 不是用户请求的则校验 Id-Token 身份凭证
            SaSameUtil.checkCurrentRequestToken();
        }
        return DataResult.ok(emailService.sendHtmlMail(email,title,content));
    }

    /**
     * 向用户发送邮件
     *
     * @param userId userId
     * @param title title
     * @param content content
     * @return java.lang.String
     * @author laoyu
     * @date 2022/7/9
     */
    @GetMapping("/sendHtmlMailByUserId")
    @SaveLog(logType = "操作日志", moduleName = "邮件服务", description = "邮箱：?1", expressions = {"#p1"}, actionType = "发送邮件")
    public R<String> sendHtmlMailByUserId(@RequestParam("userId") Long userId, @RequestParam("title") String title, @RequestParam("content") String content){
        if(!StpUtil.isLogin()){
            // 不是用户请求的则校验 Id-Token 身份凭证
            SaSameUtil.checkCurrentRequestToken();
        }
        return DataResult.ok(emailService.sendHtmlMailByUserId(userId,title,content));
    }



}
