package plus.easydo.uc.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import plus.easydo.common.constant.SystemConstant;
import plus.easydo.web.base.BaseController;
import plus.easydo.common.dto.ChangeStatusDto;
import plus.easydo.uc.dto.SysDictTypeDto;
import plus.easydo.uc.entity.SysDictType;
import plus.easydo.log.SaveLog;
import plus.easydo.dao.qo.MpBaseQo;
import plus.easydo.common.result.MpDataResult;
import plus.easydo.uc.service.SysDictTypeService;
import plus.easydo.common.vo.SysDictTypeVo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plus.easydo.common.result.DataResult;
import plus.easydo.common.result.R;
import plus.easydo.common.vaild.Insert;
import plus.easydo.common.vaild.Update;

/**
 * @author laoyu
 * @version 1.0
 * @date 2022/7/22
 */
@RestController
@RequestMapping("/dictType")
public class SysDictTypeController extends BaseController<SysDictTypeService, SysDictType, Long, MpBaseQo<SysDictType>, SysDictTypeVo, SysDictTypeDto> {

    public static final String MODULE_NAME = "字典类型管理";

    public static final String ADD_DESCRIPTION = "添加字典类型: ?1";

    public static final String ADD_EXPRESSIONS = "#p1.dictName";

    public static final String EDIT_DESCRIPTION = "编辑字典类型: ?1";

    public static final String EDIT_EXPRESSIONS = "#p1.id";

    public static final String REMOVE_DESCRIPTION = "删除字典类型: ?1";

    public static final String REMOVE_EXPRESSIONS = "#p1";


    @Override
    @SaCheckLogin
    public MpDataResult page(@RequestBody MpBaseQo<SysDictType> mpBaseQo){
        return super.page(mpBaseQo);
    }

    @Override
    @SaCheckLogin
    public R<SysDictTypeVo> info(@PathVariable("id") Long id) {
        return super.info(id);
    }

    @Override
    @SaCheckLogin
    @SaveLog(logType = LOG_TYPE, moduleName = MODULE_NAME, description = ADD_DESCRIPTION, expressions = ADD_EXPRESSIONS, actionType = ADD_ACTION)
    public R<Object> add(@RequestBody @Validated({Insert.class}) SysDictTypeDto sysDictTypeDto) {
        return super.add(sysDictTypeDto);
    }

    @Override
    @SaCheckLogin
    @SaveLog(logType = LOG_TYPE, moduleName = MODULE_NAME, description = EDIT_DESCRIPTION, expressions = EDIT_EXPRESSIONS, actionType = EDIT_ACTION)
    public R<Object> update(@RequestBody @Validated({Update.class}) SysDictTypeDto sysDictTypeDto) {
        return super.update(sysDictTypeDto);
    }

    @Override
    @SaCheckLogin
    @SaveLog(logType = LOG_TYPE, moduleName = MODULE_NAME, description = REMOVE_DESCRIPTION, expressions = REMOVE_EXPRESSIONS, actionType = REMOVE_ACTION)
    public R<Object> remove(@PathVariable("id") Long id) {
        return super.remove(id);
    }

    @SaCheckRole(SystemConstant.SUPER_ADMIN_ROLE)
    @PostMapping("/changeStatus")
    public R<Object> changeStatus(@Validated @RequestBody ChangeStatusDto changeStatusDto) {
        return baseService.changeStatus(changeStatusDto)? DataResult.ok():DataResult.fail();
    }


}
