package game.server.manager.server.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import game.server.manager.server.entity.AppInfo;
import game.server.manager.mybatis.plus.qo.MpBaseQo;
import game.server.manager.mybatis.plus.result.MpDataResult;
import game.server.manager.mybatis.plus.result.MpResultUtil;
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
    public MpDataResult page(@RequestBody MpBaseQo mpBaseQo) {
        IPage<AppInfo> pageResult = appInfoService.storePage(mpBaseQo);
        long pageSize = pageResult.getSize();
        long current = pageResult.getCurrent();
        long startRow = ((pageResult.getCurrent() - 1L) * pageResult.getSize());
        long endRow = (pageResult.getCurrent() * pageResult.getSize());
        Long total = pageResult.getTotal();
        long pages = pageResult.getPages();
        List<List<AppInfo>> result = new ArrayList<>();
        List<AppInfo> records = pageResult.getRecords();
        int index = 0;
        int groupSize = 5;
        double forSize = Math.ceil((double)records.size() / (double)groupSize);
        if(!records.isEmpty()){
            for (int i = 0; i < forSize; i++) {
                result.add(records.stream().skip(index).limit(groupSize).collect(Collectors.toList()));
                index = index + 5;
            }
            return MpResultUtil.buildPageData(current, pageSize, startRow, endRow, total, pages,result);
        }else {
            return MpResultUtil.buildPageData(current, pageSize, startRow, endRow, total, pages, Collections.EMPTY_LIST);
        }
    }


}
