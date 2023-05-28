package plus.easydo.client.config;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import plus.easydo.client.model.ClientInitData;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author laoyu
 * @version 1.0
 */
@Data
@Component
public class SystemUtils {

    public static final Map<String,Object> VALUES = new HashMap<>();

    @Autowired
    Environment environment;

    @Value("${manager-url:https://manager.easydo.plus}")
    private String managerUrl;

    @Value("${server-socket-url:https://manager.easydo.plus}")
    private String serverSocketUrl;

    @Value("${client-id:no}")
    private String clientId;

    @Value("${app-id:0}")
    private String appId;

    @Value("${client-version:0}")
    private String version;

    @Value("${secret:}")
    private String secret;

    @Value("${client-type:host}")
    private String clientType;

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
        return environment.getProperty("local.server.port");
    }



}
