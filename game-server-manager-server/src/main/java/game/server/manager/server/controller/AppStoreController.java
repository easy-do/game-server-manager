package game.server.manager.server.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import game.server.manager.mybatis.plus.result.MpDataResult;
import game.server.manager.mybatis.plus.result.MpResultUtil;
import game.server.manager.server.qo.ApplicationQo;
import game.server.manager.server.service.ApplicationService;
import game.server.manager.server.vo.ApplicationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author laoyu
 * @version 1.0
 * @date 2022/6/28
 */
@RestController
@RequestMapping("/appStore")
public class AppStoreController {

    @Autowired
    private ApplicationService applicationService;



    @PostMapping("/page")
    public MpDataResult page(@RequestBody ApplicationQo applicationQo) {
        IPage<ApplicationVo> pageResult = applicationService.storePage(applicationQo);
        return MpResultUtil.buildPage(pageResult);
    }


}
