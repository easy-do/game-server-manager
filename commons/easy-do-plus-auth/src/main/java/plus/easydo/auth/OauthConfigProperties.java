package plus.easydo.auth;

import plus.easydo.common.exception.ExceptionFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;
import java.util.Objects;

/**
 * @author laoyu
 * @version 1.0
 * @date 2022/2/22
 */
@ConfigurationProperties(prefix = "system.easy-push")
public class OauthConfigProperties {

    private Map<String, Map<String, String>> clients;

    public String getClientId(String platform){
        Map<String, String> client = getClient(platform);
        if(Objects.nonNull(client)){
            return client.get("clientId");
        }
        throw ExceptionFactory.baseException("not get platform");
    }
    public String getSecret(String platform){
        Map<String, String> client = getClient(platform);
        if(Objects.nonNull(client)){
            return client.get("secret");
        }
        throw ExceptionFactory.baseException("not get platform");
    }
    public String getRedirectUri(String platform){
        Map<String, String> client = getClient(platform);
        if(Objects.nonNull(client)){
            return client.get("redirectUri");
        }
        throw ExceptionFactory.baseException("not get platform");
    }

    public String getValue(String platform, String key) {
        Map<String, String> client = getClient(platform);
        if(Objects.nonNull(client)){
            return client.get(key);
        }
        throw ExceptionFactory.baseException("not get platform");
    }

    private Map<String, String> getClient(String platform) {
        if (Objects.isNull(platform)) {
            throw ExceptionFactory.baseException("platform is null");
        }
        return clients.get(platform);
    }


    public Map<String, Map<String, String>> getClients() {
        return clients;
    }

    public void setClients(Map<String, Map<String, String>> clients) {
        this.clients = clients;
    }
}
