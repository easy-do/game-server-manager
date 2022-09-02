package game.server.manager.server.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import game.server.manager.log.SaveLog;
import game.server.manager.web.base.BaseController;
import game.server.manager.common.vaild.Insert;
import game.server.manager.common.vaild.Update;
import game.server.manager.server.dto.AppInfoDto;
import game.server.manager.server.entity.AppInfo;
import game.server.manager.mybatis.plus.qo.MpBaseQo;
import game.server.manager.mybatis.plus.result.MpDataResult;
import game.server.manager.server.service.AppInfoService;
import game.server.manager.common.vo.AppInfoVo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import game.server.manager.common.result.R;


/**
 * @author laoyu
 * @version 1.0
 * @date 2022/5/20
 */
@RestController
@RequestMapping("/app")
public class AppInfoController extends BaseController<AppInfoService, AppInfo, Long, AppInfoVo, AppInfoDto> {

    public static final String MODULE_NAME = "APP管理";

    public static final String ADD_DESCRIPTION = "添加APP: ?1";

    public static final String ADD_EXPRESSIONS = "#p1.appName";

    public static final String EDIT_DESCRIPTION = "编辑APP: ?1";

    public static final String EDIT_EXPRESSIONS = "#p1.id";

    public static final String REMOVE_DESCRIPTION = "删除APP: ?1";

    public static final String REMOVE_EXPRESSIONS = "#p1";


    @SaCheckLogin
    public MpDataResult page(@RequestBody MpBaseQo mpBaseQo){
        return super.page(mpBaseQo);
    }

    @SaCheckLogin
    public R<AppInfoVo> info(@PathVariable("id") Long id) {
        return super.info(id);
    }

    @SaCheckLogin
    @SaveLog(logType = LOG_TYPE, moduleName = MODULE_NAME, description = ADD_DESCRIPTION, expressions = ADD_EXPRESSIONS, actionType = ADD_ACTION)
    public R<Object> add(@RequestBody @Validated({Insert.class}) AppInfoDto appInfoDto) {
        return super.add(appInfoDto);
    }

    @SaCheckLogin
    @SaveLog(logType = LOG_TYPE, moduleName = MODULE_NAME, description = EDIT_DESCRIPTION, expressions = EDIT_EXPRESSIONS, actionType = EDIT_ACTION)
    public R<Object> edit(@RequestBody @Validated({Update.class}) AppInfoDto appInfoDto) {
        return super.edit(appInfoDto);
    }

    @SaCheckLogin
    @SaveLog(logType = LOG_TYPE, moduleName = MODULE_NAME, description = REMOVE_DESCRIPTION, expressions = REMOVE_EXPRESSIONS, actionType = REMOVE_ACTION)
    public R<Object> delete(@PathVariable("id") Long id) {
        return super.delete(id);
    }



}
