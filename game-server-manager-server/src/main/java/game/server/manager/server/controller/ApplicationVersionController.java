package game.server.manager.server.controller;


import cn.dev33.satoken.annotation.SaCheckLogin;
import game.server.manager.web.base.BaseController;
import game.server.manager.server.qo.ApplicationVersionQo;
import game.server.manager.server.service.ApplicationVersionService;
import game.server.manager.server.vo.ApplicationVersionVo;
import game.server.manager.server.dto.ApplicationVersionDto;
import  game.server.manager.server.entity.ApplicationVersion;
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
 * 应用版本信息Controller
 * 
 * @author yuzhanfeng
 * @date 2023-03-18 14:56:21
 */
@RestController
@RequestMapping("/applicationVersion")
public class ApplicationVersionController extends BaseController<ApplicationVersionService,ApplicationVersion,Long, ApplicationVersionQo,ApplicationVersionVo,ApplicationVersionDto> {

    /**
     * 获取所有应用版本信息列表
     */
    @SaCheckLogin
    @RequestMapping("/list")
    @Override
    public R<List<ApplicationVersionVo>> list() {
        return super.list();
    }

    /**
     * 分页条件查询应用版本信息列表
     */
    @SaCheckLogin
    @PostMapping("/page")
    @Override
    public MpDataResult page(@RequestBody ApplicationVersionQo applicationVersionQo) {
        return super.page(applicationVersionQo);
    }


    /**
     * 获取应用版本信息详细信息
     */
    @SaCheckLogin
    @GetMapping("/info/{id}")
    @Override
    public R<ApplicationVersionVo> info(@PathVariable("id")Long id) {
        return super.info(id);
    }

    /**
     * 新增应用版本信息
     */
    @SaCheckLogin
    @PostMapping("/add")
    @SaveLog(logType = "操作日志", moduleName = "应用版本信息", description = "添加应用版本信息", actionType = "添加")
    @Override
    public R<Object> add(@RequestBody @Validated({Insert.class}) ApplicationVersionDto applicationVersionDto) {
        return super.add(applicationVersionDto);
    }

    /**
     * 修改应用版本信息
     */
    @SaCheckLogin
    @PostMapping("/update")
    @SaveLog(logType = "操作日志", moduleName = "应用版本信息", description = "编辑应用版本信息: ?1", expressions = {"#p1.id"},actionType = "编辑")
    @Override
    public R<Object> update(@RequestBody @Validated({Update.class}) ApplicationVersionDto applicationVersionDto) {
        return super.update(applicationVersionDto);
    }

    /**
     * 删除应用版本信息
     */
    @SaCheckLogin
	@GetMapping("/remove/{id}")
    @SaveLog(logType = "操作日志", moduleName = "应用版本信息", description = "删除应用版本信息: ?1", expressions = {"#p1"}, actionType = "删除")
    @Override
    public R<Object> remove(@PathVariable("id")Long id) {
        return super.remove(id);
    }
}
