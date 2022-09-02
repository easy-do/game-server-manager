package game.server.manager.plugins.config;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import game.server.manager.common.mode.PluginsInitData;
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

    @Value("${application-id:}")
    private String applicationId;

    @Value("${app-id:}")
    private String appId;

    @Value("${app-version:}")
    private String appVersion;

    public void init(PluginsInitData pluginsInitData){
        String publicKey = pluginsInitData.getPublicKey();
        VALUES.put("publicKey",publicKey);
        VALUES.put("pluginsInitData",pluginsInitData);
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

    public PluginsInitData getPluginsInitData() {
        return (PluginsInitData) VALUES.get("pluginsInitData");
    }

}
