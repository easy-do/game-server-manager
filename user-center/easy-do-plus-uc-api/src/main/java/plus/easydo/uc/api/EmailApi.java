package plus.easydo.uc.api;


import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import plus.easydo.common.result.R;

/**
 * @author laoyu
 * @version 1.0
 * @description 邮箱相关
 * @date 2022/6/12
 */
@HttpExchange("/email")
public  interface EmailApi {

    String apiPath = "/email";

    @GetExchange("/sendMailCode")
    R<Object> sendMailCode(@RequestParam("email") String email);


    /**
     * 向邮箱发送邮件
     *
     * @param email   email
     * @param title   title
     * @param content content
     * @return java.lang.String
     * @author laoyu
     * @date 2022/7/9
     */
    @PostExchange("/sendHtmlMail")
    R<String> sendHtmlMail(@RequestParam("email") String email, @RequestParam("title") String title, @RequestParam("content") String content);

    /**
     * 向用户发送邮件
     *
     * @param userId  userId
     * @param title   title
     * @param content content
     * @return java.lang.String
     * @author laoyu
     * @date 2022/7/9
     */
    @GetExchange("/sendHtmlMailByUserId")
    R<String> sendHtmlMailByUserId(@RequestParam("userId") Long userId, @RequestParam("title") String title, @RequestParam("content") String content);


}
