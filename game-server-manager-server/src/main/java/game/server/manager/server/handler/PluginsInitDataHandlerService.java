package game.server.manager.server.handler;

import game.server.manager.common.mode.SyncData;
import game.server.manager.common.result.DataResult;
import game.server.manager.handler.AbstractHandlerService;
import game.server.manager.handler.annotation.HandlerService;



/**
 * @author laoyu
 * @version 1.0
 * @date 2022/7/31
 */
@HandlerService("pluginsInitData")
public class PluginsInitDataHandlerService extends AbstractHandlerService<SyncData,Object> {


    @Override
    public Object handler(SyncData syncData) {
            return DataResult.ok();
    }
}
