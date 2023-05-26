package plus.easydo.client.config;



import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import lombok.Data;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import plus.easydo.client.model.ClientInitData;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

/**
 * @author laoyu
 * @version 1.0
 */
@Data
@ApplicationScoped
public class SystemUtils {

    public static final Map<String,Object> VALUES = new HashMap<>();

    @Inject
    Config config;

    @ConfigProperty(name = "manager-url", defaultValue = "https://manager.easydo.plus")
    String managerUrl;

    @ConfigProperty(name = "server-socket-url", defaultValue = "https://manager.easydo.plus")
    String serverSocketUrl;

    @ConfigProperty(name = "client-id", defaultValue = "no")
    String clientId;

    @ConfigProperty(name = "app-id", defaultValue = "0")
    String appId;

    @ConfigProperty(name = "client_version", defaultValue = "0")
    String version;

    @ConfigProperty(name = "secret", defaultValue = "no")
    String secret;

    public void init(ClientInitData clientInitData){
        String publicKey = clientInitData.getPublicKey();
        VALUES.put("publicKey",publicKey);
        VALUES.put("clientInitData",clientInitData);
        VALUES.put("rsa",new RSA(null,publicKey));
    }

    public String encrypt(String content){
        return getRsa().encryptBase64(content.getBytes(), KeyType.PublicKey);
    }

    public String decrypt(String encryptStr){
        byte[] data = getRsa().decrypt(encryptStr.getBytes(), KeyType.PublicKey);
        return StrUtil.str(data, CharsetUtil.CHARSET_UTF_8);
    }

    public String getPublicKey() {
        return (String) VALUES.get("publicKey");
    }

    public RSA getRsa() {
        return (RSA) VALUES.get("rsa");
    }

    public ClientInitData getClientInitData() {
        return (ClientInitData) VALUES.get("clientInitData");
    }

    public String getPort() {
        return config.getValue("quarkus.http.port",String.class);
    }

}
