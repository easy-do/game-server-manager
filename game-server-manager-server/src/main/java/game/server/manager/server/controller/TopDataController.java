package game.server.manager.server.controller;

import game.server.manager.server.service.TopDataServer;
import game.server.manager.common.vo.TopDataVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import game.server.manager.common.result.DataResult;
import game.server.manager.common.result.R;

/**
 * @author laoyu
 * @version 1.0
 * @description 统计数据
 * @date 2022/5/31
 */
@RestController
@RequestMapping("/top")
public class TopDataController {

    @Autowired
    private TopDataServer topDataServer;

    @GetMapping("/home")
    public R<TopDataVo> topData(){
        long userCount = topDataServer.userCount();
        long deployCount = topDataServer.deployCount();
        long applicationCount = topDataServer.applicationCount();
        TopDataVo topDataVo = TopDataVo.builder().userCount(userCount).deployCount(deployCount).applicationCount(applicationCount).build();
        return DataResult.ok(topDataVo);
    }
}
