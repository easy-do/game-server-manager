package game.server.manager.auth;



import cn.dev33.satoken.oauth2.logic.SaOAuth2Template;
import cn.dev33.satoken.oauth2.model.SaClientModel;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.stereotype.Component;

/**
 * @author laoyu
 * @version 1.0
 * @description Sa-Token OAuth2.0 整合实现
 * @date 2022/9/1
 */
@Component
public class SaOAuth2TemplateImpl extends SaOAuth2Template {

    /**
     * 根据ClientId 和 LoginId 获取openid
     *
     * @param clientId clientId
     * @return cn.dev33.satoken.oauth2.model.SaClientModel
     * @author laoyu
     * @date 2022/9/1
     */
    @Override
    public SaClientModel getClientModel(String clientId) {
        // 此为模拟数据，真实环境需要从数据库查询
        if("1001".equals(clientId)) {
            return new SaClientModel()
                    .setClientId("10001")
                    .setClientSecret("aaaa-bbbb-cccc-dddd-eeee")
                    .setAllowUrl("*")
                    .setContractScope("userinfo")
                    .setIsAutoMode(true);
        }
        return null;
    }

    /**
     * 根据ClientId 和 LoginId 获取openid
     *
     * @param clientId clientId
     * @param loginId loginId
     * @return java.lang.String
     * @author laoyu
     * @date 2022/9/1
     */
    @Override
    public String getOpenid(String clientId, Object loginId) {
        // 此为模拟数据，真实环境需要从数据库查询
        return "gr_SwoIN0MC1ewxHX_vfCW3BothWDZMMtx__";
    }

    /**
     * 重写 Access-Token 生成策略：复用登录会话的Token
     *
     * @param clientId clientId
     * @param loginId loginId
     * @param scope scope
     * @return java.lang.String
     * @author laoyu
     * @date 2022/9/1
     */
    @Override
    public String randomAccessToken(String clientId, Object loginId, String scope) {
        String tokenValue = StpUtil.createLoginSession(loginId);
        return tokenValue;
    }

}

