package plus.easydo.server.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import plus.easydo.common.result.MpDataResult;
import plus.easydo.dao.result.MpResultUtil;
import plus.easydo.server.qo.server.ApplicationQo;
import plus.easydo.server.service.ApplicationService;
import plus.easydo.server.vo.server.ApplicationVo;
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
