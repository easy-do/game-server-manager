package plus.easydo.server.controller;

import plus.easydo.server.service.TopDataServer;
import plus.easydo.common.vo.TopDataVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plus.easydo.common.result.DataResult;
import plus.easydo.common.result.R;

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
