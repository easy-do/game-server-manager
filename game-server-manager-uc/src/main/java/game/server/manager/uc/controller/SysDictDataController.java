package game.server.manager.uc.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.id.SaIdUtil;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.text.CharSequenceUtil;
import game.server.manager.api.SysDictDataApi;
import game.server.manager.common.constant.SystemConstant;
import game.server.manager.common.dto.ChangeStatusDto;
import game.server.manager.uc.dto.SysDictDataDto;
import game.server.manager.uc.entity.SysDictData;
import game.server.manager.uc.service.SysDictDataService;
import game.server.manager.web.base.BaseController;
import game.server.manager.log.SaveLog;
import game.server.manager.mybatis.plus.qo.MpBaseQo;
import game.server.manager.mybatis.plus.result.MpDataResult;
import game.server.manager.common.vo.SysDictDataVo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import game.server.manager.common.exception.BizException;
import game.server.manager.common.result.DataResult;
import game.server.manager.common.result.R;
import game.server.manager.common.vaild.Insert;
import game.server.manager.common.vaild.Update;


/**
 * @author laoyu
 * @version 1.0
 * @date 2022/7/22
 */
@RestController
@RequestMapping("/dictData")
public class SysDictDataController extends BaseController<SysDictDataService, SysDictData, Long, MpBaseQo<SysDictData>, SysDictDataVo, SysDictDataDto> implements SysDictDataApi {

    public static final String MODULE_NAME = "字典数据管理";

    public static final String ADD_DESCRIPTION = "添加字典数据: ?1";

    public static final String ADD_EXPRESSIONS = "#p1.dictKey";

    public static final String EDIT_DESCRIPTION = "编辑字典数据: ?1";

    public static final String EDIT_EXPRESSIONS = "#p1.id";

    public static final String REMOVE_DESCRIPTION = "删除字典数据: ?1";

    public static final String REMOVE_EXPRESSIONS = "#p1";


    @Override
    @SaCheckLogin
    public MpDataResult page(@RequestBody MpBaseQo<SysDictData> mpBaseQo){
        return super.page(mpBaseQo);
    }

    @Override
    @SaCheckLogin
    public R<SysDictDataVo> info(@PathVariable("id") Long id) {
        return super.info(id);
    }

    @Override
    @SaCheckLogin
    @SaveLog(logType = LOG_TYPE, moduleName = MODULE_NAME, description = ADD_DESCRIPTION, expressions = ADD_EXPRESSIONS, actionType = ADD_ACTION)
    public R<Object> add(@RequestBody @Validated({Insert.class}) SysDictDataDto sysDictDataDto) {
        return super.add(sysDictDataDto);
    }

    @Override
    @SaCheckLogin
    @SaveLog(logType = LOG_TYPE, moduleName = MODULE_NAME, description = EDIT_DESCRIPTION, expressions = EDIT_EXPRESSIONS, actionType = EDIT_ACTION)
    public R<Object> update(@RequestBody @Validated({Update.class}) SysDictDataDto sysDictDataDto) {
        return super.update(sysDictDataDto);
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

    @GetMapping("/listByCode/{dictCode}")
    public R<Object> listByCode(@PathVariable("dictCode") String dictCode) {
        if(!StpUtil.isLogin()){
          // 不是用户请求的则校验 Id-Token 身份凭证
          SaIdUtil.checkCurrentRequestToken();
        }
        if(CharSequenceUtil.isEmpty(dictCode)){
            throw new BizException("code为空");
        }
        return DataResult.ok(baseService.listByCode(dictCode));
    }


    @GetMapping("/getSingleDictData/{dictCode}/{dictDataKey}")
    public R<SysDictDataVo> getSingleDictData(@PathVariable("dictCode")String dictCode, @PathVariable("dictDataKey")String dictDataKey){
        if(!StpUtil.isLogin()){
            // 不是用户请求的则校验 Id-Token 身份凭证
            SaIdUtil.checkCurrentRequestToken();
        }
        return DataResult.ok(baseService.getSingleDictData(dictCode,dictDataKey));
    }


}
