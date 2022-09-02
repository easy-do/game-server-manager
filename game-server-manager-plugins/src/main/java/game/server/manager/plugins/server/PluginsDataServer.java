package game.server.manager.plugins.server;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.text.CharSequenceUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import game.server.manager.plugins.config.SystemUtils;
import game.server.manager.common.mode.PluginsData;
import game.server.manager.common.mode.PluginsInitData;
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
public class PluginsDataServer {

    private static final Logger logger = LoggerFactory.getLogger(PluginsDataServer.class);

    private final PluginsData pluginsData = new PluginsData();

    @Autowired
    private SystemUtils systemUtils;


    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public PluginsData getPluginsData() {
        logger.info("loading pluginsData......");
        pluginsData.setApplicationId(systemUtils.getApplicationId());
        pluginsData.setVersion(systemUtils.getAppVersion());
        pluginsData.setIp(IpRegionSearchUtil.searchServerIp());
        pluginsData.setEnv(getEnv());
        pluginsData.setSystemInfo(new SystemInfo());
        PluginsInitData iniData = systemUtils.getPluginsInitData();
        String configFilePath = iniData.getConfigFilePath();
        if(CharSequenceUtil.isNotEmpty(configFilePath)){
            pluginsData.setConfig(getConfigFile(configFilePath));
        }
        return pluginsData;
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


    public String getEnv() {
        Map<String, String> env = System.getenv();
        return JSON.toJSONString(env);
    }
}
