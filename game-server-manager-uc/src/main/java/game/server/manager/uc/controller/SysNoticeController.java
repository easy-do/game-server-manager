package game.server.manager.uc.controller;


import cn.dev33.satoken.annotation.SaCheckPermission;
import game.server.manager.web.base.BaseController;
import game.server.manager.uc.qo.SysNoticeQo;
import game.server.manager.uc.service.SysNoticeService;
import game.server.manager.uc.vo.SysNoticeVo;
import game.server.manager.uc.dto.SysNoticeDto;
import  game.server.manager.uc.entity.SysNotice;
import game.server.manager.log.SaveLog;
import game.server.manager.mybatis.plus.result.MpDataResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import game.server.manager.common.result.R;
import game.server.manager.common.vaild.Insert;
import game.server.manager.common.vaild.Update;

import java.util.List;


/**
 * 通知公告Controller
 * 
 * @author yuzhanfeng
 * @date 2022-10-03 17:39:25
 */
@RestController
@RequestMapping("/notice")
public class SysNoticeController extends BaseController<SysNoticeService,SysNotice,Long, SysNoticeQo,SysNoticeVo,SysNoticeDto> {

    /**
     * 获取所有通知公告列表
     */
    @RequestMapping("/list")
    @Override
    public R<List<SysNoticeVo>> list() {
        return super.list();
    }

    /**
     * 分页条件查询通知公告列表
     */
    @PostMapping("/page")
    @Override
    public MpDataResult page(@RequestBody SysNoticeQo sysNoticeQo) {
        return super.page(sysNoticeQo);
    }


    /**
     * 获取通知公告详细信息
     */
    @GetMapping("/info/{id}")
    @Override
    public R<SysNoticeVo> info(@PathVariable("id")Long id) {
        return super.info(id);
    }

    /**
     * 新增通知公告
     */
    @SaCheckPermission("system:uc:notice:add")
    @PostMapping("/add")
    @SaveLog(logType = "操作日志", moduleName = "通知公告", description = "添加通知公告", actionType = "添加")
    @Override
    public R<Object> add(@RequestBody @Validated({Insert.class}) SysNoticeDto sysNoticeDto) {
        return super.add(sysNoticeDto);
    }

    /**
     * 修改通知公告
     */
    @SaCheckPermission("system:uc:notice:update")
    @PostMapping("/update")
    @SaveLog(logType = "操作日志", moduleName = "通知公告", description = "编辑通知公告: ?1", expressions = {"#p1.id"},actionType = "编辑")
    @Override
    public R<Object> update(@RequestBody @Validated({Update.class}) SysNoticeDto sysNoticeDto) {
        return super.update(sysNoticeDto);
    }

    /**
     * 删除通知公告
     */
    @SaCheckPermission("system:uc:notice:remove")
	@GetMapping("/remove/{id}")
    @SaveLog(logType = "操作日志", moduleName = "通知公告", description = "删除通知公告: ?1", expressions = {"#p1"}, actionType = "删除")
    @Override
    public R<Object> remove(@PathVariable("id")Long id) {
        return super.remove(id);
    }
}
