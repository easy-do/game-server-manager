package plus.easydo.client.server;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.io.IoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import plus.easydo.client.config.SystemUtils;
import plus.easydo.client.model.ClientData;
import plus.easydo.client.model.SystemInfo;
import plus.easydo.client.utils.IpRegionSearchUtil;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.FileReader;
import java.util.Map;

/**
 * @author laoyu
 * @version 1.0
 */
@ApplicationScoped
public class ClientDataServer {

    private static final Logger logger = LoggerFactory.getLogger(ClientDataServer.class);

    private final ClientData clientData = new ClientData();

    @Inject
    SystemUtils systemUtils;



    public ClientData getClientData() {
        logger.info("loading pluginsData......");
        clientData.setClientId(systemUtils.getClientId());
        clientData.setVersion(systemUtils.getVersion());
//        clientData.setIp(IpRegionSearchUtil.searchServerIp());
        clientData.setEnv(getEnv());
//        clientData.setSystemInfo(new SystemInfo());
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
