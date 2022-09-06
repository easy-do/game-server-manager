package game.server.manager.server.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.hutool.core.bean.BeanUtil;
import game.server.manager.common.application.DeployParam;
import game.server.manager.web.base.BaseController;
import game.server.manager.server.dto.ApplicationInfoDto;
import game.server.manager.server.dto.DeployParamDto;
import game.server.manager.server.entity.ApplicationInfo;
import game.server.manager.log.SaveLog;
import game.server.manager.mybatis.plus.qo.MpBaseQo;
import game.server.manager.mybatis.plus.result.MpDataResult;
import game.server.manager.server.service.ApplicationInfoService;
import game.server.manager.common.vo.ApplicationInfoVo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import game.server.manager.common.result.DataResult;
import game.server.manager.common.result.R;
import game.server.manager.common.vaild.Insert;
import game.server.manager.common.vaild.Update;

import java.util.List;

/**
 * @author laoyu
 * @version 1.0
 * @date 2022/5/20
 */
@RestController
@RequestMapping("/application")
public class ApplicationController extends BaseController<ApplicationInfoService, ApplicationInfo, String, MpBaseQo<ApplicationInfo>, ApplicationInfoVo, ApplicationInfoDto> {


    public static final String MODULE_NAME = "应用管理";

    public static final String ADD_DESCRIPTION = "添加应用: ?1";

    public static final String ADD_EXPRESSIONS = "#p1.applicationName";

    public static final String EDIT_DESCRIPTION = "编辑应用: ?1 - ?2";

    public static final String REMOVE_DESCRIPTION = "删除应用: ?1";

    public static final String REMOVE_EXPRESSIONS = "#p1";

    @SaCheckLogin
    @RequestMapping("/list")
    public R<List<ApplicationInfoVo>> list() {
        return super.list();
    }

    @SaCheckLogin
    @PostMapping("/page")
    public MpDataResult page(@RequestBody MpBaseQo<ApplicationInfo> mpBaseQo) {
        return super.page(mpBaseQo);
    }

    @SaCheckLogin
    @RequestMapping("/info/{id}")
    public R<ApplicationInfoVo> info(@PathVariable("id")String id) {
        return super.info(id);
    }
    @SaCheckLogin
    @PostMapping("/add")
    @SaveLog(logType = "操作日志", moduleName = MODULE_NAME, description = "添加应用: ?1",expressions = ADD_EXPRESSIONS,  actionType = ADD_ACTION)
    public R<Object> add(@RequestBody @Validated({Insert.class}) ApplicationInfoDto applicationInfoDto) {
        return super.add(applicationInfoDto);
    }

    @SaCheckLogin
    @PostMapping("/edit")
    @SaveLog(logType = "操作日志", moduleName = MODULE_NAME, description = EDIT_DESCRIPTION, expressions = {"#p1.applicationId","#p1.applicationName"},  actionType = EDIT_ACTION)
    public R<Object> edit(@RequestBody @Validated({Update.class}) ApplicationInfoDto applicationInfoDto) {
        return super.edit(applicationInfoDto);
    }

    @SaCheckLogin
    @GetMapping("/delete/{id}")
    @SaveLog(logType = "操作日志", moduleName = MODULE_NAME, description = REMOVE_DESCRIPTION, expressions = REMOVE_EXPRESSIONS, actionType = REMOVE_ACTION)
    public R<Object> delete(@PathVariable("id")String id) {
        return super.delete(id);
    }
    @SaCheckLogin
    @PostMapping("/deployment")
    @SaveLog(logType = "操作日志", moduleName = MODULE_NAME, description = "应用: ?1,脚本: ?2",expressions = {"#p1.applicationId","#p1.appScriptId"},  actionType = "执行脚本")
    public R<Object> deployment(@RequestBody DeployParamDto deployParamDto) {
        DeployParam deployParam = BeanUtil.copyProperties(deployParamDto,DeployParam.class);
        return baseService.deployment(deployParam) ? DataResult.okMsg("部署请求已提交,详情请查看部署日志."):DataResult.fail();
    }
}
