package game.server.manager.server.handler;

import game.server.manager.common.mode.SyncData;
import game.server.manager.common.result.DataResult;
import game.server.manager.handler.AbstractHandlerService;
import game.server.manager.handler.annotation.HandlerService;



/**
 * @author laoyu
 * @version 1.0
 */
@HandlerService("up")
public class PluginsUpHandlerService extends AbstractHandlerService<SyncData,Object> {


    @Override
    public Object handler(SyncData syncData) {
         return DataResult.ok();
    }


}
