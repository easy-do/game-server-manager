package plus.easydo.server.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import plus.easydo.common.result.DataResult;
import plus.easydo.common.vo.ExecScriptVo;
import plus.easydo.common.vo.ScriptDataVo;
import plus.easydo.common.vo.ScriptEnvDataListVo;
import plus.easydo.server.dto.ScriptDataDto;
import plus.easydo.server.entity.ScriptData;
import plus.easydo.server.service.ScriptEnvDataService;
import plus.easydo.server.service.ExecScriptService;
import plus.easydo.server.service.ScriptDataService;
import plus.easydo.web.base.BaseController;
import plus.easydo.log.SaveLog;
import plus.easydo.dao.qo.MpBaseQo;
import plus.easydo.common.result.MpDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plus.easydo.common.result.R;
import plus.easydo.common.vaild.Insert;
import plus.easydo.common.vaild.Update;

import java.util.List;


/**
 * @author laoyu
 * @version 1.0
 */
@RestController
@RequestMapping("/scriptData")
public class ScriptDataController extends BaseController<ScriptDataService, ScriptData, Long, MpBaseQo<ScriptData>, ScriptDataVo, ScriptDataDto> {

    public static final String MODULE_NAME = "脚本管理";

    public static final String ADD_DESCRIPTION = "添加脚本: ?1";

    public static final String ADD_EXPRESSIONS = "#p1.scriptName";

    public static final String REMOVE_DESCRIPTION = "删除脚本: ?1";

    public static final String REMOVE_EXPRESSIONS = "#p1";

    @Autowired
    private ExecScriptService execScriptService;

    @Autowired
    private ScriptEnvDataService scriptEnvDataService;

    @SaCheckLogin
    @RequestMapping("/list")
    @Override
    public R<List<ScriptDataVo>> list() {
        return super.list();
    }

    @SaCheckLogin
    @PostMapping("/page")
    @Override
    public MpDataResult page(@RequestBody MpBaseQo mpBaseQo) {
        return super.page(mpBaseQo);
    }

    @SaCheckLogin
    @RequestMapping("/info/{id}")
    @Override
    public R<ScriptDataVo> info(@PathVariable("id") Long id) {
        return super.info(id);
    }

    @SaCheckLogin
    @PostMapping("/add")
    @SaveLog(logType = LOG_TYPE, moduleName = MODULE_NAME, description = ADD_DESCRIPTION, expressions = ADD_EXPRESSIONS,actionType = ADD_ACTION)
    @Override
    public R<Object> add(@RequestBody @Validated({Insert.class}) ScriptDataDto scriptDataDto) {
        return super.add(scriptDataDto);
    }

    @SaCheckLogin
    @PostMapping("/update")
    @SaveLog(logType = LOG_TYPE, moduleName = MODULE_NAME, description = "编辑脚本: ?1 - ?2", expressions = {"#p1.id","#p1.scriptName"},actionType = EDIT_ACTION)
    @Override
    public R<Object> update(@RequestBody @Validated({Update.class}) ScriptDataDto scriptDataDto) {
        return super.update(scriptDataDto);
    }

    @SaCheckLogin()
    @GetMapping("/remove/{id}")
    @SaveLog(logType = LOG_TYPE, moduleName = MODULE_NAME, description = REMOVE_DESCRIPTION, expressions = REMOVE_EXPRESSIONS, actionType = REMOVE_ACTION)
    @Override
    public R<Object> remove(@PathVariable("id") Long id) {
        return super.remove(id);
    }


    @SaCheckLogin
    @PostMapping("/execScript")
    @SaveLog(logType = LOG_TYPE, moduleName = MODULE_NAME, description = "编辑脚本: ?1 - ?2", expressions = {"#p1.id","#p1.scriptName"},actionType = EDIT_ACTION)
    public R<Object> execScript(@RequestBody ExecScriptVo execScriptVo) {
        return execScriptService.execScript(execScriptVo)? DataResult.ok():DataResult.fail();
    }

    @SaCheckLogin
    @RequestMapping("/evnList/{scriptId}")
    public R<List<ScriptEnvDataListVo>> listByScriptId(@RequestHeader("token") String token, @PathVariable Long scriptId) {
        return DataResult.ok(scriptEnvDataService.listByScriptId(scriptId));
    }

}

