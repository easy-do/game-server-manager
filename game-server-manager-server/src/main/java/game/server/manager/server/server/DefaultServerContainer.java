package game.server.manager.server.server;

import game.server.manager.common.mode.SyncData;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * @author laoyu
 * @version 1.0
 */
@Component
public class DefaultServerContainer extends HashMap<String, AbstractDefaultServer> {

    public AbstractDefaultServer getServer(String key){
        return get(key);
    }

    public Object processData(SyncData syncData){
        return getServer(syncData.getKey()).processData(syncData);
    }
}
