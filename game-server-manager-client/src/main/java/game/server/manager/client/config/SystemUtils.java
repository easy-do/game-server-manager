package game.server.manager.client.config;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import game.server.manager.common.mode.ClientInitData;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${manager-url:https://manager.easydo.plus}")
    private String managerUrl;

    @Value("${client-id:no}")
    private String clientId;

    @Value("${app-id:0}")
    private String appId;

    @Value("${client_version:0}")
    private String version;

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

}
