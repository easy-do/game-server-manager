package game.server.manager.client.server;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.io.IoUtil;
import com.alibaba.fastjson2.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import game.server.manager.client.config.SystemUtils;
import game.server.manager.common.mode.ClientData;
import game.server.manager.common.mode.SystemInfo;
import game.server.manager.common.utils.IpRegionSearchUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;

/**
 * @author laoyu
 * @version 1.0
 */
@Component
public class ClientDataServer {

    private static final Logger logger = LoggerFactory.getLogger(ClientDataServer.class);

    private final ClientData clientData = new ClientData();

    @Autowired
    private SystemUtils systemUtils;


    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public ClientData getClientData() {
        logger.info("loading pluginsData......");
        clientData.setClientId(systemUtils.getClientId());
        clientData.setVersion(systemUtils.getVersion());
        clientData.setIp(IpRegionSearchUtil.searchServerIp());
        clientData.setEnv(getEnv());
        clientData.setSystemInfo(new SystemInfo());
        clientData.setPort(systemUtils.getPort());
        return clientData;
    }

    public String getConfigFile(String configFilePath){
        try {
            FileReader fileReader = new FileReader(configFilePath);
            return IoUtil.read(fileReader);
        } catch (Exception e) {
            return "配置文件读取失败,"+ExceptionUtil.getMessage(e);
        }
    }

    public String getConfigJson(String path){
        try {
            FileReader fileReader = new FileReader(path);
            JSONObject json = GSON.fromJson(fileReader, JSONObject.class);
            return json.toJSONString();
        }catch (FileNotFoundException fileNotFoundException){
            return new JSONObject().toJSONString();
        }
    }


    public JSONObject getEnv() {
        Map<String, String> env = System.getenv();
        return new JSONObject(env);
    }
}
