package plus.easydo.api;

import plus.easydo.common.result.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author yuzhanfeng
 */
public interface EmailApi {

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
    @GetMapping("/sendHtmlMail")
    R<String> sendHtmlMail(@RequestParam("email") String email, @RequestParam("title") String title, @RequestParam("content") String content);

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
    R<String> sendHtmlMailByUserId(@RequestParam("userId") Long userId, @RequestParam("title") String title, @RequestParam("content") String content);
}
