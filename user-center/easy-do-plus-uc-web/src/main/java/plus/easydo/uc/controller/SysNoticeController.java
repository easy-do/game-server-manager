package plus.easydo.uc.controller;


import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.zhxu.bs.BeanSearcher;
import cn.zhxu.bs.SearchResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;
import plus.easydo.common.result.DataResult;
import plus.easydo.dao.result.MpResultUtil;
import plus.easydo.uc.api.SysNoticeApi;
import plus.easydo.uc.mapstruct.SysNoticeMapstruct;
import plus.easydo.uc.service.SysNoticeService;
import plus.easydo.uc.vo.SysNoticeVo;
import plus.easydo.uc.dto.SysNoticeDto;
import plus.easydo.uc.entity.SysNotice;
import plus.easydo.log.SaveLog;
import plus.easydo.common.result.MpDataResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plus.easydo.common.result.R;
import plus.easydo.common.vaild.Insert;
import plus.easydo.common.vaild.Update;

import java.util.List;
import java.util.Map;

import static plus.easydo.uc.api.SysNoticeApi.apiPath;


/**
 * 通知公告Controller
 *
 * @author yuzhanfeng
 * @date 2022-10-03 17:39:25
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(apiPath)
public class SysNoticeController implements SysNoticeApi {

    private final  SysNoticeService baseService;

    private final BeanSearcher beanSearcher;

    /**
     * 获取所有通知公告列表
     */
    @GetMapping("/list")
    public R<List<SysNoticeVo>> list(@RequestParam(required = false) Map<String, Object> queryParam) {
        List<SysNotice> result = beanSearcher.searchList(SysNotice.class, queryParam);
        return DataResult.ok(SysNoticeMapstruct.INSTANCE.entityToVo(result));
    }

    /**
     * 分页条件查询通知公告列表
     */
    @PostMapping("/page")
    public MpDataResult page(@RequestBody Map<String,Object> queryParam) {
        SearchResult<SysNotice> result = beanSearcher.search(SysNotice.class, queryParam);
        List<SysNoticeVo> voList = SysNoticeMapstruct.INSTANCE.entityToVo(result.getDataList());
        return MpResultUtil.buildPage(voList, (Long) result.getTotalCount());
    }

    /**
     * 获取通知公告详细信息
     */
    @GetMapping("/info/{id}")
    public R<SysNoticeVo> info(@PathVariable("id")Long id) {
            SysNotice entity  = baseService.getById(id);
            return DataResult.ok(SysNoticeMapstruct.INSTANCE.entityToVo(entity));
    }

    /**
     * 新增通知公告
     */
    @SaCheckPermission("notice:add")
    @PostMapping("/add")
    @SaveLog(logType = "操作日志", moduleName = "通知公告", description = "添加通知公告", actionType = "添加")
    public R<Object> add(@RequestBody @Validated({Insert.class}) SysNoticeDto sysNoticeDto) {
        SysNotice entity = SysNoticeMapstruct.INSTANCE.dtoToEntity(sysNoticeDto);
        return baseService.save(entity)? DataResult.ok():DataResult.fail();
    }

    /**
     * 修改通知公告
     */
    @SaCheckPermission("notice:update")
    @PostMapping("/update")
    @SaveLog(logType = "操作日志", moduleName = "通知公告", description = "编辑通知公告: ?1", expressions = {"#p1.id"},actionType = "编辑")
    public R<Object> update(@RequestBody @Validated({Update.class}) SysNoticeDto sysNoticeDto) {
        SysNotice entity = SysNoticeMapstruct.INSTANCE.dtoToEntity(sysNoticeDto);
        return baseService.updateById(entity)? DataResult.ok():DataResult.fail();
    }

    /**
     * 删除通知公告
     */
    @SaCheckPermission("notice:remove")
	@GetMapping("/remove/{id}")
    @SaveLog(logType = "操作日志", moduleName = "通知公告", description = "删除通知公告: ?1", expressions = {"#p1"}, actionType = "删除")
    public R<Object> remove(@PathVariable("id")Long id) {
        return baseService.removeById(id)? DataResult.ok():DataResult.fail();
    }
}
