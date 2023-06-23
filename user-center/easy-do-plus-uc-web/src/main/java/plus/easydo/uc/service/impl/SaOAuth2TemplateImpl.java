package plus.easydo.uc.service.impl;

import cn.dev33.satoken.oauth2.SaOAuth2Manager;
import cn.dev33.satoken.oauth2.config.SaOAuth2Config;
import cn.dev33.satoken.oauth2.logic.SaOAuth2Template;
import cn.dev33.satoken.oauth2.model.SaClientModel;
import cn.hutool.core.text.CharSequenceUtil;
import plus.easydo.auth.ClientAuthModalEnum;
import plus.easydo.uc.entity.OauthClientDetails;
import plus.easydo.uc.mapper.OauthClientDetailsMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @author yuzhanfeng
 * @Date 2023-02-27 9:07
 * @Description sa-token oauth2template实现
 */
@Component
public class SaOAuth2TemplateImpl extends SaOAuth2Template {


    @Resource
    private OauthClientDetailsMapper authClientDetailsMapper;


    @Override
    public SaClientModel getClientModel(String clientId) {
        OauthClientDetails client = authClientDetailsMapper.selectById(clientId);
        if(Objects.isNull(client)){
            return null;
        }
        SaOAuth2Config config = SaOAuth2Manager.getConfig();
        List<String> grantTypes = CharSequenceUtil.split(client.getAuthorizedGrantTypes(), ",");
        Long accessTokenValidity = client.getAccessTokenValidity();
        Long refreshTokenValidity = client.getRefreshTokenValidity();
        Long clientTokenValidity = client.getClientTokenValidity();
        return new SaClientModel()
                .setClientId(client.getClientId())
                .setClientSecret(client.getClientSecret())
                .setAllowUrl(client.getRedirectUri())
                .setContractScope(client.getScope())
                .setIsAutoMode(true)
                .setAccessTokenTimeout(Objects.isNull(accessTokenValidity)? config.getAccessTokenTimeout():accessTokenValidity)
                .setClientTokenTimeout(Objects.isNull(clientTokenValidity)? config.getClientTokenTimeout():clientTokenValidity)
                .setRefreshTokenTimeout(Objects.isNull(refreshTokenValidity)? config.getRefreshTokenTimeout():refreshTokenValidity)
                .setPastClientTokenTimeout(Objects.isNull(accessTokenValidity)? config.getPastClientTokenTimeout():accessTokenValidity)
                .setIsCode(grantTypes.contains(ClientAuthModalEnum.AUTHORIZATION_CODE.getValue()))
                .setIsImplicit(grantTypes.contains(ClientAuthModalEnum.IMPLICIT.getValue()))
                .setIsPassword(grantTypes.contains(ClientAuthModalEnum.PASSWORD.getValue()))
                .setIsClient(grantTypes.contains(ClientAuthModalEnum.CLIENT_CREDENTIALS.getValue()));
    }

    @Override
    public String getOpenid(String clientId, Object loginId) {
        return clientId+"_"+loginId;
    }
}
