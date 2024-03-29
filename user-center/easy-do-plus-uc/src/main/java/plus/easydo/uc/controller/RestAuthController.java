package plus.easydo.uc.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import plus.easydo.auth.AuthStateRedisCache;
import plus.easydo.auth.AuthorizationUtil;
import plus.easydo.auth.OauthConfigProperties;
import plus.easydo.log.SaveLog;
import plus.easydo.common.vo.UserInfoVo;
import plus.easydo.uc.dto.LoginModel;
import plus.easydo.uc.dto.RestPasswordModel;
import plus.easydo.uc.service.AuthorizationCodeService;
import plus.easydo.uc.service.LoginService;
import plus.easydo.uc.service.UserInfoService;
import lombok.Data;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.enums.scope.AuthGiteeScope;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.*;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import plus.easydo.common.result.DataResult;
import plus.easydo.common.result.R;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Objects;

/**
 * @author laoyu
 * @version 1.0
 * @date 2022/2/22
 */
@Data
@RestController
@RequestMapping("/oauth")
@EnableConfigurationProperties(OauthConfigProperties.class)
public class RestAuthController {

    @Autowired
    private OauthConfigProperties oauthConfigProperties;

    @Autowired
    private AuthStateRedisCache authStateRedisCache;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private AuthorizationCodeService authorizationCodeService;

    @Autowired
    private LoginService loginService;

    @Value("${system.be-domain:}")
    private String beDomain;

    @Autowired
    private AuthorizationUtil authorizationUtil;

    @SaveLog(saveDb = false, logType = "授权日志", moduleName = "授权服务", description = "跳转第三方授权: ?1", expressions = {"#p1"}, actionType = "跳转授权")
    @GetMapping("/render/{source}")
    public void renderAuth(@PathVariable("source") String source, HttpServletResponse response) throws IOException {
        AuthRequest authRequest = getAuthRequest(source);
        response.sendRedirect(authRequest.authorize(AuthStateUtils.createState()));
    }

    @SaveLog(saveDb = false, logType = "授权日志", moduleName = "授权服务", description = "三方平台授权验证: ?1", expressions = {"#p1"}, actionType = "三方授权验证")
    @GetMapping("/login/{source}")
    public R<String> login(@PathVariable("source") String source) {
        AuthRequest authRequest = getAuthRequest(source);
        return DataResult.ok(authRequest.authorize(AuthStateUtils.createState()), "ok");
    }

    @SaveLog(saveDb = false, logType = "授权日志", moduleName = "授权服务", description = "回调登录: ?1", expressions = {"#p1"}, actionType = "授权回调")
    @RequestMapping("/callback/{platform}")
    public void callback(@PathVariable("platform") String platform, AuthCallback callback, HttpServletResponse response) throws IOException {
        AuthRequest authRequest = getAuthRequest(platform);
        AuthResponse<AuthUser> authResponse = authRequest.login(callback);
        if (authResponse.ok()) {
            AuthUser authUser = authResponse.getData();
            String token = loginService.platformLogin(authUser);
            Cookie cookie = new Cookie("token",token);
            response.addCookie(cookie);
            response.sendRedirect(beDomain + "/login?token=" + token);
        } else {
            response.sendRedirect(beDomain + "/loginFailed");
        }
    }

    @PostMapping("/login")
    @SaveLog(logType = "授权日志", moduleName = "授权服务", description = "登录类型,?1", expressions = {"#p1.loginType"},  actionType = "登录")
    public R<String> login(@RequestBody @Validated LoginModel loginModel, HttpServletResponse response) {
        UserInfoVo userInfoVo = loginService.login(loginModel);
        if (Objects.nonNull(userInfoVo)) {
            Cookie cookie = new Cookie("token",userInfoVo.getToken());
            response.addCookie(cookie);
            return DataResult.ok(beDomain + "/login");
        } else {
            return DataResult.fail(beDomain + "/loginFailed");
        }
    }

    @SaCheckLogin
    @PostMapping("/userResetPassword")
    @SaveLog(logType = "授权日志", moduleName = "授权服务", description = "用户id,?1", expressions = {"#p1.userId"}, actionType = "重置密码")
    public R<Object> userResetPassword(@RequestBody @Validated RestPasswordModel restPasswordModel) {
        return userInfoService.userResetPassword(restPasswordModel)? DataResult.ok():DataResult.fail();
    }

    @SaCheckLogin
    @PostMapping("/resetPassword")
    @SaveLog(logType = "授权日志", moduleName = "授权服务", description = "用户id,?1", expressions = {"#p1.userId"}, actionType = "重置密码")
    public R<Object> resetPassword(@RequestBody @Validated RestPasswordModel restPasswordModel) {
        return userInfoService.resetPassword(restPasswordModel)? DataResult.ok():DataResult.fail();
    }


    @SaCheckLogin
    @GetMapping("/getUserInfo")
    public R<UserInfoVo> getUserInfo() {
        return DataResult.ok(authorizationUtil.getUser());

    }

