package game.server.manager.server.server;


import game.server.manager.common.mode.SyncData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author laoyu
 * @version 1.0
 */
public abstract class AbstractDefaultServer {

    protected static Logger logger = LoggerFactory.getLogger(AbstractDefaultServer.class);

    /**
     * 功能描述
     *
     * @param syncData syncData
     * @return java.lang.Object
     * @author laoyu
     */
    abstract Object processData(SyncData syncData);
}
