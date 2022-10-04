package game.server.manager.server.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import game.server.manager.server.entity.AppInfo;
import game.server.manager.mybatis.plus.qo.MpBaseQo;
import game.server.manager.mybatis.plus.result.MpDataResult;
import game.server.manager.mybatis.plus.result.MpResultUtil;
import game.server.manager.server.qo.AppInfoQo;
import game.server.manager.server.service.AppInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author laoyu
 * @version 1.0
 * @date 2022/6/28
 */
@RestController
@RequestMapping("/appStore")
public class AppStoreController {

    @Autowired
    private AppInfoService appInfoService;



    @PostMapping("/page")
    public MpDataResult page(@RequestBody AppInfoQo mpBaseQo) {
        IPage<AppInfo> pageResult = appInfoService.storePage(mpBaseQo);
        return MpResultUtil.buildPage(pageResult);
    }


}
