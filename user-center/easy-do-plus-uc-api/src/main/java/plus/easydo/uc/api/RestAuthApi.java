package plus.easydo.uc.api;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import plus.easydo.common.result.R;
import plus.easydo.common.vo.UserInfoVo;
import plus.easydo.uc.dto.LoginModel;
import plus.easydo.uc.dto.RestPasswordModel;

import java.io.IOException;

/**
 * @author laoyu
 * @version 1.0
 * @description 授权登录相关api
 * @date 2023/6/24
 */
@HttpExchange("/oauth")
public interface RestAuthApi {

    String apiPath = "/oauth";

    @GetExchange("/render/{source}")
    void renderAuth(@PathVariable("source") String source, HttpServletResponse response) throws IOException;

    @GetExchange("/login/{source}")
    R<String> platformLogin(@PathVariable("source") String source);
    
    @PostExchange("/login")
    R<String> login(@RequestBody @Validated LoginModel loginModel, HttpServletResponse response);

    @PostExchange("/userResetPassword")
    R<Object> userResetPassword(@RequestBody @Validated RestPasswordModel restPasswordModel);

    @PostExchange("/resetPassword")
    R<Object> resetPassword(@RequestBody @Validated RestPasswordModel restPasswordModel);

    @GetExchange("/getUserInfo")
    R<UserInfoVo> getUserInfo();

    @GetExchange("/resetSecret")
    R<Object> resetSecret(HttpServletResponse response);

    @GetExchange("/bindingEmail")
    R<Object> bindingEmail(@RequestParam("email") String email, @RequestParam("code") String code);

    @GetExchange("/logout")
    R<Object> logout();

    @GetExchange("/authorization/{authorizationCode}")
    R<Object> authorization(@PathVariable("authorizationCode") String authorizationCode);
}
