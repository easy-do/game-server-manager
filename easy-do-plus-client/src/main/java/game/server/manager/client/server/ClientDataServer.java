package plus.easydo.push.client.server;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.io.IoUtil;
import plus.easydo.push.client.config.SystemUtils;
import plus.easydo.push.client.model.ClientData;
import plus.easydo.push.client.model.SystemInfo;
import plus.easydo.push.client.utils.IpRegionSearchUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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



    public ClientData getClientData() {
        logger.info("loading pluginsData......");
        clientData.setClientId(systemUtils.getClientId());
        clientData.setVersion(systemUtils.getVersion());
        clientData.setIp(IpRegionSearchUtil.searchServerIp());;
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

    public Map<String, String> getEnv() {
        return System.getenv();
    }
}