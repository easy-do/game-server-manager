package game.server.manager.server.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import game.server.manager.web.base.BaseController;
import game.server.manager.server.dto.AppScriptDto;
import game.server.manager.server.entity.AppScript;
import game.server.manager.log.SaveLog;
import game.server.manager.mybatis.plus.qo.MpBaseQo;
import game.server.manager.mybatis.plus.result.MpDataResult;
import game.server.manager.server.service.AppScriptService;
import game.server.manager.common.vo.AppScriptVo;
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
 */
@RestController
@RequestMapping("/appScript")
public class AppScriptController extends BaseController<AppScriptService, AppScript, Long, MpBaseQo<AppScript>, AppScriptVo, AppScriptDto> {

    public static final String MODULE_NAME = "脚本管理";

    public static final String ADD_DESCRIPTION = "添加脚本: ?1";

    public static final String ADD_EXPRESSIONS = "#p1.scriptName";

    public static final String REMOVE_DESCRIPTION = "删除脚本: ?1";

    public static final String REMOVE_EXPRESSIONS = "#p1";

    @SaCheckLogin
    @RequestMapping("/list")
    public R<List<AppScriptVo>> list() {
        return super.list();
    }

    @SaCheckLogin
    @RequestMapping("/listByAppid/{appId}")
    public R<List<AppScriptVo>> listByAppid(@PathVariable Long appId) {
        return  DataResult.ok(baseService.listByAppid(appId));

    }

    @SaCheckLogin
    @PostMapping("/page")
    public MpDataResult page(@RequestBody MpBaseQo mpBaseQo) {
        return super.page(mpBaseQo);
    }

    @SaCheckLogin
    @RequestMapping("/info/{id}")
    public R<AppScriptVo> info(@PathVariable("id") Long id) {
        return super.info(id);
    }

    @SaCheckLogin
    @PostMapping("/add")
    @SaveLog(logType = LOG_TYPE, moduleName = MODULE_NAME, description = ADD_DESCRIPTION, expressions = ADD_EXPRESSIONS,actionType = ADD_ACTION)
    public R<Object> add(@RequestBody @Validated({Insert.class}) AppScriptDto appScriptDto) {
        return super.add(appScriptDto);
    }

    @SaCheckLogin
    @PostMapping("/update")
    @SaveLog(logType = LOG_TYPE, moduleName = MODULE_NAME, description = "编辑脚本: ?1 - ?2", expressions = {"#p1.id","#p1.scriptName"},actionType = EDIT_ACTION)
    public R<Object> update(@RequestBody @Validated({Update.class}) AppScriptDto appScriptDto) {
        return super.update(appScriptDto);
    }

    @SaCheckLogin()
    @GetMapping("/remove/{id}")
    @SaveLog(logType = LOG_TYPE, moduleName = MODULE_NAME, description = REMOVE_DESCRIPTION, expressions = REMOVE_EXPRESSIONS, actionType = REMOVE_ACTION)
    public R<Object> remove(@PathVariable("id") Long id) {
        return super.remove(id);
    }


}

