package game.server.manager.server.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import game.server.manager.web.base.BaseController;
import game.server.manager.server.dto.DiscussionDto;
import game.server.manager.server.entity.Discussion;
import game.server.manager.log.SaveLog;
import game.server.manager.mybatis.plus.qo.MpBaseQo;
import game.server.manager.mybatis.plus.result.MpDataResult;
import game.server.manager.mybatis.plus.result.MpResultUtil;
import game.server.manager.server.service.DiscussionService;
import game.server.manager.common.vo.DiscussionVo;
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


/**
 * @author laoyu
 * @version 1.0
 * @description 讨论交流
 * @date 2022/7/3
 */
@RestController
@RequestMapping("/discussion")
public class DiscussionController extends BaseController<DiscussionService, Discussion,Long, DiscussionVo, DiscussionDto> {


    @PostMapping("/page")
    public MpDataResult page(@RequestBody MpBaseQo mpBaseQo) {
        return super.page(mpBaseQo);
    }

    @SaCheckLogin
    @PostMapping("/managerPage")
    public MpDataResult managerPage(@RequestBody MpBaseQo mpBaseQo) {
       return MpResultUtil.buildPage(baseService.managerPage(mpBaseQo));
    }

    @RequestMapping("/info/{id}")
    public R<DiscussionVo> info(@PathVariable("id") Long id) {
        return super.info(id);
    }

    @SaCheckLogin
    @PostMapping("/add")
    @SaveLog(logType = "操作日志", moduleName = "讨论话题", description = "添加话题: ?1",expressions = {"#p1.appName"}, actionType = "添加")
    public R<Object> add(@RequestBody @Validated({Insert.class}) DiscussionDto discussionDto) {
        return super.add(discussionDto);
    }

    @SaCheckLogin
    @PostMapping("/edit")
    @SaveLog(logType = "操作日志", moduleName = "讨论话题", description = "编辑话题: ?1", expressions = {"#p1.id"}, actionType = "编辑")
    public R<Object> edit(@RequestBody @Validated({Update.class}) DiscussionDto discussionDto) {
        return super.edit(discussionDto);
    }

    @SaCheckRole(SystemConstant.SUPER_ADMIN_ROLE)
    @GetMapping("/delete/{id}")
    @SaveLog(logType = "操作日志", moduleName = "讨论话题", description = "删除话题: ?1 ", expressions = {"#p1"}, actionType = "删除")
    public R<Object> delete( @PathVariable("id") Long id) {
        return super.delete(id);
    }

}
