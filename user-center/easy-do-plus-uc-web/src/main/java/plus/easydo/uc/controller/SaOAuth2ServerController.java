package plus.easydo.uc.controller;

import cn.dev33.satoken.oauth2.config.SaOAuth2Config;
import cn.dev33.satoken.oauth2.logic.SaOAuth2Handle;
import cn.dev33.satoken.oauth2.logic.SaOAuth2Util;
import cn.dev33.satoken.oauth2.model.AccessTokenModel;
import cn.dev33.satoken.util.SaResult;
import plus.easydo.common.enums.LoginTypeEnums;
import plus.easydo.common.result.DataResult;
import plus.easydo.common.result.R;
import plus.easydo.common.vo.UserInfoVo;
import plus.easydo.uc.dto.LoginModel;
import plus.easydo.uc.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author yuzhanfeng
 * @Date 2023-02-27 10:27
 * @Description oauth2授权控制
 */
@RestController
public class SaOAuth2ServerController {


    @Autowired
    private LoginService loginService;

    /**
     * 根据access_token获取用户信息
     *
     * @return java.lang.Object
     * @author laoyu
     * @date 2023-02-27
     */
    @RequestMapping("/oauth2/userinfo")
    public R<UserInfoVo> userinfo(@RequestParam("access_token")String accessToken) {
        AccessTokenModel accessTokenModel = SaOAuth2Util.checkAccessToken(accessToken);
        Map<String, Object> map = accessTokenModel.toLineMap();
        String openId = (String) map.get("openid");
        UserInfoVo userInfoVo = loginService.getUserInfoByOpenId(openId);
        return DataResult.ok(userInfoVo);
    }

    /**
     * 处理所有OAuth相关请求
     *
     * @return java.lang.Object
     * @author laoyu
     * @date 2023-02-27
     */
    @RequestMapping("/oauth2/*")
    public Object request() {
        return SaOAuth2Handle.serverRequest();
    }

    /**
     * a-OAuth2 定制化配置
     *
     * @param cfg cfg
     * @author laoyu
     * @date 2023-02-27
     */
    @Autowired
    public void setSaOAuth2Config(SaOAuth2Config cfg) {
        cfg.
                // 配置：未登录时返回的View
                        setNotLoginView(() ->
                                "当前会话在SSO-Server端尚未登录，请先访问"
                                + "<a href='/oauth2/doLogin?name=sa&pwd=123456' target='_blank'> doLogin登录 </a>"
                                + "进行登录之后，刷新页面开始授权").
                // 配置：确认授权时返回的View
                        setConfirmView((clientId, scope) ->
                                "<p>应用 " + clientId + " 请求授权：" + scope + "</p>"
                                + "<p>请确认：<a href='/oauth2/doConfirm?client_id=" + clientId + "&scope=" + scope + "' target='_blank'> 确认授权 </a></p>"
                                + "<p>确认之后刷新页面</p>")
                // 配置：登录处理函数
                .setDoLoginHandle((name, pwd) -> {
                    LoginModel loginModel = LoginModel.builder()
                            .loginType(LoginTypeEnums.PASSWORD.getType())
                            .userName(name)
                            .password(pwd)
                            .build();
                    UserInfoVo suerInfoVo = loginService.login(loginModel);
                    return SaResult.data(suerInfoVo);
                });
    }

}
