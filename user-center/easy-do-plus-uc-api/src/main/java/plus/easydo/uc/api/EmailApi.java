package plus.easydo.uc.api;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import plus.easydo.common.result.R;

/**
 * @author laoyu
 * @version 1.0
 * @description 邮箱相关
 * @date 2022/6/12
 */
public interface EmailApi {

    String apiPath = "/email";

    public R<Object> sendMailCode(@RequestParam("email") String email);


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
    @PostMapping(apiPath + "/sendHtmlMail")
    public R<String> sendHtmlMail(@RequestParam("email") String email, @RequestParam("title") String title, @RequestParam("content") String content);

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
    @GetMapping(apiPath + "/sendHtmlMailByUserId")
    public R<String> sendHtmlMailByUserId(@RequestParam("userId") Long userId, @RequestParam("title") String title, @RequestParam("content") String content);


}
