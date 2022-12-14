package game.server.manager.uc.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import game.server.manager.auth.AuthStateRedisCache;
import game.server.manager.auth.AuthorizationUtil;
import game.server.manager.auth.OauthConfigProperties;
import game.server.manager.log.SaveLog;
import game.server.manager.common.vo.UserInfoVo;
import game.server.manager.uc.dto.LoginModel;
import game.server.manager.uc.dto.RestPasswordModel;
import game.server.manager.uc.service.AuthorizationCodeService;
import game.server.manager.uc.service.LoginService;
import game.server.manager.uc.service.UserInfoService;
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
import game.server.manager.common.result.DataResult;
import game.server.manager.common.result.R;

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

    @SaveLog(saveDb = false, logType = "????????????", moduleName = "????????????", description = "?????????????????????: ?1", expressions = {"#p1"}, actionType = "????????????")
    @GetMapping("/render/{source}")
    public void renderAuth(@PathVariable("source") String source, HttpServletResponse response) throws IOException {
        AuthRequest authRequest = getAuthRequest(source);
        response.sendRedirect(authRequest.authorize(AuthStateUtils.createState()));
    }

    @SaveLog(saveDb = false, logType = "????????????", moduleName = "????????????", description = "????????????????????????: ?1", expressions = {"#p1"}, actionType = "??????????????????")
    @GetMapping("/login/{source}")
    public R<String> login(@PathVariable("source") String source) {
        AuthRequest authRequest = getAuthRequest(source);
        return DataResult.ok(authRequest.authorize(AuthStateUtils.createState()), "ok");
    }

    @SaveLog(saveDb = false, logType = "????????????", moduleName = "????????????", description = "????????????: ?1", expressions = {"#p1"}, actionType = "????????????")
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
    @SaveLog(logType = "????????????", moduleName = "????????????", description = "????????????,?1", expressions = {"#p1.loginType"},  actionType = "??????")
    public R<String> login(@RequestBody @Validated LoginModel loginModel, HttpServletResponse response) {
        UserInfoVo userInfoVo = loginService.login(loginModel);
        if (Objects.nonNull(userInfoVo)) {
            Cookie cookie = new Cookie("token",userInfoVo.getToken());
            response.addCookie(cookie);
            return DataResult.ok(beDomain + "/login?token=" + userInfoVo.getToken());
        } else {
            return DataResult.fail(beDomain + "/loginFailed");
        }
    }


    @SaCheckLogin
    @PostMapping("/resetPassword")
    @SaveLog(logType = "????????????", moduleName = "????????????", description = "??????id,?1", expressions = {"#p1.userId"}, actionType = "????????????")
    public R<Object> resetPassword(@RequestBody @Validated RestPasswordModel restPasswordModel) {
        return userInfoService.resetPassword(restPasswordModel)? DataResult.ok():DataResult.fail();
    }


    @SaCheckLogin
    @GetMapping("/getUserInfo")
    public R<UserInfoVo> getUserInfo() {
        return DataResult.ok(AuthorizationUtil.getUser());

    }

    @SaCheckLogin
    @SaveLog(logType = "????????????", moduleName = "????????????", description = "????????????", actionType = "????????????")
    @GetMapping("/resetSecret")
    public R<Object> resetSecret() {
        return userInfoService.resetSecret()?DataResult.ok():DataResult.fail();
    }


    @SaCheckLogin
    @SaveLog(logType = "????????????", moduleName = "????????????", description = "????????????????1", expressions = {"#p2"}, actionType = "????????????")
    @GetMapping("/bindingEmail")
    public R<Object> bindingEmail(@RequestParam("email") String email, @RequestParam("code") String code) {
        boolean result = userInfoService.bindingEmail(AuthorizationUtil.getUserId(), email, code);
        return result ? DataResult.ok() : DataResult.fail();
    }

    @SaCheckLogin
    @SaveLog(logType = "????????????", moduleName = "????????????", description = "????????????", actionType = "????????????")
    @GetMapping("/logout")
    public R<Object> logout() {
        loginService.logout();
        return DataResult.ok();
    }

    @SaCheckLogin
    @SaveLog(logType = "????????????", moduleName = "????????????", description = "?????????????1", expressions = {"#p1"}, actionType = "???????????????")
    @GetMapping("/authorization/{authorizationCode}")
    public R<Object> authorization(@PathVariable("authorizationCode") String authorizationCode) {
        boolean result = userInfoService.authorization(AuthorizationUtil.getUser(), authorizationCode);
        return result ? DataResult.ok() : DataResult.fail("????????????");

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
                    //????????????
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
