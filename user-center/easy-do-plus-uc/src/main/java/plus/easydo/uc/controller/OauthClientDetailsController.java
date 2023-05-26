package plus.easydo.uc.controller;


import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.zhxu.bs.BeanSearcher;
import cn.zhxu.bs.SearchResult;
import lombok.RequiredArgsConstructor;
import plus.easydo.common.result.DataResult;
import  plus.easydo.dao.result.MpResultUtil;
import plus.easydo.uc.api.OauthClientDetailsApi;
import plus.easydo.uc.service.OauthClientDetailsService;
import plus.easydo.uc.vo.OauthClientDetailsVo;
import plus.easydo.uc.dto.OauthClientDetailsDto;
import plus.easydo.uc.entity.OauthClientDetails;
import plus.easydo.log.SaveLog;
import plus.easydo.common.result.MpDataResult;
import plus.easydo.uc.mapstruct.OauthClientDetailsMapstruct;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plus.easydo.common.result.R;
import plus.easydo.common.vaild.Insert;
import plus.easydo.common.vaild.Update;

import java.util.List;
import java.util.Map;


/**
 * 授权客户端信息Controller
 *
 * @author yuzhanfeng
 * @date 2023-05-27 02:25:50
 */
@RestController
@RequestMapping(OauthClientDetailsApi.apiPath)
@RequiredArgsConstructor
public class OauthClientDetailsController implements OauthClientDetailsApi{


    private final OauthClientDetailsService baseService;

    private final BeanSearcher beanSearcher;


    /**
     * 获取所有授权客户端信息列表
     */
    @SaCheckPermission("uc:oauthClient:list")
    @GetMapping("/list")
    public R<List<OauthClientDetailsVo>> list(@RequestParam(required = false) Map<String, Object> queryParam) {
        List<OauthClientDetails> result = beanSearcher.searchList(OauthClientDetails.class, queryParam);
        return DataResult.ok(OauthClientDetailsMapstruct.INSTANCE.entityToVo(result));
    }

    /**
     * 分页条件查询授权客户端信息列表
     */
    @SaCheckPermission("uc:oauthClient:page")
    @PostMapping("/page")
    public MpDataResult page(@RequestBody Map<String,Object> queryParam) {
        SearchResult<OauthClientDetails> result = beanSearcher.search(OauthClientDetails.class, queryParam);
        List<OauthClientDetailsVo> voList = OauthClientDetailsMapstruct.INSTANCE.entityToVo(result.getDataList());
        return MpResultUtil.buildPage(voList,(Long)result.getTotalCount());
    }


    /**
     * 获取授权客户端信息详细信息
     */
    @SaCheckPermission("uc:oauthClient:info")
    @GetMapping("/info/{id}")
    public R<OauthClientDetailsVo> info(@PathVariable("id")Long id) {
        OauthClientDetails entity = baseService.getById(id);
        return DataResult.ok(OauthClientDetailsMapstruct.INSTANCE.entityToVo(entity));
    }

    /**
     * 新增授权客户端信息
     */
    @SaCheckPermission("uc:oauthClient:add")
    @PostMapping("/add")
    @SaveLog(logType = "操作日志", moduleName = "授权客户端信息", description = "添加授权客户端信息", actionType = "添加")
    public R<Object> add(@RequestBody @Validated({Insert.class}) OauthClientDetailsDto oauthClientDetailsDto) {
        OauthClientDetails entity = OauthClientDetailsMapstruct.INSTANCE.dtoToEntity(oauthClientDetailsDto);
        return baseService.save(entity)? DataResult.ok():DataResult.fail();
    }

    /**
     * 修改授权客户端信息
     */
    @SaCheckPermission("uc:oauthClient:update")
    @PostMapping("/update")
    @SaveLog(logType = "操作日志", moduleName = "授权客户端信息", description = "编辑授权客户端信息: ?1", expressions = {"#p1.id"},actionType = "编辑")
    public R<Object> update(@RequestBody @Validated({Update.class}) OauthClientDetailsDto oauthClientDetailsDto) {
        OauthClientDetails entity = OauthClientDetailsMapstruct.INSTANCE.dtoToEntity(oauthClientDetailsDto);
        return baseService.updateById(entity)? DataResult.ok():DataResult.fail();
    }

    /**
     * 删除授权客户端信息
     */
    @SaCheckPermission("uc:oauthClient:remove")
    @GetMapping("/remove/{id}")
    @SaveLog(logType = "操作日志", moduleName = "授权客户端信息", description = "删除授权客户端信息: ?1", expressions = {"#p1"}, actionType = "删除")
    public R<Object> remove(@PathVariable("id")Long id) {
        return baseService.removeById(id)? DataResult.ok():DataResult.fail();
    }
}
