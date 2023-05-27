package plus.easydo.generate.controller;


import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.zhxu.bs.BeanSearcher;
import cn.zhxu.bs.SearchResult;
import lombok.RequiredArgsConstructor;
import plus.easydo.common.result.DataResult;
import  plus.easydo.dao.result.MpResultUtil;
import plus.easydo.generate.api.TemplateManagementApi;
import plus.easydo.generate.mapstruct.TemplateMapstruct;
import plus.easydo.generate.service.TemplateManagementService;
import plus.easydo.generate.vo.TemplateManagementVo;
import plus.easydo.generate.dto.TemplateManagementDto;
import plus.easydo.generate.entity.TemplateManagement;
import plus.easydo.log.SaveLog;
import plus.easydo.common.result.MpDataResult;
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
 * 模板管理Controller
 *
 * @author yuzhanfeng
 * @date 2023-05-27 17:58:09
 */
@RestController
@RequestMapping(TemplateManagementApi.apiPath)
@RequiredArgsConstructor
public class TemplateManagementController implements TemplateManagementApi{


    private final TemplateManagementService baseService;

    private final BeanSearcher beanSearcher;

    private final TemplateMapstruct convert;


    /**
     * 获取所有模板管理列表
     */
    @SaCheckPermission("generate:TemplateManagement:list")
    @GetMapping("/list")
    public R<List<TemplateManagementVo>> list(@RequestParam(required = false) Map<String, Object> queryParam) {
        List<TemplateManagement> result = beanSearcher.searchList(TemplateManagement.class, queryParam);
        return DataResult.ok(convert.entityToVo(result));
    }

    /**
     * 分页条件查询模板管理列表
     */
    @SaCheckPermission("generate:TemplateManagement:page")
    @PostMapping("/page")
    public MpDataResult page(@RequestBody Map<String,Object> queryParam) {
        SearchResult<TemplateManagement> result = beanSearcher.search(TemplateManagement.class, queryParam);
        List<TemplateManagementVo> voList = convert.entityToVo(result.getDataList());
        return MpResultUtil.buildPage(voList,(Long)result.getTotalCount());
    }


    /**
     * 获取模板管理详细信息
     */
    @SaCheckPermission("generate:TemplateManagement:info")
    @GetMapping("/info/{id}")
    public R<TemplateManagementVo> info(@PathVariable("id")Long id) {
        TemplateManagement entity = baseService.getById(id);
        return DataResult.ok(convert.entityToVo(entity));
    }

    /**
     * 新增模板管理
     */
    @SaCheckPermission("generate:TemplateManagement:add")
    @PostMapping("/add")
    @SaveLog(logType = "操作日志", moduleName = "模板管理", description = "添加模板管理", actionType = "添加")
    public R<Object> add(@RequestBody @Validated({Insert.class}) TemplateManagementDto templateManagementDto) {
        TemplateManagement entity = convert.dtoToEntity(templateManagementDto);
        return baseService.save(entity)? DataResult.ok():DataResult.fail();
    }

    /**
     * 修改模板管理
     */
    @SaCheckPermission("generate:TemplateManagement:update")
    @PostMapping("/update")
    @SaveLog(logType = "操作日志", moduleName = "模板管理", description = "编辑模板管理: ?1", expressions = {"#p1.id"},actionType = "编辑")
    public R<Object> update(@RequestBody @Validated({Update.class}) TemplateManagementDto templateManagementDto) {
        TemplateManagement entity = convert.dtoToEntity(templateManagementDto);
        return baseService.updateById(entity)? DataResult.ok():DataResult.fail();
    }

    /**
     * 删除模板管理
     */
    @SaCheckPermission("generate:TemplateManagement:remove")
    @GetMapping("/remove/{id}")
    @SaveLog(logType = "操作日志", moduleName = "模板管理", description = "删除模板管理: ?1", expressions = {"#p1"}, actionType = "删除")
    public R<Object> remove(@PathVariable("id")Long id) {
        return baseService.removeById(id)? DataResult.ok():DataResult.fail();
    }
}
