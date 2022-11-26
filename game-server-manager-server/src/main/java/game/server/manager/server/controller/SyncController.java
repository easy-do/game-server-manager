package game.server.manager.server.controller;

import game.server.manager.common.mode.SyncData;
import game.server.manager.handler.HandlerServiceContainer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * @author laoyu
 * @version 1.0
 */
@RestController
public class SyncController {

    @Resource
    private HandlerServiceContainer<SyncData,Object> handlerServiceContainer;

    @PostMapping("/sync")
    public Object sync(@RequestBody SyncData syncData){
        return handlerServiceContainer.handler(syncData.getKey(),syncData);
    }

}
