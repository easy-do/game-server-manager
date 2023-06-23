package plus.easydo.uc.service;



/**
 * @author laoyu
 * @version 1.0
 * @description 邮件服务
 * @date 2022/6/12
 */

public interface EmailService {

    /**
     * 发送邮箱验证码
     *
     * @param email email
     * @return plus.easydo.core.result.R<java.lang.Object>
     * @author laoyu
     * @date 2022/6/12
     */
    Object sendMailCode(String email);

    /**
     * 校验验证码
     *
     * @param email email
     * @param code code
     * @return void
     * @author laoyu
     * @date 2022/6/12
     */
    boolean checkCode(String email, String code);

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
    String sendHtmlMail(String email, String title, String content);

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
    String sendHtmlMailByUserId(Long userId, String title, String content);
}
