package game.server.manager.server.controller;

import game.server.manager.common.mode.SyncData;
import game.server.manager.server.server.DefaultServerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author laoyu
 * @version 1.0
 */
@RestController
public class SyncController {

    @Autowired
    private DefaultServerContainer defaultServerContainer;

    @PostMapping("/sync")
    public Object sync(@RequestBody SyncData syncData){
        return defaultServerContainer.processData(syncData);
    }

}