    @SaCheckLogin
    @SaveLog(logType = "授权日志", moduleName = "授权服务", description = "重置密钥", actionType = "重置密钥")
    @GetMapping("/resetSecret")
    public R<Object> resetSecret(HttpServletResponse response) {
        boolean result = userInfoService.resetSecret();
        if(result){
            String token = StpUtil.getTokenValue();
            Cookie cookie = new Cookie("token", token);
            response.addCookie(cookie);
            return DataResult.ok(token);
        }else {
            return DataResult.fail();
        }
    }


    @SaCheckLogin
    @SaveLog(logType = "授权日志", moduleName = "授权服务", description = "绑定邮箱：?1", expressions = {"#p2"}, actionType = "绑定邮箱")
    @GetMapping("/bindingEmail")
    public R<Object> bindingEmail(@RequestParam("email") String email, @RequestParam("code") String code) {
        boolean result = userInfoService.bindingEmail(AuthorizationUtil.getUserId(), email, code);
        return result ? DataResult.ok() : DataResult.fail();
    }

    @SaCheckLogin
    @SaveLog(logType = "授权日志", moduleName = "授权服务", description = "退出登录", actionType = "退出登陆")
    @GetMapping("/logout")
    public R<Object> logout() {
        loginService.logout();
        return DataResult.ok();
    }

    @SaCheckLogin
    @SaveLog(logType = "授权日志", moduleName = "授权服务", description = "授权码：?1", expressions = {"#p1"}, actionType = "使用授权码")
    @GetMapping("/authorization/{authorizationCode}")
    public R<Object> authorization(@PathVariable("authorizationCode") String authorizationCode) {
        boolean result = userInfoService.authorization(authorizationCode);
        return result ? DataResult.ok() : DataResult.fail("授权失败");

    }

    private AuthDefaultRequest getAuthRequest(String platform) {
        return switch (platform) {
            case "dingtalk" -> new AuthDingTalkRequest(AuthConfig.builder()
                    .clientId(oauthConfigProperties.getClientId(platform))
                    .clientSecret(oauthConfigProperties.getSecret(platform))
                    .redirectUri(oauthConfigProperties.getRedirectUri(platform))
                    .build(), authStateRedisCache);
            case "gitee" -> new AuthGiteeRequest(AuthConfig.builder()
                    .clientId(oauthConfigProperties.getClientId(platform))
                    .clientSecret(oauthConfigProperties.getSecret(platform))
                    .redirectUri(oauthConfigProperties.getRedirectUri(platform))
                    .scopes(Collections.singletonList(AuthGiteeScope.USER_INFO.getScope()))
                    .build(), authStateRedisCache);
            case "alipay" -> new AuthAlipayRequest(AuthConfig.builder()
                    //APPID
                    .clientId(oauthConfigProperties.getClientId(platform))
                    //应用私钥
                    .clientSecret(oauthConfigProperties.getSecret(platform))
                    .redirectUri(oauthConfigProperties.getRedirectUri(platform))
                    .build(), oauthConfigProperties.getValue(platform, "alipayPublicKey"), authStateRedisCache);
            case "wechat_enterprise" -> new AuthWeChatEnterpriseQrcodeRequest(AuthConfig.builder()
                    .clientId(oauthConfigProperties.getClientId(platform))
                    .clientSecret(oauthConfigProperties.getSecret(platform))
                    .redirectUri(oauthConfigProperties.getRedirectUri(platform))
                    .agentId(oauthConfigProperties.getValue(platform, "agentId"))
                    .build(), authStateRedisCache);
            case "huawei" -> new AuthHuaweiRequest(AuthConfig.builder()
                    .clientId(oauthConfigProperties.getClientId(platform))
                    .clientSecret(oauthConfigProperties.getSecret(platform))
                    .redirectUri(oauthConfigProperties.getRedirectUri(platform))
                    .build(), authStateRedisCache);
            case "github" -> new AuthGithubRequest(AuthConfig.builder()
                    .clientId(oauthConfigProperties.getClientId(platform))
                    .clientSecret(oauthConfigProperties.getSecret(platform))
                    .redirectUri(oauthConfigProperties.getRedirectUri(platform))
                    .build(), authStateRedisCache);
            case "oschina" -> new AuthOschinaRequest(AuthConfig.builder()
                    .clientId(oauthConfigProperties.getClientId(platform))
                    .clientSecret(oauthConfigProperties.getSecret(platform))
                    .redirectUri(oauthConfigProperties.getRedirectUri(platform))
                    .build(), authStateRedisCache);
            case "baidu" -> new AuthBaiduRequest(AuthConfig.builder()
                    .clientId(oauthConfigProperties.getClientId(platform))
                    .clientSecret(oauthConfigProperties.getSecret(platform))
                    .redirectUri(oauthConfigProperties.getRedirectUri(platform))
                    .build(), authStateRedisCache);
            case "coding" -> new AuthCodingRequest(AuthConfig.builder()
                    .clientId(oauthConfigProperties.getClientId(platform))
                    .clientSecret(oauthConfigProperties.getSecret(platform))
                    .redirectUri(oauthConfigProperties.getRedirectUri(platform))
                    .domainPrefix(oauthConfigProperties.getValue(platform, "team"))
                    .build());
            default -> null;
        };
    }
}
