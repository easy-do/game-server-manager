package plus.easydo.uc.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.zhxu.bs.BeanSearcher;
import cn.zhxu.bs.SearchResult;
import plus.easydo.uc.api.AuthorizationCodeApi;
import plus.easydo.uc.dto.AuthorizationCodeDto;
import plus.easydo.uc.vo.AuthorizationCodeVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;
import plus.easydo.common.constant.SystemConstant;
import plus.easydo.log.SaveLog;
import plus.easydo.common.result.MpDataResult;
import plus.easydo.dao.result.MpResultUtil;
import plus.easydo.common.dto.AuthorizationConfigDto;
import plus.easydo.uc.entity.AuthorizationCode;
import plus.easydo.uc.mapstruct.AuthorizationCodeMapstruct;
import plus.easydo.uc.service.AuthorizationCodeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plus.easydo.common.result.DataResult;
import plus.easydo.common.result.R;

import java.util.List;
import java.util.Map;


/**
 * @author laoyu
 * @version 1.0
 * @date 2022/5/21
 */
@RestController
@RequestMapping(AuthorizationCodeApi.apiPath)
@RequiredArgsConstructor
public class AuthorizationCodeController implements AuthorizationCodeApi {


    private final AuthorizationCodeService baseService;

    private final BeanSearcher beanSearcher;


    @SaCheckRole(SystemConstant.SUPER_ADMIN_ROLE)
    @GetMapping("/list")
    public R<List<AuthorizationCodeVo>> list(@RequestParam(required = false) Map<String,Object> queryParam) {
        List<AuthorizationCode> result = beanSearcher.searchList(AuthorizationCode.class, queryParam);
        return DataResult.ok(AuthorizationCodeMapstruct.INSTANCE.entityToVo(result));
    }


    @SaCheckRole(SystemConstant.SUPER_ADMIN_ROLE)
    @PostMapping("/page")
    public MpDataResult page(@RequestBody Map<String,Object> queryParam) {
        SearchResult<AuthorizationCode> result = beanSearcher.search(AuthorizationCode.class, queryParam);
        List<AuthorizationCodeVo> voList = AuthorizationCodeMapstruct.INSTANCE.entityToVo(result.getDataList());
        return MpResultUtil.buildPage(voList,(Long)result.getTotalCount());
    }

    @SaCheckRole(SystemConstant.SUPER_ADMIN_ROLE)
    @GetMapping("/info/{id}")
    public R<AuthorizationCodeVo> info(@PathVariable("id")String id) {
        AuthorizationCode entity = baseService.getById(id);
        return DataResult.ok(AuthorizationCodeMapstruct.INSTANCE.entityToVo(entity));
    }

    @SaCheckRole(SystemConstant.SUPER_ADMIN_ROLE)
    @PostMapping("/update")
    @SaveLog(logType = "操作日志", moduleName = "授权码管理", description = "编辑授权码: ?1", expressions = {"#p1.id"},actionType = "编辑")
    public R<Object> edit(@RequestBody AuthorizationCodeDto authorizationCodeDto) {
        AuthorizationCode entity = AuthorizationCodeMapstruct.INSTANCE.dtoToEntity(authorizationCodeDto);
        return baseService.updateById(entity)? DataResult.ok():DataResult.fail();
    }

    @SaCheckRole(SystemConstant.SUPER_ADMIN_ROLE)
    @PostMapping("/generateAuthCode")
    @SaveLog(logType = "操作日志", moduleName = "授权码管理", description = "生成授权码 ?1 个", expressions = {"#p1.genNum"},actionType = "添加")
    public R<Object> generateAuthorization(@RequestBody() AuthorizationConfigDto authorizationConfigDto) {
        boolean result = baseService.generateAuthorization(authorizationConfigDto);
        return result? DataResult.ok():DataResult.fail();
    }

    @SaCheckRole(SystemConstant.SUPER_ADMIN_ROLE)
    @GetMapping("/remove/{id}")
    @SaveLog(logType = "操作日志", moduleName = "授权码管理", description = "删除授权码: ?1", expressions = {"#p1"}, actionType = "删除")
    public R<Object> delete(@PathVariable("id")String id) {
        return baseService.removeById(id)? DataResult.ok():DataResult.fail();
    }
}
